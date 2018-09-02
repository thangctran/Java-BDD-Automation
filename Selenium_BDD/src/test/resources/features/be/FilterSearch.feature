Feature: FE - Verify filter search functionality on FE page

Scenario: FE002-Tours - Verify Tours Filter
    Given User uses test data: CSV files "fe\\FE002.csv", delimiter ",", at row "1"
    And   User navigates to "Tours" page
    When  User set Price Range to filter Tours
    And   User filter search Tours
    Then  Tour Type each Tours displayed correctly

Scenario: FE003-Cars - Verify Cars Filter
    Given User uses test data: CSV files "fe\\FE003.csv", delimiter ",", at row "1"
    And   User navigates to "Cars" page
    When  User set Price Range to filter Cars
    And   User filter search Cars
    Then  Verify green Airport Pickup button for each Cars

Scenario: FE005-Hotels - Verify Hotels Filter
    Given User uses test data: CSV files "fe\\FE005.csv", delimiter "\t", at row "1"
    And   User navigates to "Hotels" page
    When  User set Price Range to filter Hotels
    And   User filter search Hotels
    Then  Verify Number Star of each Hotels displayed correctly