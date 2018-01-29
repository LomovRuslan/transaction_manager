# transaction_manager
download or clone the project,
navigate to the folder,
sbt run,
To check current amount of money:
curl -X GET http://localhost:9000/balance
To make debit transaction:
curl -X POST -d '{"addSum":500}' -H "Content-Type: application/json" http://localhost:9000/debit
To make credit transaction:
curl -X POST -d '{"minusSum":800}' -H "Content-Type: application/json" http://localhost:9000/credit
To list all credit transactions:
curl -X GET http://localhost:9000/credits
To list all debit transactions:
curl -X GET http://localhost:9000/debits
