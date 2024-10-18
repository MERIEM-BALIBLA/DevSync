Feature: User Management

  Scenario: Insert a non-manager user and create a token
    Given a non-manager user "John" with email "john.doe@example.com"
    When the user is inserted
    Then a token should be created for the user
    And the inserted user should not be null
    And the token should have 2 daily tokens
    And the token should have 1 monthly token
