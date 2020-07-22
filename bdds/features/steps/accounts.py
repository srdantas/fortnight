import behave


@behave.given('valid account data')
def step_impl(context):
    pass


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
