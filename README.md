# test-automation
A sample  set of CRUD based tests using Rest Assured farmework running on test-ng.
SampleTest.java contains tests for the Application resource based tests .

Refer pom.xml for various dependencies.

To run these tests invoke mvn test after changing the HCM URL.

These tests are based on ARA hence you would need a HCM Ultimate edition to run these against.

The following are the sequence of Tests
  1. Invoking the list of applications and asserting that the no. of applications is 10(This will vary in your instance).
  2. Creating a new application by invoking Post url.
  3. Getting the application and verifying each atrribute.
  4. Verify that the list of applications is incremented to 11.
  5. Delete the Application 
  6. Invoking the list of applications and asserting that the no. of applications is 10(This will vary in your instance).
  
