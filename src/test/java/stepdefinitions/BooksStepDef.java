package stepdefinitions;
import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


import java.util.List;
import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import pojo.Book_details;

public class BooksStepDef {

    private Response response;
    private List<Book_details> books;

    @Given("I send a GET request to the books endpoint")
    public void iSendGetRequestToBooksEndpoint() {

        //getting the response from book endpoint
        response = given().baseUri("https://simple-books-api.click/")
                        .when().get("/books");

        //Take the JSON response and deserialize it into a Java List containing Book_details objects.
        books = response.as(new TypeRef<List<Book_details>>() {});

        System.out.println(response.asPrettyString());

    }

    @Then("the response status code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {

        int actualStatusCode = response.getStatusCode();

        assertEquals(expectedStatusCode, actualStatusCode);

        System.out.println("Status code validation successful! Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
    }

    @And("the response time should be optimal")
    public void responseTimeCheck()
    {
        System.out.println(" GET response time is : "+response.getTime()+ "ms");
    }

    @And("the response should contain {int} books")
    public void responseShouldContainBooks(int expectedCount) {

        //books object is pointing to deserialized response in to list of book_details object

        int actualSize =  books.size();

        assertEquals(expectedCount, actualSize);
        System.out.println("count validation successful! Expected: " + expectedCount + ", Actual: " + actualSize);
    }

    @And("the book with id {int} should have:")

    public void bookWithIdShouldHave(int bookId, DataTable dataTable) {

        System.out.println("********Details for book ID*********** "+bookId);

        Book_details book = null;

        for (Book_details b : books) {
            if (b.getId() == bookId) {
                book = b;
                break;
            }
        }

        if (book == null) {
            throw new AssertionError("Book with id " + bookId + " not found");
        }

        // Convert DataTable into key/value pairs
        var data = dataTable.asMap(String.class, String.class);

        assertEquals(data.get("name"), book.getName());
        System.out.println("The Book name is:"+ book.getName());

        assertEquals(data.get("type"), book.getType());
        System.out.println("The Book Type is:"+ book.getType());

        assertEquals(Boolean.parseBoolean(data.get("available")), book.isAvailable());
        System.out.println("The Book is:"+ book.isAvailable());


    }
    @Then("the following books should have:")
    public void the_following_books_should_have(DataTable dataTable) {

        //
        List<Map<String, String>> expectedBooks =
                dataTable.asMaps(String.class, String.class);

        //get the list of actual Id's from JSON response & store it in list<Integer>
        List<Integer> actualIds =
                response.jsonPath().getList("id", Integer.class); //extracts all id values from the JSON response and converts them into a Java List<Integer>.


        System.out.println("Actual IDs: " + actualIds);
        System.out.println("API response:");
        //System.out.println(response.asPrettyString());

        for (Map<String, String> expectedBook : expectedBooks) {

            int expectedId = Integer.parseInt(expectedBook.get("id"));
            String expectedName = expectedBook.get("name");
            String expectedType = expectedBook.get("type");
            boolean expectedAvailable = Boolean.parseBoolean(expectedBook.get("available"));

            //index is pointing to book for which we want to assert the response
            int index = actualIds.indexOf(expectedId);

            System.out.println("********Details for book ID*********** "+expectedId);

            assertThat("Book ID not found: " + expectedId, index, Matchers.greaterThanOrEqualTo(0));

            String actualName = response.jsonPath().getString("[" + index + "].name");

            String actualType = response.jsonPath().getString("[" + index + "].type");

            boolean actualAvailable = response.jsonPath().getBoolean("[" + index + "].available");

            assertThat("Mismatch in name for book ID: " + expectedId, actualName, Matchers.equalTo(expectedName));

            System.out.println("The Book name is:"+actualName);

            assertThat("Mismatch in type for book ID: " + expectedId, actualType, Matchers.equalTo(expectedType));
            System.out.println("The Book Type is:"+actualType);

            assertThat("Mismatch in available for book ID: " + expectedId, actualAvailable, Matchers.equalTo(expectedAvailable));
            System.out.println("The Book is:"+ actualAvailable);
        }
    }

}