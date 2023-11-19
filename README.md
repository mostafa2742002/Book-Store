# Spring Application: Book Store Backend

## Overview
This Spring project serves as the backend for a book store application, providing essential functionalities for administration, user authentication, and user-specific actions. The project includes three main controllers: AdminController, HomeController, and UserController, each handling distinct features.

## Token Usage

This backend utilizes JSON Web Tokens (JWT) for user authentication. When making requests to protected endpoints, include the JWT token in the Authorization header of your HTTP requests.

**Note:** 

- For endpoints requiring `ROLE_ADMIN`, the JWT token must have the appropriate admin role claim.
- For endpoints requiring `ROLE_USER`, the JWT token must have the appropriate user role claim.

Make sure to obtain and handle JWT tokens securely.

## Controllers

### AdminController

#### Add Book

- `/addbook` (POST)
  - Requires: ROLE_ADMIN
  - Adds a new book to the database.

#### Remove Book

- `/removebook` (POST)
  - Requires: ROLE_ADMIN
  - Removes a book from the database.

#### Update Book

- `/updatebook` (POST)
  - Requires: ROLE_ADMIN
  - Updates information for an existing book.

### HomeController

#### Get All Books

- `/home` (GET)
  - Retrieves a list of all books.

#### Recently Added Books

- `/home/recentlyadded` (GET)
  - Retrieves a list of recently added books.

#### Top Selling Books

- `/home/topselling` (GET)
  - Retrieves a list of top-selling books.

#### Add New User

- `/home/addnewuser` (POST)
  - Adds a new user to the system.

#### Authenticate User

- `/home/authenticate` (POST)
  - Authenticates a user and returns a JWT token.

### UserController

#### Get All Books for User

- `/all` (GET)
  - Requires: ROLE_USER
  - Retrieves a list of all books for the authenticated user.

#### Add Star to Book

- `/addstar` (POST)
  - Requires: ROLE_USER
  - Adds a book to the user's starred list.

#### Remove Star from Book

- `/removestar` (POST)
  - Requires: ROLE_USER
  - Removes a book from the user's starred list.

#### Add Book to Cart

- `/addcart` (POST)
  - Requires: ROLE_USER
  - Adds a book to the user's shopping cart.

#### Remove Book from Cart

- `/removecart` (POST)
  - Requires: ROLE_USER
  - Removes a book from the user's shopping cart.

#### Add Order

- `/addorder` (POST)
  - Requires: ROLE_USER
  - Places an order for the user.

#### Remove Order

- `/removeorder` (POST)
  - Requires: ROLE_USER
  - Removes an order for the user.

#### Get User Orders

- `/getorders` (GET)
  - Requires: ROLE_USER
  - Retrieves a list of orders for the user.

## Data Models

### Book
Represents a book in the database.

### Order
Represents an order in the database.

### User
Represents a user in the database.

## Build and Run
Follow these steps to build and run the project:

1. Clone the repository.
2. Configure dependencies and services.
3. Build the project using your preferred build tool.
4. Run the project.

Ensure that the MongoDB database is properly set up and running.
