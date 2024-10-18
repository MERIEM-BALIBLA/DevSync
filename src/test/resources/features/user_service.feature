Feature: User Management

  Scenario: Add normal user to empty list
    Given an empty employee list
    And a normal user
    When the employee is added to the employee list
    Then the employee list should contain the new employee

  Scenario: Add manager user to empty list
    Given an empty employee list
    And a manager user
    When the employee is added to the employee list
    Then the employee list should contain the new employee

  Scenario: Ensure employee list is empty after clear
    Given an empty employee list
    Then the employee list should be empty
