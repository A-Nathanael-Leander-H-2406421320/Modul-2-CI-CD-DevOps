<details>

<summary>
<h1>Module 2 - CI/CD & DevOps</h1>
</summary>

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

</details>

<details>
<summary>
<h1>Module 3 - CI/CD & DevOps</h1>
</summary>

## Reflection

**1) Explain what principles you apply to your project!**

In this project, I applied all five SOLID principles to improve the structure and maintainability of the codebase:

* **Single Responsibility Principle (SRP):** I separated `CarController` from `ProductController` into its own dedicated
  file. Now, each controller is strictly responsible for handling HTTP requests for a single entity type. This
  separation ensures each class has only one reason to change, making the code easier to understand and maintain.
* **Open/Closed Principle (OCP):** I introduced generic base interfaces (`ReadService` and `WriteService`) for standard
  CRUD operations. The system is now open for extension (we can add new entities easily by extending these interfaces)
  but closed for modification (we don't need to rewrite the core service structure).
* **Liskov Substitution Principle (LSP):** I removed the `extends ProductController` relationship from the
  `CarController` class. Since a `CarController` is not a true substitute for a `ProductController`, this inheritance
  hierarchy was incorrect and prone to unintended side effects. By decoupling them, we ensure that each controller can
  be used independently without violating the expected behavior of the other.
* **Interface Segregation Principle (ISP):** I split the monolithic service interfaces into smaller, more focused
  interfaces (`ReadService` for fetching data and `WriteService` for modifying data). Clients now only depend on the
  specific behaviors they actually use. This reduces the risk of clients being forced to implement methods they don't
  need and promotes a cleaner separation
* **Dependency Inversion Principle (DIP):** In the controllers, I changed the injected dependencies from concrete
  implementations (e.g., `CarServiceImpl`) to their abstractions (e.g., `CarService`). The high-level controller module
  now depends purely on the interface contract. This allows for greater flexibility and easier testing, as we can swap
  out implementations without modifying the controller code.

**2) Explain the advantages of applying SOLID principles to your project with examples.**

Applying SOLID principles makes the codebase more scalable, predictable, and easier to collaborate on.

* **Easier Maintenance (SRP Example):** By separating `CarController` and `ProductController` into different files,
  multiple developers can work on car-related features and product-related features simultaneously without causing merge
  conflicts in the same file.
* **Safer Contracts (ISP Example):** Segregating read and write operations into `ReadService` and `WriteService`
  improves safety. If a future component only needs to display a list of cars (like a reporting dashboard), it can rely
  solely on `ReadService`. This guarantees at compile-time that the component cannot accidentally delete or modify data,
  as those methods are completely invisible to it.
* **Flexibility (DIP Example):** By depending on the `CarService` interface rather than `CarServiceImpl`, the
  application is much more flexible. If we decide to swap out the in-memory data list for a real database implementation
  later, we simply create a new service implementation. The controller code will not need a single line of modification
  because it only knows about the interface contract.

**3) Explain the disadvantages of not applying SOLID principles to your project with examples.**

Ignoring SOLID principles typically results in tightly coupled, fragile code where a
small change in one place breaks unexpected things somewhere else.

* **Unpredictable Behavior (LSP Violation Example):** When `CarController` improperly extended `ProductController`, it
  silently inherited all of the product-related route mappings and logic. This could easily lead to a scenario where a
  user navigating to a car-related URL accidentally triggers logic meant for products, creating severe bugs that are
  difficult to trace.
* **Code Duplication & Resistance to Change (OCP Violation Example):** Before using generic interfaces, every time a new
  entity was added to the application, we would have to write a brand new service interface with potentially
  inconsistent method names (e.g., `deleteCarById` vs. `deleteProduct`). This makes the codebase bloated and hard to
  standardize, meaning future extensions require significantly more repetitive work.

</details>