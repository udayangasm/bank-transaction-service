# Bank Transaction Service

## Description

This application implements several use cases related to bank account and transaction management.

* Open a bank account with an initial deposit and credit line. 
* Retrieve the current account balance of a given bank account. 
* Test if a pending debit payment would exceed the overdraft limit of that bank account. 
* Get a list of all transactions booked of a given bank account since a given calendar date. 
* Receive a list of all bank accounts in the red, i.e., whose account balance is lower than zero.


## Build and Run

gradle clean build

java -jar build/libs/bank-transaction-service-1.0.0.jar
