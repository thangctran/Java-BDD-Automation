Feature: FE - Verify Login on FE page

Scenario: FE001-Login - Login successful
    Given User navigates to "Front-end" page
    When  User select main-menu "My Account" -> sub-menu " Login"
    Then  Verify Login FE page is displayed
    Given User login FE page with valid email and password
    Then  My Account page is displayed