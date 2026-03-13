Feature: Customer dropdown default
  As a user opening the home page
  I want to see a default placeholder in the customer dropdown
  So that I know I must select a customer

  Scenario: Default option shows "Select Customer"
    Given the app is running
    When I open the home page
    Then the customer dropdown default is "Select Customer"
