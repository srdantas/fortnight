import behave

import requests


@behave.then('balance with amount {amount}!')
def assert_balance_amount(context, amount):
    document = context.model.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    assert response.status_code == 200
    assert response.json()['balance'] == float(amount)


@behave.then('balance are not create')
def assert_balance_not_found(context):
    document = context.model.document
    server_url = context.config.userdata['server']
    response = requests.get(f'{server_url}/customers/{document}')

    assert response.status_code == 404
