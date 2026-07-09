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


## Deposit Flow

1. Client sends DepositRequest.
2. Controller validates request.
3. Service finds account.
4. Deposit amount added to account balance.
5. Updated account saved.
6. CREDIT transaction created.
7. Transaction saved.
8. DepositResponse returned.

## Withdrawal Flow 

## Withdrawal Flow

1. Client sends WithdrawRequest.
2. Validate amount.
3. Find account.
4. Verify account is ACTIVE.
5. Verify sufficient balance.
6. Deduct balance.
7. Save account.
8. Create DEBIT transaction.
9. Save transaction.
10. Return WithdrawResponse.

## Transfer Flow

1. Client sends TransferRequest.
2. Validate sender.
3. Validate receiver.
4. Check both accounts are ACTIVE.
5. Verify sufficient balance.
6. Deduct sender balance.
7. Add receiver balance.
8. Save both accounts.
9. Create sender DEBIT transaction.
10. Create receiver CREDIT transaction.
11. Save both transactions.
12. Return TransferResponse.

## Exception Handling
Transaction module handles:

- AccountNotFoundException
- AccountInactiveException
- InsufficientBalanceException
- Validation Exceptions

# Module 4 Walkthrough

## User Registration

1. Client submits registration request.
2. Password is encrypted using BCrypt.
3. User is saved in database.
4. Encrypted password is stored instead of plain text.

---

## User Login

1. Client submits email and password.
2. User is searched by email.
3. BCrypt verifies password.
4. JwtService generates JWT.
5. JWT returned to client.

---

## JWT Structure

JWT consists of three parts:

Header
Payload
Signature

Header stores token metadata.

Payload stores:

- Email
- Issue Time
- Expiration Time

Signature is generated using:

Header

+

Payload

+

Secret Key

The final JWT is returned to the client.

---

## Validation

JwtService validates:

- Signature
- Expiration

If valid:

Request is authenticated.

Otherwise:

401 Unauthorized.