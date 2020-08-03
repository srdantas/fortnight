Feature: Withdraw from customer

  Scenario: make withdraw with success
    Given valid customer data
    And random valid amount for deposit
    And withdraw of deposit amount

    When request customer creation
    And make deposit on customer
    And make a withdraw on customer

    Then withdraw is make with success!

  Scenario: make withdraw when balance is insufficient
    Given valid customer data
    And random valid amount for withdraw

    When request customer creation
    And make a withdraw on customer

    Then withdraw is rejected!
