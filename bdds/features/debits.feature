Feature: Deposit on customer

  Scenario: create customer and make deposit on it with success
    Given valid customer data
    And random valid amount for deposit

    When request customer creation
    And make deposit on customer

    Then deposit is make with success!
    And balance amount deposit is correct!
