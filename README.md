# Module 1 - Coding Standards

## Reflection 1

I already implemented these coding standards in this projects:

- Clean code principles: clear variable and function names, simple and concise functions, non-redundant use of comments,
  consistent formatting powered by IntelliJ IDEA's auto-formatting features, modular code structure, null safety in
  error handling, and use of getters and setters for encapsulation.
- Secure coding practices: input validation for numeral inputs, use of getter-setters instead of direct field access,
  and use of random UUID instead of sequential numeric IDs for product IDs.

One of the challenges I faced while writing this program is to ensure that the name of HTML templates are the same as
return values of controller methods. This is discovered when I encountered an error while adhering to the module
documentation. To overcome this challenge, I carefully reviewed the code and cross-checked the template names with the
return values of the controller methods to ensure they matched correctly.

## Reflection 2

#### 1) Writing unit tests

Writing unit tests felt exhausting but also productive and clarifying, because I can ensure the program's behavior is
correct and edge cases are handled. Seeing tests pass and catching bugs early provided satisfaction and confidence in
the code's quality.

There's no fixed number of tests we should write per class. The goal is to cover behavior, not implementation. We can
make sure our tests are enough by focusing on testing observable behavior (public methods and outputs), not private
implementation details. Consider all possible inputs, edge cases, and error conditions. This relates to the next
paragraph about code coverage.

Code coverage is a helpful metric that shows which lines/branches were executed during tests. It helps find untested
areas, but it is only a guide. 100% code coverage does not guarantee bug-free code. Coverage only indicates that code
was executed by tests, it does not assert that the behavior is correct for all cases. Tests can execute lines without
asserting the right outcomes.

#### 2) About duplicated functional test class scenario

Duplicating setup and instance variables across functional test classes can be considered as a violation of DRY (Don't
Repeat Yourself) principle and clean code principle in general. It increases maintenance overhead and makes tests
noisier and more brittle. This is because a change to shared wiring or fixtures must be repeated in multiple places, and
repeated boilerplate hides the intent of individual tests.

We can extract shared fixtures and helpers to keep tests clean and focused, by using a small base test (or a
package-level test fixtures), test factories or helper methods to build sample data, and page-object-like helpers for
functional flows. It is recommended to use composition or reusable helpers over copy-paste, and leverage
appropriate test framework (e.g., Spring test annotations) so tests remain readable, maintainable, and resilient to
implementation changes.

