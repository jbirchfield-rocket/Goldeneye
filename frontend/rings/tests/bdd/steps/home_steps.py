from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def _get_selected_option_text(driver, select_css):
    select_el = driver.find_element(By.CSS_SELECTOR, select_css)
    # Prefer :checked (fast path)
    selected = select_el.find_elements(By.CSS_SELECTOR, "option:checked")
    if selected:
        return selected[0].text.strip()
    # Fallback: iterate options
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
    # Step 1: wait until the <select> exists and is interactable
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

    # Step 2: wait for options to be populated
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
    # Server is started outside (vite preview).
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
    select_el = _wait_for_select_ready(d, select_css, min_options=2, open_first=True)

    first_opt, first_text = _first_real_option(select_el)
    if not first_opt or not first_text:
        raise AssertionError("Could not find a selectable customer option in the dropdown.")
    try:
        first_opt.click()
    except StaleElementReferenceException:
        select_el = _wait_for_select_ready(d, select_css, min_options=2, open_first=True)
        first_opt, first_text = _first_real_option(select_el)
        if not first_opt:
            raise AssertionError("Option went stale and could not be re-located after refresh.")
        first_opt.click()

    context.selected_first_customer_text = first_text

@then("the first customer option is the value of the drop down")
def step_first_customer_is_selected(context):
    d = context.driver
    select_css = "#customer-select"
    _ = _wait_for_select_ready(d, select_css, min_options=2, open_first=False)

    actual = _get_selected_option_text(d, select_css)
    expected = (getattr(context, "selected_first_customer_text", "") or "").strip()

    if not expected:
        raise AssertionError("Missing expected value from context; did the When step set it?")
    assert actual == expected, f'Expected dropdown selection "{expected}", got "{actual}"'

