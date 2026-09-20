package stepdefinitions;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import pojo.Book_details;

public class BooksStepDef {

    private Response response;
    private List<Book_details> books;

    @Given("I send a GET request to the books endpoint")
    public void iSendGetRequestToBooksEndpoint() {

        response =
                given()
                        .baseUri("https://simple-books-api.click/")
                        .when()
                        .get("/books");

        // Deserialize JSON array into List<Book>
        books = response.as(new TypeRef<List<Book_details>>() {});
        System.out.println(response.asPrettyString());

    }

    @Then("the response status code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {

        int actualStatusCode = response.getStatusCode();
        assertEquals(
                expectedStatusCode,
                response.getStatusCode()
        );
        System.out.println(
                "✅ Status code validation successful! Expected: "
                        + expectedStatusCode
                        + ", Actual: "
                        + actualStatusCode
        );
    }

    @And("the response time should be optimal")
    public void responseTimeCheck()
    {
        System.out.println("✅ GET response time is : "+response.getTime()+ "ms");
    }

    @And("the response should contain {int} books")
    public void responseShouldContainBooks(int expectedCount) {

        int actualSize =  books.size();

        assertEquals(
                expectedCount,
                books.size()
        );
        System.out.println(
                "✅ count validation successful! Expected: "
                        + expectedCount
                        + ", Actual: "
                        + actualSize
        );
    }

    @And("the book with id {int} should have:")
    public void bookWithIdShouldHave(
            int bookId,
            DataTable dataTable) {

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

        assertEquals(
                data.get("name"),
                book.getName()
        );
        System.out.println("The Book name is:"+ book.getName());

        assertEquals(
                data.get("type"),
                book.getType()
        );
        System.out.println("The Book Type is:"+ book.getType());

        assertEquals(
                Boolean.parseBoolean(data.get("available")),
                book.isAvailable()
        );
        System.out.println("The Book is:"+ book.isAvailable());


    }
}