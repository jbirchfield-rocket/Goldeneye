Feature: Manage Orders page
  As a user on the Manage Orders page
  I want to view my order history
  So that I can track my purchases

  Background:
    Given the app is running
    And I am on the home page
    And I select the first customer from the dropdown
    And I navigate to "Manage Orders"

  Scenario: Manage Orders page displays the correct heading
    Then the manage orders heading should be visible

  Scenario: Empty state is shown when no orders are available
    Then the empty orders message should be visible

  Scenario: Empty state has a link that navigates to Purchase Rings
    Given the empty orders message is visible
    When I click the Start Shopping link
    Then I should be on the "Purchase Rings" page

  @api
  Scenario: Order cards are displayed when the API returns data
    Then at least one order card should be visible

  @api
  Scenario: Each order card shows the order ID
    Then the first order card should display an order ID

  @api
  Scenario: Each order card shows ring details
    Then the first order card should display ring type material width stone and quantity

  @api
  Scenario: Each order card shows the delivery location
    Then the first order card should display a delivery location

  @api
  Scenario: Each order card shows an order total
    Then the first order card should display an order total
