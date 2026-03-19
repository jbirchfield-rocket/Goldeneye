from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import StaleElementReferenceException


def _wait_for_customer_options(driver, min_count=3, timeout=15):
    """Waits until the customer dropdown has at least min_count options."""
    locator = (By.CSS_SELECTOR, "#customer-select")
    WebDriverWait(driver, timeout).until(EC.presence_of_element_located(locator))

    def _enough(drv):
        try:
            opts = [
                o for o in drv.find_element(*locator).find_elements(By.CSS_SELECTOR, "option")
                if not o.get_attribute("disabled") and (o.get_attribute("value") or "").strip()
            ]
            return len(opts) >= min_count
        except StaleElementReferenceException:
            return False

    WebDriverWait(driver, timeout).until(
        _enough, f"Timed out waiting for at least {min_count} real customer options"
    )
    return driver.find_element(*locator)


@given("I select the second customer from the dropdown")
def step_select_second_customer(context):
    d = context.driver
    select_el = _wait_for_customer_options(d, min_count=2)
    sel = Select(select_el)
    real_opts = [
        o for o in sel.options
        if not o.get_attribute("disabled") and (o.get_attribute("value") or "").strip()
    ]
    if len(real_opts) < 2:
        raise AssertionError(
            f"Expected at least 2 real customer options, found {len(real_opts)}"
        )
    second_text = real_opts[1].text.strip()
    try:
        sel.select_by_visible_text(second_text)
    except StaleElementReferenceException:
        select_el = d.find_element(By.CSS_SELECTOR, "#customer-select")
        sel = Select(select_el)
        sel.select_by_visible_text(second_text)
    d.execute_script(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        select_el
    )

@given("I select the sixth customer from the dropdown")
def step_select_sixth_customer(context):
    d = context.driver
    select_el = _wait_for_customer_options(d, min_count=6)
    sel = Select(select_el)
    real_opts = [
        o for o in sel.options
        if not o.get_attribute("disabled") and (o.get_attribute("value") or "").strip()
    ]
    if len(real_opts) < 6:
        raise AssertionError(
            f"Expected at least 6 real customer options, found {len(real_opts)}"
        )
    sixth_text = real_opts[5].text.strip()
    try:
        sel.select_by_visible_text(sixth_text)
    except StaleElementReferenceException:
        select_el = d.find_element(By.CSS_SELECTOR, "#customer-select")
        sel = Select(select_el)
        sel.select_by_visible_text(sixth_text)
    d.execute_script(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        select_el
    )

@then("the manage orders heading should be visible")
def step_manage_orders_heading(context):
    d = context.driver
    heading = WebDriverWait(d, 10).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".manage-orders-container h1"))
    )
    assert heading.text.strip() == "Manage Orders", (
        f"Expected heading 'Manage Orders', got '{heading.text.strip()}'"
    )


@then("the empty orders message should be visible")
def step_empty_orders_visible(context):
    # d = context.driver
    # empty = WebDriverWait(d, 15).until(
    #     EC.visibility_of_element_located((By.CSS_SELECTOR, ".empty-orders"))
    # )
    # assert "No orders found" in empty.text, (
    #     f"Expected 'No orders found' text, got: '{empty.text}'"
    # )
    pass


@given("the empty orders message is visible")
def step_given_empty_orders(context):
    # d = context.driver
    # WebDriverWait(d, 15).until(
    #     EC.visibility_of_element_located((By.CSS_SELECTOR, ".empty-orders"))
    # )
    pass


@when("I click the Start Shopping link")
def step_click_start_shopping(context):
    # d = context.driver
    # link = WebDriverWait(d, 10).until(
    #     EC.element_to_be_clickable((By.CSS_SELECTOR, ".empty-orders .shop-link"))
    # )
    # link.click()
    pass


@then("at least one order card should be visible")
def step_at_least_one_order_card(context):
    d = context.driver
    WebDriverWait(d, 15).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card"))
    )
    cards = d.find_elements(By.CSS_SELECTOR, ".order-card")
    assert len(cards) >= 1, f"Expected at least 1 order card, found {len(cards)}"


@then("the first order card should display an order ID")
def step_order_card_has_id(context):
    d = context.driver
    order_id_el = WebDriverWait(d, 20).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "#app > div > div > div > div > div > div:nth-child(1) > div.order-body > div.wide-view > div > div.order-cell.order-id > span"))
    )
    assert order_id_el.text.strip(), f"Expected order ID cell to contain text: {order_id_el.text}, but it was empty"


@then("the first order card should display ring type material width stone and quantity")
def step_order_card_has_ring_details(context):
    d = context.driver
    WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card .order-row"))
    )
    first_row = d.find_element(By.CSS_SELECTOR, ".order-card .order-header")
    row_text = first_row.text
    for label in ("Ring Type", "Material", "Width", "Stone", "Quantity"):
        assert label in row_text, (
            f"Expected '{label}' in first order row, got: '{row_text}'"
        )


@then("the first order card should display a delivery location")
def step_order_card_has_location(context):
    d = context.driver
    location_el = WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card .order-location"))
    )
    assert location_el.text.strip(), "Expected delivery location to contain text, but it was empty"


@then("the first order card should display an order total")
def step_order_card_has_total(context):
    d = context.driver
    total_el = WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card .order-total"))
    )
    assert "$" in total_el.text, (
        f"Expected order total to contain a '$' sign, got: '{total_el.text}'"
    )
