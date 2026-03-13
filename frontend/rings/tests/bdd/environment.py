import os, time
from pathlib import Path

from selenium import webdriver
from selenium.webdriver.chrome.options import Options

ARTIFACTS = Path(__file__).resolve().parent / "artifacts"
LOGS_DIR = ARTIFACTS / "logs"
SCREENSHOTS_DIR = ARTIFACTS / "screenshots"

def _ensure_clean_dir(d: Path):
    d.mkdir(parents=True, exist_ok=True)
    for p in d.glob("*"):
        if p.is_dir():
            for sub in p.rglob("*"):
                sub.unlink(missing_ok=True)
            p.rmdir()
        else:
            p.unlink(missing_ok=True)

def before_all(context):
    # Prepare artifacts (fresh each run)
    _ensure_clean_dir(LOGS_DIR)
    _ensure_clean_dir(SCREENSHOTS_DIR)

    # Base URL for app
    context.base_url = os.getenv("BASE_URL", "http://localhost:5173")

    # Headless Chrome (Selenium Manager will fetch the driver)
    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    options.add_argument("--window-size=1280,800")

    context.driver = webdriver.Chrome(options=options)
    context.logs_dir = LOGS_DIR
    context.screenshots_dir = SCREENSHOTS_DIR

def after_scenario(context, scenario):
    if scenario.status == "failed":
        ts = int(time.time())
        name = f"{ts}-{scenario.name.replace(' ', '_')}.png"
        path = context.screenshots_dir / name
        try:
            context.driver.save_screenshot(str(path))
        except Exception as e:
            print(f"[e2e] Could not save screenshot: {e}")

def after_all(context):
    try:
        context.driver.quit()
    except Exception:
        pass