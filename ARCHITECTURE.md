Controller
↓
Service
↓
Repository
↓
Hibernate
↓
MySQL



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



for Responses:
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