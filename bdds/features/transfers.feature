Feature: Transfer between customers

  Scenario: create transfer between customers
    Given valid customers for transfer
    And create deposit for creditor
    When request creditor transfer to debtor
    Then transfer is create with success!

  Scenario: create transfer between customers when creditor haven't balance
    Given valid customers for transfer
    When request creditor transfer to debtor
    Then transfer is rejected!