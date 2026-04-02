# Project Setup: Maven Project and Selenium Test

## Prerequisites

- Java 17 (JDK)
- Maven 3.x
- Git
- An IDE (Eclipse, IntelliJ, or VS Code)

---

## Maven Project Structure

This project follows standard Maven layout:

```
selenium-ci-cd-github-actions/
├── src/
│   └── test/
│       └── java/
│           └── com/cicd/
│               └── LoginTest.java
├── testing.xml
└── pom.xml
```

Source code goes under `src/main/java/`. Test code goes under `src/test/java/`. Maven compiles and runs them separately.

---

## Key Dependencies (pom.xml)

### Selenium WebDriver
```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.18.1</version>
</dependency>
```
Provides the WebDriver API for controlling Chrome programmatically.

### WebDriverManager
```xml
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.7.0</version>
</dependency>
```
Automatically downloads the correct `chromedriver` binary at runtime. No manual driver management needed.

### TestNG
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.9.0</version>
    <scope>test</scope>
</dependency>
```
The test framework. Provides `@Test`, `@BeforeMethod`, `@AfterMethod` annotations.

---

## The Test Class

`LoginTest.java` contains three test methods targeting [the-internet.herokuapp.com/login](https://the-internet.herokuapp.com/login):

| Method | What it does |
|--------|-------------|
| `testLoginPageTitle()` | Navigates to `/login`, asserts the `<h2>` heading equals "Login Page" |
| `testSuccessfulLogin()` | Enters valid credentials (`tomsmith` / `SuperSecretPassword!`), asserts success flash message |
| `testFailedLogin()` | Enters invalid credentials, asserts error flash message |

### Headless Chrome Detection

The `setUp()` method checks the `CI` environment variable (set to `"true"` automatically by GitHub Actions):

```java
String ci = System.getenv("CI");
if (ci != null && ci.equals("true")) {
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
}
```

This means tests run with a visible Chrome window locally, and headless in CI — no code changes needed.

---

## TestNG Suite Configuration (testing.xml)

```xml
<suite name="CI/CD Test Suite">
    <test name="Login Tests">
        <classes>
            <class name="com.cicd.LoginTest"/>
        </classes>
    </test>
</suite>
```

The Surefire plugin is configured in `pom.xml` to use this file as the test suite entry point.

---

## Running Tests Locally

```bash
# Clone the repository
git clone https://github.com/gamaeldin-wol/selenium-ci-cd-github-actions.git
cd selenium-ci-cd-github-actions

# Run all tests
mvn clean test
```

Test results appear in `target/surefire-reports/` and `test-output/`.
