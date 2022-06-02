# IOBuilders challenge

For learning purposes I have added more functionality to the challenge, I hope this was not a bad decision.

## Note 

I have tried to develop this challenge following the concepts of "Clean Architecture":

* Independent from database

* Easy to test

* Isolated from frameworks

* All the business logic is found in the use cases, not spread everywhere

* Easy to deploy because leaves the configuration for the last

* Each use case involves different tasks

I've tried also use SOLID principles to reach the actual clean architecture design pattern. Overall there is the use of
the dependency inversion principle.

## Technology
I have used Java 11 With Spring Boot for develop the whole project whith this dependencies

| Dependendencies              |
|:-----------------------------|
| spring-boot-devtools         |    
| spring-boot-starter-test     |      
| spring-boot-starter-data-jpa |        
| h2 database                  |     
| jjwt                         |       
| spring-boot-starter-web      | 
| junit                        |    
| argon2-jvm                   |        
| mockito-junit-jupiter        |     


## Application structure

I have structured the project using an adaptation of hexagonal architecture.

### Domain
Perhaps I should have separated the use cases from the domain, I felt that this would be a good representation, and in case the project had to be restructured this would not harm the functioning of the application.
* Model: Contains the business entities.
* Use case/application: implements the use cases exposed in the requirements of this challenge. Define interfaces for the data
 that use cases need.
* Exception: Some bussines exceptions, that represents the errors.

### Infrastructure
  * Database: Provides data to use cases, implementing the interfaces defined in them. We could have different data
    providers, but in this case I have implemented only one database provider that uses an in-memory h2 database.

  * Controller: Defines the way to interact with this application. In this case is a rest adapter for a API Rest. This includes the
    existing controller definition.

### Configuration:
Creates necessary beans to configures the whole application


## Tests
I have tried to create integration and unit tests, I have separated them into different packages.
Due to lack of time I have not been able to do all the necessary tests (I know it goes against TDD), however I have included the unit tests for the use cases.
For integration I have added all the ones that have to do with the API and the JPA ones.

## API reference

#### Get user info

```http
  GET /user/{username}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Username of item |
Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "id": 1,
    "username": "mariofr"
}
```

#### Get wallet info

```http
  GET /wallet/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of item to fetch |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "balance": 0,
    "name": "cartera1",
    "id": 1
}
```

#### Get wallet transactions

```http
  GET /wallet/{id}/transactions
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of item to fetch |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "balance": 0,
    "name": "cartera1",
    "id": 1
}
```

#### Get user wallets

```http
  GET /user/{id}/wallets
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of item to fetch |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
[
    {
        "balance": 300.00,
        "name": "main wallet",
        "id": 1
    },
    {
        "balance": 0.00,
        "name": "second wallet",
        "id": 2
    },
    {
        "balance": 0.00,
        "name": "parties",
        "id": 3
    }
]
```
#### Sign up user

```http
  POST /user
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Username    |
| `password` | `string` | **Required**. password    |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "id": 1,
    "username": "mariofr"
}
```
#### Login

```http
  POST /login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Username    |
| `password` | `string` | **Required**. password    |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjU0MTY2ODIxLCJzdWIiOiJtYXJpb2ZyIiwiaXNzIjoiTWFpbiIsImV4cCI6MTY1NDI1MzIyMX0.qT2wk1SJykH2xtQdiBKFh6PSdRblwgpRCx0FzFjeons"
}
```

#### Create wallet

```http
  POST /wallet
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**. wallet name    |
| `user_id` | `string` | **Required**. user id    |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |

Sample response:
```json
{
    "balance": 0,
    "name": "wallet",
    "id": 1
}
```

#### Make transfer

```http
  POST /wallet/{id}/transfer
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `amount` | `number` | **Required**. amount   |
| `toWalletId` | `string` | **Required**. to wallet id    |
| `user_id` | `string` | **Required**. user id    |

Headers

|Content-Type| Value    |
|---|----------|
|token| **Required** jwtoken |


#### Deposit amount

```http
  PUT /wallet/{id}/deposit
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `amount` | `number` | **Required**. amount     |

 
 

