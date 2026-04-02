# Selenium CI/CD with GitHub Actions

Automated Selenium test suite that runs in a GitHub Actions pipeline on every push — demonstrating continuous integration for QA automation.

[![Selenium Tests](https://github.com/gamaeldin-wol/selenium-ci-cd-github-actions/actions/workflows/selenium.yml/badge.svg)](https://github.com/gamaeldin-wol/selenium-ci-cd-github-actions/actions/workflows/selenium.yml)

---

## What This Project Does

A Selenium WebDriver test runs against [the-internet.herokuapp.com](https://the-internet.herokuapp.com/login) inside a GitHub Actions pipeline. Every time code is pushed to the `main` branch, the pipeline automatically checks out the code, sets up Java, installs dependencies, launches headless Chrome, and executes the test suite.

The badge above shows the current build status — green means the last push passed all tests.

---

## The Pipeline

```
Developer pushes code
        │
        ▼
GitHub detects change on main branch
        │
        ▼
GitHub Actions pipeline starts
        │
        ├── 1. Checkout code from repository
        ├── 2. Set up Java 17 (Temurin distribution)
        ├── 3. Install Chrome browser
        ├── 4. Build project and download dependencies (mvn install -DskipTests)
        └── 5. Run Selenium tests in headless Chrome (mvn test)
        │
        ▼
Results visible in Actions tab (pass/fail)
```

---

## Test Cases

| Test | What It Validates |
|------|-------------------|
| Login page title | Navigates to `/login` and verifies the page heading is "Login Page" |
| Successful login | Enters valid credentials, clicks Login, verifies success message |
| Failed login | Enters invalid credentials, verifies error message appears |

---

## Tech Stack

- **Java 17** — Language
- **Selenium WebDriver 4.18.1** — Browser automation
- **WebDriverManager** — Automatic ChromeDriver binary management
- **TestNG 7.9.0** — Test framework
- **Maven** — Build tool
- **GitHub Actions** — CI/CD pipeline
- **Headless Chrome** — Browser runs without a visible window (required for CI servers)

---

## Project Structure

```
selenium-ci-cd-github-actions/
├── .github/
│   └── workflows/
│       └── selenium.yml                 ← GitHub Actions pipeline definition
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── cicd/
│                   └── LoginTest.java   ← Selenium test class (3 test methods)
├── testing.xml                          ← TestNG suite configuration
├── pom.xml                              ← Maven dependencies
├── docs/
│   ├── 01_cicd_concepts.md              ← What CI/CD is and why QA needs it
│   ├── 02_project_setup.md              ← Maven project + Selenium test creation
│   └── 03_github_actions_pipeline.md    ← Pipeline YAML explained line by line
└── README.md
```

---

## How to Run

**Locally:**

```bash
git clone https://github.com/gamaeldin-wol/selenium-ci-cd-github-actions.git
cd selenium-ci-cd-github-actions
mvn clean test
```

**In the pipeline:** Push any change to `main` — the pipeline runs automatically. Check results in the repository's **Actions** tab.

---

## Key Concepts Demonstrated

**Headless Chrome** allows Selenium tests to run on servers that have no monitor or display. The `--headless` flag tells Chrome to render pages in memory without opening a window. This is mandatory for CI environments like GitHub Actions.

**WebDriverManager** eliminates manual ChromeDriver downloads. It detects the installed Chrome version and downloads the matching driver binary at runtime.

**GitHub Actions** is a CI/CD platform built into GitHub. The pipeline is defined in a YAML file at `.github/workflows/selenium.yml`. It triggers on push events, runs on an Ubuntu virtual machine, and reports pass/fail directly in the GitHub UI.

**The build badge** in this README updates automatically after each pipeline run. Recruiters can see at a glance that your tests are passing.

---

## Documentation

See the [`docs/`](./docs/) folder for detailed explanations:

- [CI/CD Concepts](./docs/01_cicd_concepts.md) — What CI/CD is, why it matters for QA
- [Project Setup](./docs/02_project_setup.md) — Step-by-step Maven project and Selenium test creation
- [GitHub Actions Pipeline](./docs/03_github_actions_pipeline.md) — YAML file explained line by line
