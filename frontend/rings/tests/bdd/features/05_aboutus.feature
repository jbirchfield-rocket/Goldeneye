Feature: About Us page
  As a user visiting the About Us page
  I want to see information about Goldeneye
  So that I can learn about the company

  Background:
    Given the app is running
    And I am on the home page
    When I navigate to "About Us"

  Scenario: About Us page displays the main heading
    Then the About Us main heading should be visible

  Scenario: About Us page displays all section headings
    Then the following section headings should be visible
      | heading                    |
      | What We Do                 |
      | Our Craftsmanship          |
      | Personalization That Matters |
      | Quality You Can Trust      |
      | More Than Just Rings       |
