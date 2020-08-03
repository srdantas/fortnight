Feature: Create customer

  Scenario: create customer with success
    Given valid customer data
    When request customer creation
    Then customer is create with success!

  Scenario: create customer already exists
    Given customer data with document that already exists
    When request customer creation
    Then customer is not create with conflict!
