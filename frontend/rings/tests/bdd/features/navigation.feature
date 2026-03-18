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