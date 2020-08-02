import random

import behave
import names
import requests


def generate_cpf():
    cpf = [random.randint(0, 9) for x in range(9)]

    for _ in range(2):
        val = sum([(len(cpf) + 1 - i) * v for i, v in enumerate(cpf)]) % 11

        cpf.append(11 - val if val > 1 else 0)

    return '%s%s%s%s%s%s%s%s%s%s%s' % tuple(cpf)


class Customer:
    def __init__(self, name, document):
        self.name = name
        self.document = document

    def dict(self):
        return {
            'name': self.name,
            'document': self.document
        }


@behave.given('valid customer data')
def random_valid_model(context):
    name = names.get_full_name()
    document = generate_cpf()
    context.customer = Customer(name, document)


@behave.given('customer data with document that already exists')
def model_with_document_already_exists(context):
    random_valid_model(context)
    request_create_customer(context)


@behave.when('request customer creation')
def request_create_customer(context):
    server_url = context.config.userdata['server']
    response = requests.post(f'{server_url}/customers', json=context.customer.dict())
    context.customer_response = response


@behave.then('customer is create with success!')
def assert_success(context):
    assert context.failed is False
    assert context.customer_response.status_code == 201


@behave.then('customer is not create with conflict!')
def assert_conflict(context):
    assert context.failed is False
    assert context.customer_response.status_code == 409
