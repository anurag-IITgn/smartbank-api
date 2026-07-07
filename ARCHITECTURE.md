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
- Set createdAt.
- Save account.