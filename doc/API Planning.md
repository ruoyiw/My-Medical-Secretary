# API Planning

## Interception

- Create a interceptor to handle authentication and identification extraction, bind with `@Secured` annotation.
  - User identity extraction & passing using `SecurityContext`

```java
// Inject SecurityContext as a parameter
public void someMethod(@Context SecurityContext securityContext) {

    // Get user instance from interceptor (with uid and role):
    User user = (User)securityContext.getUserPrincipal();
    String uid = user.getId();
    UserRole role = user.getRole();

    // Or directly get user id from interceptor:
    String uid = securityContext.getUserPrincipal().getName();
}
```

- [?] Also use annotation binded intreceptors to do global filtering (depends on specific global filters)

## Paging & Common Parameters

## Response

- Use `Response builder` provided by Jersey to construct a `Response` object as return.
- Use HTTP Status Code (make use of HTTP Headers)
  - insert customised

```
// Use javax.ws.rs.core.Response as return type
public Response someMethod(User user) {


    // Construct a simple 200 OK response:
    Response response = Response.ok().build();

    // Construct a 200 OK response with a object as entity:
    response = Response.ok(user).build();

    // Construct a 400 BAD REQUEST response with a message as entity:
    response = Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new DefaultRespondEntity("Request cannot be proceed."))
                .build();

    // Return response
    return response;
}
```

## Other

- Seek for a new JSON Library, for better POJO serialisation

## Specific Design

### Authentication

* Token Generation
* Token Vertification
* User Identity extraction / passing
* [API] Logoff
* [API] Login

### Patients

* Register (C)
* Change pwd (U)

### Appointments
* List your appointments (R)
* Get appointment (R) (304)
* Get Changelist (R) (304)
* note (C R)

* confirmation ? (U)
* File ?


### General Information
* 4x get resource list (R) (304)

### Forms & Files
