Overview
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
This project is a backend implementation of an online movie ticket booking platform supporting both B2C customers and B2B theatre partners. The system focuses on clean architecture, transaction safety, and scalability-aware design.

Architecture
------------
Client → Controller → Application Service (Transactional) → Repository → Database (H2)

Technology Stack
------------------
Language: Java 17
Framework: Spring Boot
ORM: Spring Data JPA
Database: H2 (In-Memory)
Build Tool: Maven
IDE: IntelliJ IDEA

Implemented Features
----------------------
- Book movie tickets (Write scenario)
- Browse available shows (Read scenario)
- 50% discount on 3rd ticket
- 20% discount on afternoon shows

Design Patterns Used
-------------------------
- Strategy Pattern for discount rules
- Repository Pattern for persistence
- Optimistic Locking (@Version) to prevent double booking

Concurrency Handling
------------------------
Seat entity uses @Version for optimistic locking. Transactional boundary at service layer ensures atomic booking operations.

Non-Functional Considerations
------------------------------
- Horizontally scalable stateless services
- Redis-based seat hold (design-level)
- Payment integration via idempotent API design (design-level)
- Security via input validation and SQL injection prevention
