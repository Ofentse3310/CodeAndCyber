# Leave Management System

A Spring Boot web application where employees request leave and managers approve or reject it. Built as a portfolio project to practise backend development, business-rule validation and secure authentication.

## Features
- Login with BCrypt-hashed passwords and roles (Employee, Manager, Admin)
- Employees submit leave requests and see their balances and request history
- Managers approve or reject requests with a comment
- Leave balances are deducted only when a request is approved
- Friendly error messages for invalid requests

## Business rules
- End date cannot be before the start date
- Leave cannot start in the past
- Weekends are not counted as leave days
- Overlapping requests are blocked
- A request cannot exceed the employee's remaining balance
- Balance is checked again at approval time
- Only the employee's own manager can approve or reject, and only once

## Tech stack
Java 17, Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, H2 (in-memory), JUnit 5, Maven

## Project structure
```
src/main/java/com/ofentse/leavemanagement
├── config        Security setup and demo data loader
├── controller    Web endpoints
├── model         Entities (User, LeaveRequest, LeaveType, LeaveBalance)
├── repository    Database access
└── service       Business rules and login lookup
```

## How to run
1. Clone the repository
2. Open the project's `pom.xml` in IntelliJ IDEA and let Maven finish loading
3. Run `LeavemanagementApplication`
4. Open http://localhost:8080

## Demo accounts (development only)
| Role | Email | Password |
|---|---|---|
| Employee | employee@demo.test | Demo#12345 |
| Manager | manager@demo.test | Demo#12345 |

These accounts exist only for local testing. Do not reuse this password anywhere.

## Running the tests
```
mvn test
```

## Planned improvements
- Login attempt limiting and failed-login logging
- Role-based URL access rules
- Admin page for managing employees
- PostgreSQL instead of in-memory H2
- Public holidays excluded from working-day counts
- Cancel a pending request and a team calendar view

## What I learned
- Structuring a layered Spring Boot application
- Enforcing business rules in a service layer and testing them
- Password hashing and database-backed authentication
- Preventing invalid requests from reaching the database