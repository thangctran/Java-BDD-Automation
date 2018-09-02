Feature: BE - Verify Hotels Management functionality

Scenario: BE006-Hotels-Upload gallery
    Given User uses test data: CSV files "be\\BE006.csv", delimiter ",", at row "1"
    And   User navigates to "Hotels" page
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User set Price Range to filter Hotels
    And   User filter search Hotels
    Then  Number Star of each Hotels displayed correctly

Scenario: BE007-Hotels-Delete Hotels by icon
    Given User uses test data: CSV files "be\\BE007.csv", delimiter ",", at row "1"
    And   User navigates to "Hotels" page
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User deletes Hotel by Icon
    Then  Verify User deleted a Hotel

Scenario: BE008-Hotels-Delete Hotels by button
    Given User uses test data: CSV files "be\\BE008.csv", delimiter ",", at row "1"
    And   User navigates to "Hotels" page
    When  User select main-menu "Hotels" -> sub-menu "Hotels"
    And   User deletes Hotel by Button
    Then  Verify User deleted a Hotel