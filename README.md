# Description of pacticipants:


Provider 1: User service
GET /users/1
```
{
"name": "Beth Miller",
"role": "ADMIN",
"lastLogin": "2018-10-04T09:51:26Z"
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
