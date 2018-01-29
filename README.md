# transaction_manager<br>
download or clone the project,<br>
navigate to the folder,<br>
sbt run,<br>
To check current amount of money:<br>
curl -X GET http://localhost:9000/balance<br>
To make debit transaction:<br>
curl -X POST -d '{"addSum":500}' -H "Content-Type: application/json" http://localhost:9000/debit<br>
To make credit transaction:<br>
curl -X POST -d '{"minusSum":800}' -H "Content-Type: application/json" http://localhost:9000/credit<br>
To list all credit transactions:<br>
curl -X GET http://localhost:9000/credits<br>
To list all debit transactions:<br>
curl -X GET http://localhost:9000/debits<br>
