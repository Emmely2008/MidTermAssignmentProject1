# TestAssignmentProject2

## Testing Real Life Code

*You should (as a minimum):* 
 - Explain the purpose of the Test (what the original test exposed, and what your test exposes) 
 - Explain about Parameterized Tests in JUnit and how you have used it in this exercise. 
 - Explain the topic Data Driven Testing, and why it often makes a lot of sense to read test data from a file. 
 - Your answers to the question; whether what you implemented was a Unit Test or a JUnit Test, the problems you might have discovered with the test and, your suggestions for ways this could have been fixed. 
 - The steps you took to include Hamcrest matchers in the project, and the difference they made for the test 
 
 
##### Explain the purpose of the Test (what the original test exposed, and what your test exposes) 
 
The original JUnit test exposed that some inputs failed by logging out failed/correct results from the matching license plate.
A very abstracted JUnit test that didn't capture which tests failed and why they failed. The test wasn't written in a typical best practice style.
One such thing as logging out results instead of using asserts. And even though it was data driven test it didn't make use of JUnits parameterized tests.
 
##### Explain about Parameterized Tests in JUnit and how you have used it in this exercise.

Parameterized tests allow a developer to run the same test over and over again using different values. There are five steps that you need to follow to create a parameterized test.
In JUnit, you can pass the parameters into the JUnit test via the following methods:

 - Constructor
 - Fields injection via @Parameter


Because file "results" already consisted of the *image name* and the *expected license plate* separated by '=' I decided to just convert it to CSV file and use it as a source for my parameterized tests.
 
I pass the parameters into the unit test method via the fiels injector. I use the annotation @CsvFileSource for the the data source. CSV file with delimiter '='.

``` 
	@DisplayName("Checks if any resulting lisence plates are null")
    @ParameterizedTest(name = "{index} => image={0} extpected={1}")
    @CsvFileSource(resources = "/results.csv", delimiter = '=') 
	void checkSignsForNulls(String image_path, String expectedResult) throws Exception 
	
``` 


 
##### Explain the topic Data Driven Testing, and why it often makes a lot of sense to read test data from a file.

*Data Driven Testing*
Term from Wikipedia is: Data-driven testing (DDT) is a term used in the testing of computer software to describe testing done using a table of conditions directly as test inputs and verifiable outputs as well as the process where test environment settings and control are not hard-coded.

This exactly what is needed to improve test coverage – test with different scenarios and different input data without hard-coding the scenario itself, but just feeding different input and expected output data to it.
 
This makes the test very dynamic and fast for change updates and not only the developer of the test can understand and write test input and outputs. 
 
 
##### Your answers to the question; whether what you implemented was a Unit Test or a JUnit Test, the problems you might have discovered with the test and, your suggestions for ways this could have been fixed.
 
 The test are unit test. Unit test concentrate on testing the code from the inside. 

The unit tests are written in the framework JUnit (JUnit 5 to be specific). 
 
 In a the book JUnit in Action. They difference between tree different unit tests.
 
 - Logic unit test. Testing a method in isolation typically by using mocks or stubs.
 
 - Integration unit test focus on testing the interaction between components.

 
 - Functional unit-tests verifies a behavior.

I believe that the tests are of the type functional unit test because we test if the behavior license recognition functions correctly.
 
 
 The problem with having only this unit test is that it doesn't capture where the code fails. This is because the unit test are not written isolated enough.
 *JUnit best practices: unit-test one object at a time
 A vital aspect of unit tests is that they are finely grained. A unit test independently examines each object you create, so that you can isolate problems as soon as they occur.
 If more than one object is put under test, you cannot predict how objects will interact when changes occur to one or the other.
 When an object interacts with other complex objects, you can surround the object under test with predictable test objects.*
 

 
 

##### The steps you took to include Hamcrest matchers in the project, and the difference they made for the test *
 
 
 ##### Executed test cases  
 
 Referencese https://www.mkyong.com/unittest/junit-4-tutorial-6-parameterized-test/
 
 