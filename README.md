# Description of pacticipants:


Provider 1: User service
GET /users/1
```
{
    "id": "1",
    "legacyId": 123456,
    "name": "Beth Miller",
    "role": "ADMIN",
    "lastLogin": "2018-10-04T09:51:26Z",
    "friends": [
      {
        "id": 2,
        "name": "Ronald Smith "
      },
      {
        "id": 736,
        "name": "Matt Spencer"
      }
    ]
}
```

```
POST /users
{
  {
  "name": "Beth Miller",
  "role": "ADMIN",
}
}
```

Users are read from database directly, ids remain for very long, data almost never changes.

Consumer 1: cares about all attributes except lastLogin
Consumer 2: cares only about name
Consumer 3: POST


Provider 2: Messages
GET /conversation/{userId}/{conversationId}?limit=3&search=Hi

```
{
  "totalCount": 10,
  "messages": [
      {
        "sender": 1,
        "text": "Hi Matt",
        "timestamp": "2018-10-01T15:20:50Z",
      },
      {
        "sender": 3,
        "text": "Hello Beth",
        "timestamp": "2018-10-01T15:21:05Z",
      },
      {
        "sender": 1,
        "text": "How are you?",
        "timestamp": "2018-10-01T15:21:31Z",
      }

  ]
}
```

Consumer 1: cares about everything


# Steps
- Run `de.kreuzwerker.cdc.messagingapp.UserServiceContractTest`
- This will generate a pact file in directory `target/pacts`
- Start user service. It will be available on <http://localhost:8090>
- Inside directory `user-service` run ` ../mvnw pact:verify`. This will output:
     ```
      Given User 1 exists
             WARNING: State Change ignored as there is no stateChange URL
      A request to /users/1
        returns a response which
          has status code 200 (OK)
          includes headers
            "Content-Type" with value "application/json; charset=UTF-8" (OK)
          has a matching body (OK)
    ```
- Change `stringType` to `stringValue` and verify again
     ```
     Failures:

     0) Verifying a pact between messaging-app and user-service - A request to /users/1  Given User 1 exists returns a response which has a matching body
           $.name -> Expected 'test name' but received 'Beth'
     ````
- Remove `name` from User
     ``
Failures:

0) Verifying a pact between messaging-app and user-service - A request to /users/1  Given User 1 exists returns a response which has a matching body
      $ -> Expected a Map with at least 4 elements but received 3 elements

        Diff:

        {
        -    "lastLogin": "2018-08-24T21:21:34.947",
            "role": "ADMIN",
        -    "name": "test name",
        +    "lastLogin": "2018-08-24T21:24:38.968",
            "friends": [
                {
        -            "name": "a friend",
        -            "id": "2"
        +            "id": "2",
        +            "name": "Ronald Smith"
                },
                {
        -            "name": "a friend",
        -            "id": "2"
        +            "id": "3",
        +            "name": "Matt Spencer"
                }

      $ -> Expected name='test name' but was missing

        Diff:

        {
        -    "lastLogin": "2018-08-24T21:21:34.947",
            "role": "ADMIN",
        -    "name": "test name",
        +    "lastLogin": "2018-08-24T21:24:38.968",
            "friends": [
                {
        -            "name": "a friend",
        -            "id": "2"
        +            "id": "2",
        +            "name": "Ronald Smith"
                },
                {
        -            "name": "a friend",
        -            "id": "2"
        +            "id": "3",
        +            "name": "Matt Spencer"
                }

     ````
- Change `write-dates-as-timestamps: false` to `write-dates-as-timestamps: true`
     ```
     Failures:

     0) Verifying a pact between messaging-app and user-service - A request to /users/1  Given User 1 exists returns a response which has a matching body
           $.lastLogin -> Expected 1535140214758 to match a timestamp of 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'': Unable to parse the date: 1535140214758

     ````
- Return a new role:
     ```
     Failures:

     0) Verifying a pact between messaging-app and user-service - A request to /users/1  Given User 1 exists returns a response which has a matching body
           $.role -> Expected 'NEW_ROLE' to match 'ADMIN|USER'

     ````