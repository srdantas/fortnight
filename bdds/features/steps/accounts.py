import random

import behave
import names
import requests


def generate_cpf():
    cpf = [random.randint(0, 9) for x in range(9)]

    for _ in range(2):
        val = sum([(len(cpf) + 1 - i) * v for i, v in enumerate(cpf)]) % 11

        cpf.append(11 - val if val > 1 else 0)

    return '%s%s%s.%s%s%s.%s%s%s-%s%s' % tuple(cpf)


class Model:
    def __init__(self, name, document):
        self.name = name
        self.document = document

    def dict(self):
        return {
            'name': self.name,
            'document': self.document
        }


@behave.given('valid account data')
def random_valid_model(context):
    name = names.get_full_name()
    document = generate_cpf()
    context.model = Model(name, document)


@behave.given('account data with document that already exists')
def model_with_document_already_exists(context):
    random_valid_model(context)
    request_create_account(context)


@behave.given('account data without document')
def model_without_document(context):
    name = names.get_full_name()
    document = None
    context.model = Model(name, document)


@behave.given('account data without name')
def model_without_name(context):
    name = None
    document = generate_cpf()
    context.model = Model(name, document)


@behave.when('request account creation')
def request_create_account(context):
    server_url = context.config.userdata['server']
    response = requests.post(f'{server_url}/accounts', json=context.model.dict())
    context.response = response


@behave.then('account is create with success!')
def assert_success(context):
    assert context.failed is False
    assert context.response.status_code == 201


@behave.then('account is not create with conflict!')
def assert_conflict(context):
    assert context.failed is False
    assert context.response.status_code == 409


@behave.then('account is not create with invalid data')
def assert_invalid_data(context):
    assert context.failed is False
    assert context.response.status_code == 400
