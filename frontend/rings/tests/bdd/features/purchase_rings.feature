Feature: Purchase Rings page
  As a user on the Purchase Rings page
  I want to add rings to my cart
  So that I can purchase them

  Background:
    Given the app is running
    And I am on the home page
    And I select the first customer from the dropdown
    And I navigate to "Purchase Rings"
  @api
  Scenario: Add to cart shows a confirmation alert
    Given the purchase rings page has loaded
    When I click the Add to Cart button on the first ring
    Then an alert should appear with the message "Added 1 ring(s) to cart!"
