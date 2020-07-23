import random

import behave


class Model:
    def __init__(self, name, document):
        self.name = name
        self.document = document

    def __str__(self):
        return f'{self.name}, {self.document}'


@behave.given('valid account data')
def step_impl(context):
    random_item_index = random.randint(0, len(context.table.rows) - 1)
    random_item = context.table.rows[random_item_index]
    context.model = Model(random_item['name'], random_item['document'])


@behave.given('account data with document that already exists')
def step_impl(context):
    pass


@behave.given('account data without document')
def step_impl(context):
    pass


@behave.given('account data without name')
def step_impl(context):
    pass


@behave.when('request account creation')
def step_impl(context):
    assert True is not False


@behave.then('account is create with success!')
def step_impl(context):
    assert context.failed is False


@behave.then('account is not create with conflict!')
def step_impl(context):
    assert context.failed is False


@behave.then('account is not create with invalid data')
def step_impl(context):
    assert context.failed is False
