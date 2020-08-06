import random
import uuid

import behave
import requests


class Deposit:

    def __init__(self, amount, correlation=uuid.uuid4()):
        self.amount = amount
        self.correlation = str(correlation)

    def dict(self):
        return {
            "correlation": self.correlation,
            "amount": self.amount
        }

    def create(self, context, document=None):
        if not document:
            document = context.customer.document
        server_url = context.config.userdata['server']
        return requests.post(f'{server_url}/customers/{document}/deposits', json=self.dict())


@behave.given("random valid amount for deposit")
def random_valid_amount(context):
    random_value = random.random() * random.randint(1, 100)
    random_amount = float("{:.2f}".format(random_value))
    context.deposit = Deposit(random_amount)


@behave.when("make deposit on customer")
def make_deposit(context):
    context.deposit_response = context.deposit.create(context)


@behave.then("deposit is make with success!")
def assert_deposit_with_success(context):
    assert context.failed is False
    assert context.deposit_response.status_code == 201
