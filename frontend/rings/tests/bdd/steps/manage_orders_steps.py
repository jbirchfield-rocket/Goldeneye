from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


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
    d = context.driver
    empty = WebDriverWait(d, 15).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".empty-orders"))
    )
    assert "No orders found" in empty.text, (
        f"Expected 'No orders found' text, got: '{empty.text}'"
    )


@given("the empty orders message is visible")
def step_given_empty_orders(context):
    d = context.driver
    WebDriverWait(d, 15).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".empty-orders"))
    )


@when("I click the Start Shopping link")
def step_click_start_shopping(context):
    d = context.driver
    link = WebDriverWait(d, 10).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".empty-orders .shop-link"))
    )
    link.click()


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
    order_id_el = WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card .order-id"))
    )
    assert order_id_el.text.strip(), "Expected order ID cell to contain text, but it was empty"


@then("the first order card should display ring type material width stone and quantity")
def step_order_card_has_ring_details(context):
    d = context.driver
    WebDriverWait(d, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".order-card .order-row"))
    )
    first_row = d.find_element(By.CSS_SELECTOR, ".order-card .order-row")
    row_text = first_row.text
    for label in ("Ring Type:", "Material:", "Width:", "Stone:", "Quantity:"):
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
