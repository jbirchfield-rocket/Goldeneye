Feature: Primary navigation from Home
  As a user
  I want to move between core pages from the Home page
  So that I can complete key flows

  Background:
    Given the app is running
    And I open the home page

  Scenario: Navigate from Home to Purchase Rings to Manage Orders to About Us
    When I navigate to "Purchase Rings"
    Then I should be on the "Purchase Rings" page

    When I navigate to "Manage Orders"
    Then I should be on the "Manage Orders" page

    When I navigate to "About Us"
    Then I should be on the "About Us" page

  Scenario: Cart nav link navigates to the cart page
    When I navigate to "Cart"
    Then I should be on the "Cart" page

  Scenario: Logo click navigates back to the home page
    Given I navigate to "Purchase Rings"
    When I click the site logo
    Then I should be on the "Home" page

  Scenario: Cart badge shows the number of items in the cart
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    Then the cart badge should show 1
