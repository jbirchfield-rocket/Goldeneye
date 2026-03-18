from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from urllib.parse import urlparse
import re
import time


PAGES = {
    "Home": {
        "path": "/",
        "heading": "Welcome to Goldeneye", 
    },
    "Purchase Rings": {
        "path": "/purchase-rings",
        "heading": "Purchase Rings",
    },
    "Manage Orders": {
        "path": "/manage-orders",
        "heading": "Manage Orders",
    },
    "About Us": {
        "path": "/about-us",
        "heading": "About Us",
    },
    "Cart": {
        "path": "/cart",
        "heading": "Shopping Cart",
        "link_selector": ".cart-link",
    },
}

def _wait_for_app(context):
    d = context.driver
    WebDriverWait(d, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, "#app")))

def _wait_visible_clickable(d, locator):
    el = WebDriverWait(d, 10).until(EC.presence_of_element_located(locator))
    WebDriverWait(d, 5).until(EC.visibility_of(el))
    WebDriverWait(d, 5).until(EC.element_to_be_clickable(locator))
    return el

def _assert_url_path_is(context, expected_path: str):
    d = context.driver
    def _path_matches():
        try:
            current_path = urlparse(d.current_url).path.rstrip("/") or "/"
            expected = expected_path.rstrip("/") or "/"
            return current_path == expected
        except Exception:
            return False
    WebDriverWait(d, 10).until(lambda _ : _path_matches(), f"URL path did not become {expected_path!r}")

def _assert_page_heading_contains(context, expected_text: str):
    d = context.driver
    locator = (By.CSS_SELECTOR, "h1, h2")
    el = WebDriverWait(d, 10).until(EC.presence_of_element_located(locator))
    headers = d.find_elements(*locator)
    visible_texts = [h.text.strip() for h in headers if h.is_displayed()]
    haystack = " | ".join(visible_texts).lower()
    assert expected_text.lower() in haystack, f"Expected heading to include {expected_text!r}, got: {visible_texts}"


@given("I open the home page")
def step_open_home(context):
    d = context.driver
    d.get(context.base_url)
    _wait_for_app(context)

@given('I navigate to "{page_name}"')
@when('I navigate to "{page_name}"')
def step_navigate_to(context, page_name):
    d = context.driver
    assert page_name in PAGES, f"Unknown page name {page_name!r}. Add it to PAGES."
    meta = PAGES[page_name]

    if "link_selector" in meta:
        link_el = _wait_visible_clickable(d, (By.CSS_SELECTOR, meta["link_selector"]))
    else:
        link_xpaths = [
            f"//a[normalize-space()='{page_name}']",
            f"//*[@role='link' and normalize-space()='{page_name}']"
        ]
        link_el = None
        last_err = None
        for xp in link_xpaths:
            try:
                link_el = _wait_visible_clickable(d, (By.XPATH, xp))
                break
            except Exception as e:
                last_err = e
        if link_el is None:
            raise AssertionError(f"Could not find nav link for {page_name!r}. Last error: {last_err}")

    link_el.click()
    _assert_url_path_is(context, meta["path"])

@then('I should be on the "{page_name}" page')
def step_on_page(context, page_name):
    assert page_name in PAGES, f"Unknown page name {page_name!r}. Add it to PAGES."
    meta = PAGES[page_name]

    _assert_url_path_is(context, meta["path"])


@when("I click the site logo")
def step_click_logo(context):
    d = context.driver
    logo_link = _wait_visible_clickable(d, (By.CSS_SELECTOR, "nav a[href='/'] img.logo"))
    logo_link.click()


@given("the purchase rings page is ready")
def step_purchase_rings_ready(context):
    d = context.driver
    WebDriverWait(d, 15).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, ".ring-card"))
    )


@when("I add the first ring to the cart")
def step_add_first_ring(context):
    d = context.driver
    btn = WebDriverWait(d, 10).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, ".ring-card:first-child .add-to-cart-btn"))
    )
    btn.click()
    WebDriverWait(d, 5).until(EC.alert_is_present()).accept()


@then("the cart badge should show 1")
def step_cart_badge_shows_1(context):
    d = context.driver
    badge = WebDriverWait(d, 10).until(
        EC.visibility_of_element_located((By.CSS_SELECTOR, ".cart-badge"))
    )
    assert badge.text.strip() == "1", (
        f"Expected cart badge to show '1', got '{badge.text.strip()}'"
    )