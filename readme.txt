
Project Structure
Lib_Books_APITest/
│
├── .github/
│   └── workflows/
│       └── api-tests.yml
│
├── .idea/
│
├── src/
│   └── test/
│       └── java/
│           ├── features/
│           │   └── Lib_Books.feature
│           │
│           ├── pojo/
│           │   └── Book_details.java
│           │
│           ├── resources/
│           │   └── cucumber.properties
│           │
│           ├── runners/
│           │   └── TestRunner.java
│           │
│           └── stepdefinitions/
│               └── BooksStepDef.java
│
├── target/
│   ├── generated-test-sources/
│   ├── maven-archiver/
│   └── maven-status/
│
└── pom.xml

📌 Folder/File Description
Path	                    Description
src/test/java/features  Contains Cucumber feature files describing API test scenarios in Gherkin syntax.
Lib_Books.feature	    Defines book API test scenarios such as validating books returned by the API.
pojo	                Contains Java POJO classes used to represent API request/response data.
Book_details.java	    Model class representing book details.
resources	            Contains test configuration files.
cucumber.properties	    Cucumber-related configuration.
runners	                Contains Cucumber/JUnit test runner classes.
TestRunner.java	        Entry point for executing Cucumber tests.
stepdefinitions	        Contains implementations of the steps defined in feature files.
BooksStepDef.java	    Java step definitions for the book API scenarios.
.github/workflows	    GitHub Actions CI/CD configuration.
api-tests.yml	        Workflow for automatically running API tests in GitHub Actions.
target	                Maven-generated build/test output. Normally not committed to Git.
pom.xml	                Maven project configuration containing dependencies and plugins.

🧪 Test Flow
Lib_Books.feature
       ↓
TestRunner.java
       ↓
BooksStepDef.java
       ↓
API Request
       ↓
API Response
       ↓
Book_details.java
       ↓
Assertions / Validation

▶️ Running Tests
Run all tests using Maven:

mvn clean test

Or run the tests directly from TestRunner.java in IntelliJ IDEA.

🔄 CI/CD
The .github/workflows/api-tests.yml file can be used to execute the API test suite automatically through GitHub Actions.

🛠️ Technologies
Java

Maven

Cucumber BDD

JUnit

REST API testing

POJO-based response mapping

GitHub Actions