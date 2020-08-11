import random
import uuid

import behave
import names
import requests

import customers
import deposits


class Transfer:

    def __init__(self, creditor, debtor, amount, correlation=uuid.uuid4()):
        self.creditor = creditor
        self.debtor = debtor
        self.amount = amount
        self.correlation = str(correlation)

    def dict_creditor(self):
        return {
            'correlation': self.correlation,
            'amount': self.amount,
            'creditor': {
                'document': self.debtor.document
            }
        }

    def create(self, context):
        document = self.creditor.document
        server_url = context.config.userdata['server']
        return requests.post(f'{server_url}/customers/{document}/transfers', json=self.dict_creditor())


@behave.given('valid customers for transfer')
def random_valid_customer_for_transfer(context):
    creditor_name = names.get_full_name()
    creditor_document = customers.generate_cpf()
    creditor = customers.Customer(creditor_name, creditor_document)

    debtor_name = names.get_full_name()
    debtor_document = customers.generate_cpf()
    debtor = customers.Customer(debtor_name, debtor_document)

    creditor_response = creditor.create(context)
    debtor_response = debtor.create(context)

    assert creditor_response.status_code == 201
    assert debtor_response.status_code == 201

    random_value = random.random() * random.randint(1, 100)
    random_amount = float("{:.2f}".format(random_value))
    context.transfer = Transfer(creditor, debtor, random_amount)


@behave.given('create deposit for creditor')
def random_valid_deposit(context):
    random_value = random.random() * random.randint(1, 100)
    random_amount = float("{:.2f}".format(random_value))
    deposit = deposits.Deposit(random_amount)

    deposit_response = deposit.create(context, context.transfer.creditor.document)

    assert deposit_response.status_code == 201


@behave.when('request creditor transfer to debtor')
def create_transfer(context):
    response = context.transfer.create(context)
    context.transfer_response = response


@behave.then('transfer is create with success!')
def assert_transfer_success(context):
    assert context.failed is False
    assert context.transfer_response.status_code == 201


@behave.then("transfer is rejected!")
def assert_transfer_rejected(context):
    assert context.failed is False
    assert context.transfer_response.status_code == 403
