project repositories completed those are 
user
account
transactions
each repository is created to converse with their respective tables in the MySql database smartbank_db
each repository implements jpa repository and works in dynamic method calling. 
AccountRepo and TransactionRepo utilizes the object details respectively because objects have been declared within theier entity


COMPLETED:
* Spring Boot Project setup
* MySQL config
* User Entity
* User Repo
* User DTOs
* User Mapper
* User Service
* User Controller
* User Regis API
* Bean Validation
* Global Exception Handling
* Postman Testing
* Intellij HTTP Client Testing

# Module 3 - Transactions ✅

Completed:

✔ Deposit API

✔ Withdrawal API

✔ Transfer API

✔ DepositRequest / DepositResponse

✔ WithdrawRequest / WithdrawResponse

✔ TransferRequest / TransferResponse

✔ DepositService

✔ WithdrawService

✔ TransferService

✔ TransactionController

✔ TransactionRepository

✔ Validation

✔ Custom Exceptions

✔ Global Exception Handler

✔ @Transactional

✔ Postman Testing

✔ MySQL Transaction History

Status: Completed


# Module 4 – Authentication & Security ✅

## Completed

- Added Spring Security dependency
- Configured SecurityConfig
- Added BCrypt PasswordEncoder bean
- Added password field to User entity
- Updated CreateUserRequest to accept password
- Encrypted user passwords before saving to database
- Created LoginRequest DTO
- Created LoginResponse DTO
- Implemented AuthController
- Implemented AuthService
- Implemented login endpoint
- Verified passwords using BCrypt
- Added JWT dependency
- Implemented JwtService
- Generated JWT after successful login
- Implemented JWT email extraction
- Implemented JWT validation

## Tested

- User registration stores encrypted passwords
- Successful login returns JWT token
- Invalid password throws InvalidCredentialsException
- Unknown email throws UserNotFoundException
- Validation errors handled correctly

