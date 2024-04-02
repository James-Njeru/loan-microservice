# Spring Boot Loan Application
This document outlines the design and development approach for a microservice-based loan application system using Spring Boot.

## Technology Stack:
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- MySQL db
- Junit

## Microservices:
- Registration Service: Handles user registration.
- Loan Service: Processes loan requests.
- Repayment Service: Handles loan repayments.
- Account Service: Provides functionalities for checking loan balance and limit.

## Microservices:
1.	Registration Service:
   - Users can register with basic information.
   - Registration process is asynchronous.
   - It has retries a fault tolerance mechanism to handle failures gracefully.
   - Registration Service validates user data and stores it in a database.

2.	Loan Service:
   - Users can request loans with desired amount and purpose.
   - Based on evaluation, loan is approved or rejected.
   - Loan details are stored in the database upon approval.
   - Loan request is asynchronous.
   - It has retries a fault tolerance mechanism to handle failures gracefully.

3.	Repayment Service:
   - Users can initiate loan repayments with amount and reference.
   - Repayment Service communicates with Loan Service to update loan balance in the database.

4.	Account Service:
   - Users can check their loan balance and credit limit.
   - Account Service retrieves data from the database.
