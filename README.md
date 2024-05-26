Have you tried making a revision/history tracking system with hibernate envers? Well I did, and I thought it'd be cool to show what I came up with since this is not the typical usecase for hibernate/envers
<br>
Example responses:
1) For an addition/new entity:
```
{
    "entityId": "b9a60ed9-52d7-4f65-956d-5b4f3b4d5d1e",
    "entityName": "UserEntity",
    "operationType": "add",
    "attributes": [
        {
            "attributeName": "address",
            "oldValue": "",
            "newValue": "93001 Cydney Drive"
        },
        {
            "attributeName": "email",
            "oldValue": "",
            "newValue": "Archibald.Langosh91@hotmail.com"
        },
        {
            "attributeName": "id",
            "oldValue": "",
            "newValue": "b9a60ed9-52d7-4f65-956d-5b4f3b4d5d1e"
        },
        {
            "attributeName": "name",
            "oldValue": "",
            "newValue": "Alison Graham DDS"
        }
    ],
    "author": "Dummy_username",
    "timestamp": "2024-04-28T12:21:33.81"
}
```
2) For modifications of entity attributes:
```
{
    "entityId": "b9a60ed9-52d7-4f65-956d-5b4f3b4d5d1e",
    "entityName": "UserEntity",
    "operationType": "mod",
    "attributes": [
        {
            "attributeName": "email",
            "oldValue": "Archibald.Langosh91@hotmail.com",
            "newValue": "updated@email.com"
        },
        {
            "attributeName": "name",
            "oldValue": "Alison Graham DDS",
            "newValue": "Updated Name"
        }
    ],
    "author": "Dummy_username",
    "timestamp": "2024-05-04T10:17:21.118"
}
```

3) For deleting an entity

```
{
    "entityId": "8f666ffd-c9fb-48d9-b9bb-fa7512e8c8bf",
    "entityName": "UserEntity",
    "operationType": "del",
    "attributes": [
        {
            "attributeName": "address",
            "oldValue": "3314 Wisozk Hill",
            "newValue": ""
        },
        {
            "attributeName": "email",
            "oldValue": "Korbin.Nikolaus@yahoo.com",
            "newValue": ""
        },
        {
            "attributeName": "id",
            "oldValue": "8f666ffd-c9fb-48d9-b9bb-fa7512e8c8bf",
            "newValue": ""
        },
        {
            "attributeName": "name",
            "oldValue": "Nicholas McGlynn",
            "newValue": ""
        }
    ],
    "author": "Dummy_username",
    "timestamp": "2024-05-26T22:29:45.218"
}
```
