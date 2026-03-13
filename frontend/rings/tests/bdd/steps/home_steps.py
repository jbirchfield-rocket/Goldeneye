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

@given("the app is running")
def step_app_running(context):
    # Server is started outside (vite preview).
    pass

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