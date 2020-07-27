Feature: Create account

  Scenario: create account with success
    Given valid account data
    When request account creation
    Then account is create with success!
    And balance with amount "0"!

  Scenario: create account already exists
    Given account data with document that already exists
    When request account creation
    Then account is not create with conflict!

  Scenario: create account without document
    Given account data without document
    When request account creation
    Then account is not create with invalid data

  Scenario: create account without name
    Given account data without name
    When request account creation
    Then account is not create with invalid data
    And balance are not create
