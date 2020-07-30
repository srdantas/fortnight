Feature: Create customer

  Scenario: create customer with success
    Given valid customer data
    When request customer creation
    Then customer is create with success!
    And balance with amount 0!

  Scenario: create customer already exists
    Given customer data with document that already exists
    When request customer creation
    Then customer is not create with conflict!

  Scenario: create customer without document
    Given customer data without document
    When request customer creation
    Then customer is not create with invalid data

  Scenario: create customer without name
    Given customer data without name
    When request customer creation
    Then customer is not create with invalid data
    And balance are not create
