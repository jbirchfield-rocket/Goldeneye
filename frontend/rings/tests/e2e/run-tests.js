// Minimal, framework-free Selenium runner.
// You can swap to Mocha/Jest later by replacing this file and the package.json script.


import { Builder, By, until } from 'selenium-webdriver';
import chrome from 'selenium-webdriver/chrome.js';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';


const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Config
const BASE_URL = process.env.BASE_URL || 'http://localhost:5173';
const ARTIFACT_DIR = process.env.ARTIFACT_DIR || path.join(__dirname, 'artifacts');
const SCREENSHOTS_DIR = path.join(ARTIFACT_DIR, 'screenshots');
const LOGS_DIR = path.join(ARTIFACT_DIR, 'logs');

// Ensure artifact dirs exist
fs.mkdirSync(SCREENSHOTS_DIR, { recursive: true });
fs.mkdirSync(LOGS_DIR, { recursive: true });

// Helper to write a log line
function log(line) {
  const stamp = new Date().toISOString();
  fs.appendFileSync(path.join(LOGS_DIR, 'e2e.log'), `[${stamp}] ${line}\n`);
  console.log(line);
}

async function takeScreenshot(driver, name = 'screenshot') {
  try {
    const png = await driver.takeScreenshot();
    const file = path.join(SCREENSHOTS_DIR, `${Date.now()}-${name}.png`);
    fs.writeFileSync(file, png, 'base64');
    log(`Saved screenshot: ${file}`);
  } catch (err) {
    log(`Failed to take screenshot: ${err?.message || err}`);
  }
}

async function main() {
  log(`Starting E2E against ${BASE_URL}`);

  const options = new chrome.Options().addArguments(
    '--headless=new',
    '--no-sandbox',
    '--disable-dev-shm-usage',
    '--disable-gpu',
    '--window-size=1280,800'
  );

  const driver = await new Builder().forBrowser('chrome').setChromeOptions(options).build();

  try {
    // === Smoke test example (customize as you add features) ===
    await driver.get(BASE_URL);
    log('Opened base URL');

    // Wait for the Vue app root element
    await driver.wait(until.elementLocated(By.css('#app')), 10000);
    log('Found #app root');

    // Example: verify page title contains something expected (adjust as needed)
    const title = await driver.getTitle();
    log(`Page title: ${title}`);

    // Add your first real behavior check here:
    // e.g., await driver.findElement(By.css('a[href="/login"]')).click();

    log('E2E smoke passed');
  } catch (err) {
    log(`E2E failed: ${err?.stack || err}`);
    await takeScreenshot(driver, 'failure');
    process.exitCode = 1; // signal failure to CI
  } finally {
    await driver.quit();
    log('Browser closed');
  }
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});