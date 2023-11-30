### GrpcClientService

Methods:
* [GET BY USERNAME](#get-client-by-username)

#### Get client by username

**Request**:
```grpc request
POST http://localhost:8081/rgo.tt.user.grpc.ClientService/findByUsername
```

**Message**:
```
{
    "username": <string>
}
```

**Responses:**

* **OK (0):**

Trailers:
```
content-type=application/grpc
```

Body:
```
{
    "username": <string>,
    "password": <string>
}
```

* **DEADLINE_EXCEEDED (4):**

Trailers:
```
content-type=application/grpc
```

* **NOT_FOUND (5):**

Trailers:
```
content-type=application/grpc
```

* **RESOURCE_EXHAUSTED (8):**

Trailers:
```
content-type=application/grpc
```

* **INTERNAL (13):**

Trailers:
```
content-type=application/grpc
```