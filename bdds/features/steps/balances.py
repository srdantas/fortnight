import behave

import requests


@behave.then('balance with amount {amount}!')
def assert_balance_amount(context, amount):
    document = context.customer.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    assert response.status_code == 200
    assert response.json()['balance'] == float(amount)


@behave.then('balance amount deposit is correct!')
def assert_balance_deposit(context):
    document = context.customer.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    assert response.status_code == 200
    assert response.json()['balance'] == float(context.deposit.dict()['amount'])


@behave.then('balance are not create')
def assert_balance_not_found(context):
    document = context.customer.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    assert response.status_code == 404


@behave.then('balance deposit withdraw is correct!')
def assert_balance_withdraw(context):
    document = context.customer.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    deposit = context.deposit
    withdraw = context.withdraw

    assert response.status_code == 200
    assert response.json()['balance'] == float(deposit.amount - withdraw.amount)
