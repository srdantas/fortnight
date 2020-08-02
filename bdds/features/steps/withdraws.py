import random
import uuid

import behave
import requests


class Withdraw:

    def __init__(self, amount, correlation=uuid.uuid4()):
        self.amount = amount
        self.correlation = str(correlation)

    def dict(self):
        return {
            "correlation": self.correlation,
            "amount": self.amount
        }


@behave.given('withdraw of deposit amount')
def withdraw_from_deposit(context):
    deposit = context.deposit
    context.withdraw = Withdraw(deposit.amount)


@behave.given('random valid amount for withdraw')
def random_withdraw(context):
    random_value = random.random() * random.randint(1, 100)
    random_amount = float("{:.2f}".format(random_value))
    context.withdraw = Withdraw(random_amount)


@behave.when('make a withdraw on customer')
def make_withdraw(context):
    document = context.customer.document
    server_url = context.config.userdata['server']
    response = requests.post(f'{server_url}/customers/{document}/withdraws', json=context.withdraw.dict())
    context.withdraw_response = response


@behave.then('withdraw is make with success!')
def assert_withdraw_success(context):
    assert context.failed is False
    assert context.withdraw_response.status_code == 201


@behave.then('withdraw is rejected!')
def assert_withdraw_success(context):
    assert context.failed is False
    assert context.withdraw_response.status_code == 403
