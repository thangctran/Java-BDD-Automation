Feature: BE - Verify Hotels Management functionality

Scenario: BE006-Hotels-Upload gallery
    Given User uses test data: CSV files "be\BE006.csv", delimiter ",", at row "1"
    And   User login BE page with valid email and password
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User upload a image to Hotel
    And   User select main-menu "Hotels" -> sub-menu "Hotels"
    Then  Verify Image Number is uploaded

Scenario: BE007-Hotels-Delete Hotels by icon
    Given User uses test data: CSV files "be\BE007.csv", delimiter ",", at row "1"
    And   User login BE page with valid email and password
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User deletes Hotel by Icon
    Then  Verify User deleted a Hotel

Scenario: BE008-Hotels-Delete Hotels by button
    Given User uses test data: CSV files "be\BE008.csv", delimiter ",", at row "1"
    And   User login BE page with valid email and password
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User deletes Hotel by Button
    Then  Verify User deleted a Hotel