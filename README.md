# Module 2 - CI/CD & DevOps

## PaaS Deployment Link:

### [https://sparkling-georgiana-nathanaeru-46ed09d6.koyeb.app/](https://sparkling-georgiana-nathanaeru-46ed09d6.koyeb.app/)

## Reflection

#### 1) Code quality issue and its fix

Analysis with SonarCloud returns this issue: "Dependencies are not verified because the `verification-metadata.xml` file
is missing. Make sure it is safe here." This is important for security and reliability of the application, as unverified
dependencies can introduce vulnerabilities or instability. To fix this issue, I did this fix in the local repository:

```bash
./gradlew --write-verification-metadata sha256 build   
```

This generates `gradle/verification-metadata.xml` file with the necessary checksums for all dependencies, ensuring that
they are verified during the build process. After generating this file and doing some fixes to mitigate syncing fails
within Gradle, I committed it to the repository and pushed the changes to GitHub. This should resolve the issue in
SonarCloud and improve the security and reliability of the application.

#### 2) Have the current implementation met the definition of CI/CD?

Yes, I believe the current implementation meets the definitions of Continuous Integration and Continuous Deployment (
CI/CD).

CI is successfully achieved through the `ci.yml` and `sonar.yml` workflows in GitHub Actions, which automatically
execute the test suite and perform static code analysis on every push and pull request to verify code integrity before
merging.

CD is established by integrating the repository with Koyeb PaaS, where any changes pushed/pulled to the main branch
trigger an automatic build using the `Dockerfile` and deploy the updated application to production server without manual
intervention. This complete automation ensures that valid code is consistently merged and immediately released to users,
effectively fulfilling the core principles of CI/CD.
