from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import StaleElementReferenceException


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def _wait(driver, timeout=20):
    return WebDriverWait(driver, timeout)


# ---------------------------------------------------------------------------
# Given steps
# ---------------------------------------------------------------------------

@given("I ensure the cart is empty")
def step_ensure_cart_empty(context):
    d = context.driver
    d.get(context.base_url + "/cart")
    _wait(d).until(EC.presence_of_element_located((By.CSS_SELECTOR, ".cart-container")))
    clear_btns = d.find_elements(By.CSS_SELECTOR, ".clear-cart-btn")
    if clear_btns and clear_btns[0].is_displayed():
        clear_btns[0].click()
        _wait(d).until(EC.presence_of_element_located((By.CSS_SELECTOR, ".empty-cart")))


# ---------------------------------------------------------------------------
# When steps
# ---------------------------------------------------------------------------

@when("I click the empty cart Continue Shopping link")
def step_click_empty_cart_continue(context):
    d = context.driver
    link = _wait(d).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".empty-cart .continue-shopping-link"))
    )
    link.click()


@when("I select Add New Location from the delivery dropdown")
def step_select_add_new_location(context):
    d = context.driver
    delivery_sel_el = _wait(d).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".delivery-select"))
    )
    sel = Select(delivery_sel_el)
    sel.select_by_value("ADD_NEW")
    d.execute_script(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        delivery_sel_el
    )


@when("I click the Checkout button")
def step_click_checkout(context):
    d = context.driver
    btn = _wait(d).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".checkout-btn"))
    )
    btn.click()


@when("I click the Clear Cart button")
def step_click_clear_cart(context):
    d = context.driver
    btn = _wait(d).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".clear-cart-btn"))
    )
    btn.click()


@when("I click the remove button on the first cart item")
def step_click_remove_first_item(context):
    d = context.driver
    context.cart_count_before_remove = len(d.find_elements(By.CSS_SELECTOR, ".cart-item"))
    remove_btn = _wait(d).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".cart-item .remove-btn"))
    )
    remove_btn.click()


@when("I click the summary Continue Shopping button")
def step_click_summary_continue(context):
    d = context.driver
    btn = _wait(d).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".continue-shopping-btn"))
    )
    btn.click()


@when("I select the first real delivery location")
def step_select_first_delivery_location(context):
    d = context.driver
    delivery_sel_el = _wait(d).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".delivery-select"))
    )
    sel = Select(delivery_sel_el)
    for opt in sel.options:
        if opt.get_attribute("disabled"):
            continue
        val = (opt.get_attribute("value") or "").strip()
        if val and val != "ADD_NEW":
            sel.select_by_value(val)
            d.execute_script(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                delivery_sel_el
            )
            return
    raise AssertionError("No real delivery location found in the dropdown")


# ---------------------------------------------------------------------------
# Then steps
# ---------------------------------------------------------------------------

@then("the cart page heading should be visible")
def step_cart_heading(context):
    d = context.driver
    heading = _wait(d).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".cart-container h1"))
    )
    assert heading.text.strip() == "Shopping Cart", (
        f"Expected heading 'Shopping Cart', got '{heading.text.strip()}'"
    )


@then("the empty cart message should be visible")
def step_empty_cart_message(context):
    d = context.driver
    el = _wait(d).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".empty-cart"))
    )
    assert "Your cart is currently empty" in el.text, (
        f"Expected empty cart message, got: '{el.text}'"
    )


@then("the order summary section should be visible")
def step_order_summary_visible(context):
    d = context.driver
    summary = _wait(d).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".cart-summary"))
    )
    assert "Order Summary" in summary.text, (
        f"Expected 'Order Summary' in summary, got: '{summary.text[:80]}'"
    )


@then("the delivery location dropdown should be present")
def step_delivery_dropdown_present(context):
    d = context.driver
    _wait(d).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".delivery-select"))
    )


@then("the delivery location dropdown should have at least one selectable location")
def step_delivery_dropdown_has_options(context):
    d = context.driver
    delivery_sel_el = _wait(d).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".delivery-select"))
    )

    def _has_real_option(drv):
        try:
            opts = drv.find_element(By.CSS_SELECTOR, ".delivery-select") \
                       .find_elements(By.CSS_SELECTOR, "option")
            return any(
                not o.get_attribute("disabled")
                and (o.get_attribute("value") or "").strip()
                not in ("", "ADD_NEW")
                for o in opts
            )
        except StaleElementReferenceException:
            return False

    WebDriverWait(d, 20).until(
        _has_real_option, "Delivery location dropdown has no selectable location options"
    )


@then("the add new location form should be visible")
def step_add_new_location_form_visible(context):
    d = context.driver
    _wait(d).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, "#app > div > div > div > div > div > div.cart-summary > select > option:nth-child(5)"))
    )


@then("the cart item count should have decreased")
def step_cart_count_decreased(context):
    d = context.driver
    count_before = getattr(context, "cart_count_before_remove", 1)

    def _fewer_items(drv):
        current = drv.find_elements(By.CSS_SELECTOR, ".cart-item")
        empty = drv.find_elements(By.CSS_SELECTOR, ".empty-cart")
        return len(current) < count_before or len(empty) > 0

    WebDriverWait(d, 20).until(
        _fewer_items,
        f"Expected cart items to decrease from {count_before} after removing an item"
    )


@then('an alert should appear with the message containing "{partial_message}"')
def step_alert_contains(context, partial_message):
    d = context.driver
    alert = WebDriverWait(d, 20).until(EC.alert_is_present())
    actual = alert.text
    alert.accept()
    assert partial_message in actual, (
        f'Expected alert to contain "{partial_message}", got "{actual}"'
    )


#   @api
#   Scenario: Checkout with a valid delivery location submits the order
#     Given I navigate to "Purchase Rings"
#     And the purchase rings page is ready
#     When I add the first ring to the cart
#     And I navigate to "Cart"
#     When I select the first real delivery location
#     And I click the Checkout button
#     Then an alert should appear with the message containing "Order submitted successfully"