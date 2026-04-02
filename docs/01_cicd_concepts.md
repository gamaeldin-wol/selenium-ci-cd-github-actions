# CI/CD Concepts for QA Automation

## What is CI/CD?

**Continuous Integration (CI)** is the practice of automatically building and testing code every time a developer pushes a change. The goal is to catch bugs early — before they reach production — by running your test suite on every commit.

**Continuous Delivery/Deployment (CD)** extends CI by automatically deploying passing builds to staging or production environments.

Together, CI/CD creates a fast feedback loop: write code → push → get test results in minutes.

---

## Why QA Engineers Need CI/CD

Without CI/CD, test automation often lives only on a developer's laptop:
- Tests run manually, and only sometimes
- A broken test might go unnoticed for days
- Results are not visible to the whole team

With CI/CD:
- Tests run automatically on every push
- The team sees a green or red status badge immediately
- Failures are caught at the source, not after deployment

---

## How GitHub Actions Works

GitHub Actions is a CI/CD platform built directly into GitHub. You define a **workflow** in a YAML file stored at `.github/workflows/`. GitHub detects this file and runs it automatically based on triggers you define (e.g., a push to `main`).

Each workflow consists of:
- **Triggers** — events that start the workflow (e.g., `push`, `pull_request`)
- **Jobs** — groups of steps that run on a virtual machine
- **Steps** — individual commands or reusable actions

The virtual machine (runner) is a fresh environment every time, so all dependencies must be installed as part of the pipeline.

---

## The CI Feedback Loop

```
Developer pushes code
        │
        ▼
GitHub detects the push
        │
        ▼
GitHub Actions spins up a fresh Ubuntu VM
        │
        ▼
Pipeline installs Java, Chrome, Maven dependencies
        │
        ▼
Selenium tests run against the-internet.herokuapp.com
        │
        ▼
Pass → green badge   |   Fail → red badge + email notification
```

---

## Key Terms

| Term | Definition |
|------|------------|
| **Pipeline** | The automated sequence of steps that runs on every push |
| **Runner** | The virtual machine that executes the pipeline |
| **Workflow** | The YAML file defining the pipeline |
| **Job** | A group of steps that share the same runner |
| **Step** | A single command or action within a job |
| **Headless browser** | A browser that runs without a visible window — required in CI |
| **Build badge** | A live image in the README showing the last pipeline result |
