Feature: Customer dropdown
  As a user opening the home page
  I want to see a customer dropdown
  So that I can select a customer

  Scenario: Default option shows "Select Customer"
    Given the app is running
    When I open the home page
    Then the customer dropdown default is "Select Customer"

  Scenario: Correct customer is selected when drop down option is chosen
    Given I am on the home page
    When I select the first customer option in the customer drop down
    Then the first customer option is the value of the drop down
