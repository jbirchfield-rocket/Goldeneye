Feature: Manage Orders page
  As a user on the Manage Orders page
  I want to view my order history
  So that I can track my purchases

  Background:
    Given the app is running
    And I am on the home page

  @api
  Scenario: Manage Orders page displays the correct heading
    Given I select the first customer from the dropdown
    And I navigate to "Manage Orders"
    Then the manage orders heading should be visible

  @api
  Scenario: Empty state is shown when no orders are available
    Given I select the first customer from the dropdown
    And I navigate to "Manage Orders"
    Then the empty orders message should be visible

  @api
  Scenario: Empty state has a link that navigates to Purchase Rings
    Given I select the first customer from the dropdown
    And I navigate to "Manage Orders"
    And the empty orders message is visible
    When I click the Start Shopping link
    Then I should be on the "Purchase Rings" page

  @api
  Scenario: Order cards are displayed when the API returns data
    Given I select the second customer from the dropdown
    And I navigate to "Manage Orders"
    Then at least one order card should be visible

  @api
  Scenario: Each order card shows the order ID
    Given I select the second customer from the dropdown
    And I navigate to "Manage Orders"
    Then the first order card should display an order ID

  @api
  Scenario: Each order card shows ring details
    Given I select the second customer from the dropdown
    And I navigate to "Manage Orders"
    Then the first order card should display ring type material width stone and quantity

  @api
  Scenario: Each order card shows the delivery location
    Given I select the second customer from the dropdown
    And I navigate to "Manage Orders"
    Then the first order card should display a delivery location

  @api
  Scenario: Each order card shows an order total
    Given I select the second customer from the dropdown
    And I navigate to "Manage Orders"
    Then the first order card should display an order total
