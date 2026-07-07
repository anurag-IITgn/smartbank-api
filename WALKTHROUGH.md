Created project
Configured MySQL
Built entities
Added entity relationships
Implemented repository layer


Start MySQL.
Run the Spring Boot application.
Open Postman.
Send POST /users.
Test:
Valid registration → 201
Duplicate email → 409
Duplicate phone → 409
Invalid input → 400