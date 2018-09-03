Feature: BE - Verify Login on BE page

Scenario: BE001-Login-Login to page successful
    Given User login BE page with valid email and password
    Then  Verify BE page is displayed correctly

Scenario: BE002-Login-Login to page unsuccessful
    Given User uses test data: CSV files "be\BE002.csv", delimiter ",", at row "1"
    And   User login BE page with invalid format email
    Then  Verify Error message is displayed
    Given User login BE page with blank email and password
    Then  Verify Warning message is displayed