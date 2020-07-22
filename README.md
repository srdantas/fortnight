# Fortnight
Simple Core Banking for basic operations, like movements between accounts and tax withdraw
___

### Operations
For using operations, need accounts for it, the API provides a endpoint for create, with name and document (like CPF).

- Deposit in exists account
- Withdraw money
- Transfer Money between accounts

### Operations rules
- When withdraw money from account, 1% of value is charged
- When deposit money into account, account received 0.5% of value

*Transfer between accounts is unlimited and free*

## BDDs
The folder bdds have a cucumber tests, write with python (behave), for all features exists on this project.
This is a most high level of tests of this app.

for run features:
```shell
cd bdds && \ 
pip install . && \ 
behave
```
*run features required python 3.\* and pip installed*
