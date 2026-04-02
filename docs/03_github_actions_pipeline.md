# GitHub Actions Pipeline Explained

The pipeline is defined in `.github/workflows/selenium.yml`. Here is the full file with a line-by-line explanation.

---

## Full YAML

```yaml
name: Selenium Tests

on:
  push:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Setup Java 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'

      - name: Install Chrome
        uses: browser-actions/setup-chrome@v1
        with:
          chrome-version: stable

      - name: Install Dependencies
        run: mvn clean install -DskipTests

      - name: Run Selenium Tests
        run: mvn test
```

---

## Line-by-Line Breakdown

### `name: Selenium Tests`
The display name shown in the GitHub Actions UI and on the build badge.

---

### `on: push: branches: [main]`
The **trigger**. This workflow runs whenever code is pushed to the `main` branch. Pull requests to other branches do not trigger it.

---

### `jobs: test:`
Defines a job named `test`. A workflow can have multiple jobs that run in parallel or in sequence. This project needs only one.

---

### `runs-on: ubuntu-latest`
The **runner** — the type of virtual machine GitHub spins up to execute the job. `ubuntu-latest` is a clean Ubuntu Linux VM. It is destroyed after the job completes.

---

### Step 1 — `actions/checkout@v4`
Clones the repository into the runner's working directory. Without this step, the runner would have no access to your code.

---

### Step 2 — `actions/setup-java@v4`
Installs Java on the runner. `distribution: temurin` uses the Eclipse Temurin JDK (the most widely used open-source JDK distribution). `java-version: '17'` matches the version declared in `pom.xml`.

---

### Step 3 — `browser-actions/setup-chrome@v1`
Installs the Chrome browser. `chrome-version: stable` installs the latest stable release. This is necessary because the runner does not have Chrome pre-installed, and Selenium requires it to launch tests.

---

### Step 4 — `mvn clean install -DskipTests`
Downloads all Maven dependencies (Selenium, TestNG, WebDriverManager) and compiles the project. `-DskipTests` skips test execution here — tests run in the dedicated next step. Separating install from test makes failures easier to diagnose.

---

### Step 5 — `mvn test`
Runs the test suite via the Surefire plugin, which reads `testing.xml` to find and execute `LoginTest`. The `CI=true` environment variable is set automatically by GitHub Actions, which triggers headless Chrome mode in the test code.

---

## Viewing Results

After a push:
1. Go to your repository on GitHub
2. Click the **Actions** tab
3. Select the latest workflow run
4. Expand the **Run Selenium Tests** step to see per-test output

The build badge in the README updates automatically after each run.

---

## Environment Variables Set by GitHub Actions

| Variable | Value | Used by |
|----------|-------|---------|
| `CI` | `"true"` | `LoginTest.java` — enables headless Chrome |
| `GITHUB_WORKSPACE` | Path to checked-out repo | Maven working directory |
| `JAVA_HOME` | Path to installed JDK | Maven |
