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
  @api
  Scenario: Configure all ring options and verify the cart item matches
    Given the purchase rings page has loaded
    When I select the second option in the material dropdown on the first ring
    And I select the second option in the width dropdown on the first ring
    And I select the second option in the stone dropdown on the first ring
    And I set the quantity to 2 on the first ring
    And I click the Add to Cart button on the first ring
    Then an alert should appear with the message "Added 2 ring(s) to cart!"
    When I navigate to "Cart"
    Then the cart should contain at least 1 item
    And the first cart item should show quantity 2
    And the first cart item should show the selected material value
    And the first cart item should show the selected stone value
