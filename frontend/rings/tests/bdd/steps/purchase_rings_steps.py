from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import StaleElementReferenceException


def _wait_for_any_customer_option(driver, timeout=15):
    """Polls until the customer dropdown has at least one real (non-placeholder) option."""
    locator = (By.CSS_SELECTOR, "#customer-select")
    WebDriverWait(driver, timeout).until(EC.presence_of_element_located(locator))

    def _has_option(drv):
        try:
            sel = drv.find_element(By.CSS_SELECTOR, "#customer-select")
            for opt in sel.find_elements(By.CSS_SELECTOR, "option"):
                if opt.get_attribute("disabled"):
                    continue
                if (opt.get_attribute("value") or "").strip() == "":
                    continue
                if (opt.text or "").strip():
                    return True
            return False
        except StaleElementReferenceException:
            return False

    WebDriverWait(driver, timeout).until(
        _has_option, "Timed out waiting for customer options to be available"
    )
    return driver.find_element(By.CSS_SELECTOR, "#customer-select")


def _select_nth_option_in(driver, select_id, index, timeout=10):
    """
    Selects the option at `index` (0-based) in the first <select> with the given ID.
    Waits until at least index+1 options exist, then selects by index.
    Returns (value, visible_text) of the chosen option.
    """
    locator = (By.ID, select_id)
    WebDriverWait(driver, timeout).until(EC.presence_of_element_located(locator))

    def _enough_options(drv):
        try:
            return len(Select(drv.find_element(*locator)).options) > index
        except StaleElementReferenceException:
            return False

    WebDriverWait(driver, timeout).until(
        _enough_options, f"Select #{select_id} never got more than {index} option(s)"
    )

    select_el = driver.find_element(*locator)
    sel = Select(select_el)
    opt = sel.options[index]
    opt_text = (opt.text or "").strip()
    opt_value = (opt.get_attribute("value") or "").strip()
    sel.select_by_index(index)
    driver.execute_script(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        select_el
    )
    return opt_value, opt_text


@given("I select the first customer from the dropdown")
def step_select_first_customer_background(context):
    d = context.driver
    select_el = _wait_for_any_customer_option(d)
    sel = Select(select_el)
    for opt in sel.options:
        if opt.get_attribute("disabled"):
            continue
        val = (opt.get_attribute("value") or "").strip()
        if val == "":
            continue
        text = (opt.text or "").strip()
        if text:
            try:
                sel.select_by_visible_text(text)
            except StaleElementReferenceException:
                select_el = d.find_element(By.CSS_SELECTOR, "#customer-select")
                sel = Select(select_el)
                sel.select_by_visible_text(text)
            d.execute_script(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                select_el
            )
            return
    raise AssertionError("No selectable customer option found in the dropdown")


@given("the purchase rings page has loaded")
def step_purchase_rings_loaded(context):
    d = context.driver
    WebDriverWait(d, 15).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".ring-card"))
    )


@when("I select the second option in the material dropdown on the first ring")
def step_select_material(context):
    context.selected_material_value, context.selected_material_text = \
        _select_nth_option_in(context.driver, "material-select", 1)


@when("I select the second option in the width dropdown on the first ring")
def step_select_width(context):
    context.selected_width_value, context.selected_width_text = \
        _select_nth_option_in(context.driver, "width-select", 1)


@when("I select the second option in the stone dropdown on the first ring")
def step_select_stone(context):
    context.selected_stone_value, context.selected_stone_text = \
        _select_nth_option_in(context.driver, "stone-select", 1)


@when("I set the quantity to 2 on the first ring")
def step_set_quantity(context):
    _select_nth_option_in(context.driver, "quantity-select", 1)
    context.selected_quantity = 2


@when("I click the Add to Cart button on the first ring")
def step_click_add_to_cart(context):
    d = context.driver
    btn = WebDriverWait(d, 10).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".ring-card:first-child .add-to-cart-btn"))
    )
    btn.click()


@then('an alert should appear with the message "{expected_message}"')
def step_alert_message(context, expected_message):
    d = context.driver
    alert = WebDriverWait(d, 10).until(EC.alert_is_present())
    actual = alert.text
    alert.accept()
    assert actual == expected_message, (
        f'Expected alert message "{expected_message}", got "{actual}"'
    )


@then("at least 2 ring cards should be visible")
def step_multiple_ring_cards(context):
    d = context.driver
    cards = d.find_elements(By.CSS_SELECTOR, ".ring-card")
    assert len(cards) >= 2, f"Expected at least 2 ring cards, found {len(cards)}"


@then("each ring card should have material width stone and quantity dropdowns")
def step_each_card_has_dropdowns(context):
    d = context.driver
    cards = d.find_elements(By.CSS_SELECTOR, ".ring-card")
    for i, card in enumerate(cards):
        for select_id in ("material-select", "width-select", "stone-select", "quantity-select"):
            selects = card.find_elements(By.ID, select_id)
            assert len(selects) >= 1, (
                f"Ring card {i + 1} is missing the #{select_id} dropdown"
            )


@when("I note the proposed price of the first ring")
def step_note_price(context):
    d = context.driver
    price_el = WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".ring-card:first-child .price-display"))
    )
    context.initial_price = price_el.text.strip()


@when("I change the quantity to 3 on the first ring")
def step_set_quantity_3(context):
    _select_nth_option_in(context.driver, "quantity-select", 2)


@then("the proposed price of the first ring should have changed")
def step_price_changed(context):
    d = context.driver
    initial = getattr(context, "initial_price", None)
    assert initial is not None, "No initial price was recorded — did the 'When I note...' step run?"
    price_el = d.find_element(By.CSS_SELECTOR, ".ring-card:first-child .price-display")
    current = price_el.text.strip()
    assert current != initial, (
        f"Expected proposed price to change from '{initial}', but it is still '{current}'"
    )


@then("the cart should contain at least 1 item")
def step_cart_has_items(context):
    d = context.driver
    WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".cart-item"))
    )
    items = d.find_elements(By.CSS_SELECTOR, ".cart-item")
    assert len(items) >= 1, f"Expected at least 1 cart item, found {len(items)}"


@then("the first cart item should show quantity 2")
def step_cart_item_quantity(context):
    d = context.driver
    expected = str(getattr(context, "selected_quantity", 2))
    qty_select = WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".cart-item:last-child .quantity-select"))
    )
    actual = (Select(qty_select).first_selected_option.get_attribute("value") or "").strip()
    assert actual == expected, f"Expected cart item quantity {expected}, got {actual}"


@then("the first cart item should show the selected material value")
def step_cart_item_material(context):
    d = context.driver
    # Cart.vue displays materialTypeName (the text name), not the numeric materialId.
    # Use selected_material_text so the assertion works with both mock and real API data.
    expected = getattr(context, "selected_material_text", None)
    if not expected:
        return
    specs = d.find_element(By.CSS_SELECTOR, ".cart-item:last-child .item-specs")
    assert expected in specs.text, (
        f"Expected material name '{expected}' in cart item specs, got: '{specs.text}'"
    )


@then("the first cart item should show the selected stone value")
def step_cart_item_stone(context):
    d = context.driver
    # Cart.vue displays ringStoneName (the text name), not the numeric stoneId.
    # Use selected_stone_text so the assertion works with both mock and real API data.
    expected = getattr(context, "selected_stone_text", None)
    if not expected:
        return
    specs = d.find_element(By.CSS_SELECTOR, ".cart-item:last-child .item-specs")
    assert expected in specs.text, (
        f"Expected stone name '{expected}' in cart item specs, got: '{specs.text}'"
    )
