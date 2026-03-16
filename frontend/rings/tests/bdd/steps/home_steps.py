from multiprocessing import context

from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import StaleElementReferenceException
from selenium.webdriver.support import expected_conditions as EC

def _get_selected_option_text(driver, select_css):
    select_el = driver.find_element(By.CSS_SELECTOR, select_css)
    selected = select_el.find_elements(By.CSS_SELECTOR, "option:checked")
    if selected:
        return selected[0].text.strip()
    for opt in select_el.find_elements(By.CSS_SELECTOR, "option"):
        if opt.is_selected():
            return opt.text.strip()
    return ""

from selenium.common.exceptions import StaleElementReferenceException

def _wait_for_select_ready(driver, css, min_options=2, open_first=False):
    """
    Waits until the <select> is present, visible, enabled, and has at least `min_options`
    <option> elements (e.g., placeholder + 1 real option). Optionally clicks the select first.
    Returns a *freshly located* select element (avoid stale refs).
    """
    select_locator = (By.CSS_SELECTOR, css)
    el = WebDriverWait(driver, 20).until(EC.presence_of_element_located(select_locator))
    WebDriverWait(driver, 20).until(EC.visibility_of(el))
    WebDriverWait(driver, 20).until(EC.element_to_be_clickable(select_locator))

    if open_first:
        try:
            el.click()
        except StaleElementReferenceException:
                el = WebDriverWait(driver, 10).until(EC.presence_of_element_located(select_locator))
                el.click()

    def _options_ready():
        try:
            sel = driver.find_element(*select_locator)
            opts = sel.find_elements(By.CSS_SELECTOR, "option")

            if len(opts) < min_options:
                return False
            for o in opts:
                text = (o.text or "").strip()
                disabled = o.get_attribute("disabled")
                val = (o.get_attribute("value") or "").strip()
                if disabled:
                    continue
                if val == "" and text.lower() in {"select customer", "select...", "select"}:
                    continue
                if text:
                    return True
            return False
        except StaleElementReferenceException:
            return False

    WebDriverWait(driver, 20).until(lambda d: _options_ready(), "Timed out waiting for options to populate")

    return driver.find_element(*select_locator)


def _first_real_option(select_el):
    for opt in select_el.find_elements(By.CSS_SELECTOR, "option"):
        text = (opt.text or "").strip()
        disabled_attr = opt.get_attribute("disabled")
        val = (opt.get_attribute("value") or "").strip()
        if disabled_attr:
            continue
        if val == "" and text.lower() in {"select customer", "select...", "select"}:
            continue
        if text:
            return opt, text
    return None, None



@given("the app is running")
def step_app_running(context):
    pass

@given("I am on the home page")
def step_i_am_on_home(context):
    d = context.driver
    d.get(context.base_url)
    WebDriverWait(d, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, "#app")))

@when("I open the home page")
def step_open_home(context):
    d = context.driver
    d.get(context.base_url)
    WebDriverWait(d, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, "#app")))

@then('the customer dropdown default is "{expected}"')
def step_dropdown_default(context, expected):
    d = context.driver
    select_css = "#customer-select"
    el = WebDriverWait(d, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, select_css)))
    WebDriverWait(d, 5).until(EC.visibility_of(el))
    WebDriverWait(d, 5).until(EC.element_to_be_clickable((By.CSS_SELECTOR, select_css)))

    actual = _get_selected_option_text(d, select_css)
    if actual != expected:
        raise AssertionError(f'Expected default dropdown text "{expected}", got "{actual}"')

@when("I select the first customer option in the customer drop down")
def step_select_first_customer(context):
    d = context.driver
    select_css = "#customer-select"

    select_el = _wait_for_select_ready(d, select_css, min_options=2, open_first=False)

    first_opt, first_text = _first_real_option(select_el)
    if not first_opt or not first_text:
        raise AssertionError("Could not find a selectable customer option in the dropdown.")

    sel = Select(select_el)
    try:
        sel.select_by_visible_text(first_text)
    except StaleElementReferenceException:
        select_el = _wait_for_select_ready(d, select_css, min_options=2, open_first=False)
        sel = Select(select_el)
        first_opt, first_text = _first_real_option(select_el)
        sel.select_by_visible_text(first_text)

    d.execute_script(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        select_el
    )
    context.selected_first_customer_text = first_text

@then("the first customer option is the value of the drop down")
def step_first_customer_is_selected(context):
    d = context.driver
    select_css = "#customer-select"

    expected = (getattr(context, "selected_first_customer_text", "") or "").strip()
    assert expected, "Missing expected value from context"

    actual_text = _get_selected_option_text(d, select_css)
    assert actual_text == expected, f'Expected dropdown selection "{expected}", got "{actual_text}"'

# Scenario: Correct customer is selected when drop down option is chosen
#     Given I am on the home page
#     When I select the first customer option in the customer drop down
#     Then the first customer option is the value of the drop down