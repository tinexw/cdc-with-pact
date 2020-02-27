# Consumer-Driven Contract Testing with Pact

Example project that contains
- `infrastructure`: set up for Pact Broker (with PostgreSQL) and Jenkins via docker-compose
- `messaging-app`: example consumer
- `user-service`: example provider

The master branch is using JUnit4 to match the examples of the [blog post](https://kreuzwerker.de/post/writing-contract-tests-with-pact-in-spring-boot).
The [junit5 branch](https://github.com/tinexw/cdc-with-pact/tree/junit5) contains the same code updated to Junit5.
