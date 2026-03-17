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
