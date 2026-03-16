Feature: Customer dropdown
  As a user opening the home page
  I want to see a customer dropdown
  So that I can select a customer

  Scenario: Default option shows "Select Customer"
    Given the app is running
    When I open the home page
    Then the customer dropdown default is "Select Customer"
  Scenario: Customer dropdown is rendered and functional
    Given I am on the home page
    When I click the customer dropdown
    Then the dropdown should be visible and contain options