Feature: Validate Books API

  Scenario: Validate books returned by the API
    Given I send a GET request to the books endpoint
    Then the response status code should be 200
    And  the response time should be optimal
    And the response should contain 6 books
    And the book with id 1 should have:
      | name      | The Russian |
      | type      | fiction     |
      | available | true        |
    And the book with id 2 should have:
      | name      | Just as I Am |
      | type      | non-fiction  |
      | available | false        |
    And the following books should have:
      | id | name         | type        | available |
      | 1  | The Russian  | fiction     | true      |
      | 2  | Just as I Am | non-fiction | false     |


