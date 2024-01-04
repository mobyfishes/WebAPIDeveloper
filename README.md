# WebAPIDeveloper


This is a Demo for Assessment implemented by Spring Boot and RESTful API  for managing transaction-related operations. T
This controller handles transactions and their associated rewards within a three-month period. 
It interacts with the `TransactionService` to perform the necessary calculations and retrieve reward information.
And basic DAO layer to persist transactions and rewards supports simple queries for searching.

## Endpoints

### Calculate All Rewards

- **Endpoint**: `POST /Transaction`
- **Description**: Processes records of all transactions and stores them in the H2 database.
- **Request Body**: List of `Transaction` objects representing transactions during a three-month period.
- **Response**: HTTP Status 200 (OK)

### Get Total Rewards by CustomerId

- **Endpoint**: `GET /Total/{id}`
- **Description**: Retrieves the total rewards for a given customer ID.
- **Path Variable**: `id` - Customer ID
- **Response**: JSON string representing the total rewards for the specified customer.

### Get Monthly Rewards by CustomerId

- **Endpoint**: `GET /Month/{id}`
- **Description**: Retrieves the monthly rewards for a given customer ID.
- **Path Variable**: `id` - Customer ID
- **Response**: JSON string representing the monthly rewards for the specified customer.

### Get Total and Monthly Rewards by CustomerId

- **Endpoint**: `GET /Rewards/{id}`
- **Description**: Retrieves both total and monthly rewards for a given customer ID.
- **Path Variable**: `id` - Customer ID
- **Response**: JSON string representing both total and monthly rewards for the specified customer.

