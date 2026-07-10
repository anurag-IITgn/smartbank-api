## basic layer hierarchy
Controller
↓
Service
↓
Repository
↓
Hibernate
↓
MySQL

## userRequest

Client
│
▼
UserController
│
▼
UserService
│
▼
UserMapper
│
▼
UserRepository
│
▼
MySQL



## for Responses:
MySQL
│
▼
Repository
│
▼
Service
│
▼
Mapper (Entity → Response DTO)
│
▼
Controller
│
▼
Client



## Account Creation Flow

Client

↓

POST /accounts

↓

AccountController

↓

AccountService

↓

AccountMapper

↓

AccountRepository

↓

Database

Business Rules implemented inside AccountService:

- Verify user exists.
- Generate unique account number.
- Set balance = 0.
- Set status = ACTIVE.
- Set transactionTime.
- Save account.


## Transaction Module

Client
│
▼
TransactionController
│
▼
DepositService
WithdrawService
TransferService
│
▼
AccountRepository
TransactionRepository
│
▼
MySQL

# Transaction Module

The transaction module is responsible for handling all money movement inside SmartBank.

It supports:

- Cash Deposit
- Cash Withdrawal
- Account-to-Account Transfer

Every financial operation creates one or more transaction records to maintain complete transaction history.

Business rules implemented:

- Deposit amount must be positive.
- Withdrawal amount must be positive.
- Transfer amount must be positive.
- Sender account must exist.
- Receiver account must exist.
- Sender account must have sufficient balance.
- Account must be ACTIVE before transactions.
- Every transfer creates:
    - One DEBIT transaction for sender.
    - One CREDIT transaction for receiver.

All transaction operations are executed inside database transactions using @Transactional to ensure atomicity.

## Entity Relationship is as following

Account (1)
│
│
▼
Transaction (Many)


# Authentication Flow

Client

↓

POST /auth/login

↓

AuthController

↓

AuthService

↓

UserRepository

↓

MySQL Database

↓

Password Verification (BCrypt)

↓

JwtService

↓

JWT Token Returned


## Components

### SecurityConfig

Responsible for configuring Spring Security.

### PasswordEncoder

Encrypts passwords using BCrypt before storing them.

### AuthController

Receives login requests.

### AuthService

Authenticates users and generates JWT tokens.

### JwtService

Responsible for:

- Generating JWT
- Extracting email from JWT
- Validating JWT


# Module 5 – Docker Architecture

## Deployment Architecture

                 Developer
                      │
                      ▼
          docker compose up --build
                      │
                      ▼
              Docker Compose
                      │
        ┌─────────────┴─────────────┐
        ▼                           ▼
    SmartBank Container         MySQL Container
    (Spring Boot Application)    (MySQL Server)
        │                           │
        └─────────────┬─────────────┘
                      ▼
                Docker Network
                      │
                       ▼
                Docker Volume
                 (mysql_data)



# Testing Architecture

## Testing Stack

JUnit 5

↓

Mockito

↓

Service Layer

↓

Mocked Dependencies

Repositories, Mappers and PasswordEncoder are mocked during unit testing.

No database connection is required.

No Spring Boot application startup is required.

---

## Unit Test Flow

Arrange

↓

Mock Behaviour

↓

Execute Service Method

↓

Assertions

↓

Mockito Verification

---

## Testing Principles

Business logic is tested independently from infrastructure.

External dependencies are replaced by mocks.

Interactions are verified using Mockito.

Internal object state is verified using ArgumentCaptor.