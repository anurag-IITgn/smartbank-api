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



# Account Creation Walkthrough

1. Client sends POST /accounts.

2. Request is validated using @Valid.

3. Service checks whether the supplied user exists.

4. If user does not exist:

    - Throw UserNotFoundException.
    - GlobalExceptionHandler returns HTTP 404.

5. Generate a random 12-digit account number.

6. Check database using existsByAccountNumber().

7. Repeat generation until a unique number is obtained.

8. Create Account entity.

9. Apply business rules:

    - Balance = 0
    - Status = ACTIVE
    - CreatedAt = LocalDateTime.now()
    - Associate User

10. Save account.

11. Convert entity to AccountResponse.

12. Return HTTP 201 Created.