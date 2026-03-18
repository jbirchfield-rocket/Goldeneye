from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


@then("the About Us main heading should be visible")
def step_about_us_heading(context):
    d = context.driver
    heading = WebDriverWait(d, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//h1[normalize-space()='About Us']"))
    )
    assert heading.is_displayed()


@then("the following section headings should be visible")
def step_section_headings_visible(context):
    d = context.driver
    for row in context.table:
        heading_text = row["heading"].strip()
        el = WebDriverWait(d, 10).until(
            EC.visibility_of_element_located(
                (By.XPATH, f"//h2[normalize-space()='{heading_text}']")
            )
        )
        assert el.is_displayed(), f"Section heading '{heading_text}' was not visible"
