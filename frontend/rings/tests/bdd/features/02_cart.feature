Feature: Shopping Cart
  As a user with items in my cart
  I want to manage my cart and checkout
  So that I can complete my purchase

  Background:
    Given the app is running
    And I am on the home page

  Scenario: Cart page shows the Shopping Cart heading
    When I navigate to "Cart"
    Then the cart page heading should be visible

  Scenario: Empty cart shows correct message
    Given I ensure the cart is empty
    Then the empty cart message should be visible

  Scenario: Continue Shopping link from empty cart navigates to Purchase Rings
    Given I ensure the cart is empty
    When I click the empty cart Continue Shopping link
    Then I should be on the "Purchase Rings" page

  @api
  Scenario: Cart displays items after a ring is added
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    Then the cart should contain at least 1 item

  @api
  Scenario: Order summary section is visible in a populated cart
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    Then the order summary section should be visible

  @api
  Scenario: Delivery location dropdown is present in the cart
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    Then the delivery location dropdown should be present
  @api
Scenario: Remove button removes an item from the cart
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    When I click the remove button on the first cart item
    Then the cart item count should have decreased

  @api
  Scenario: Continue Shopping button in the summary navigates to Purchase Rings
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    When I click the summary Continue Shopping button
    Then I should be on the "Purchase Rings" page

  @api
  Scenario: Selecting Add New Location reveals the location form
    Given I am on the home page
    And I select the first customer from the dropdown
    And I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    When I select Add New Location from the delivery dropdown
    Then the add new location form should be visible

  @api
  Scenario: Delivery location dropdown has selectable location options
    Given I am on the home page
    And I select the second customer from the dropdown
    And I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    Then the delivery location dropdown should have at least one selectable location

  @api
  Scenario: Checkout without a delivery location shows an alert
    Given I am on the home page
    And I select the first customer from the dropdown
    And I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    When I click the Checkout button
    Then an alert should appear with the message "Please select a delivery location before checking out."

  @api
  Scenario: Clear Cart button empties the cart
    Given I navigate to "Purchase Rings"
    And the purchase rings page is ready
    When I add the first ring to the cart
    And I navigate to "Cart"
    When I click the Clear Cart button
    Then the empty cart message should be visible
