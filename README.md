# VerificationService

A lightweight Java (Spring Boot) microservice that handles user account verification during sign-up. It temporarily stores user data, emails a verification code, and persists the account once the code is confirmed.

How it works
1) POST /users/register: store user data in cache and send a welcome email.
2) POST /codes/create: generate and send a verification code to the user's email.
3) POST /users/persist: validate the code and persist the account in the database.

Base URL
- Local: http://localhost:8080
- All endpoints use JSON over HTTP. Content-Type: application/json

Endpoints

### 1) Register user
- Method: POST
- Path: /users/register
- Purpose: Temporarily store user data and send a welcome email, pending verification.
- Request body schema (UserRegisterDto):
  
```json
{
    "username": "string",
    "firstName": "string",
    "lastName": "string",
    "email": "string (valid email)",
    "password": "string (min 8 chars, must include at least 1 uppercase, 1 lowercase, and 1 digit)"
}
```
  
  Notes:
  - password regex: ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$
  - Constraints (validated):
    - password: not empty, length >= 8, must match regex above
- Response: ```{ "message": "string" }```

###  2) Request verification code
- Method: POST
- Path: /codes/create
- Purpose: Generate and email a 6-digit verification code for the given email, if a pending registration exists.
- Request body schema (EmailDto):
```json
{
    "email": "string (valid email)"
}
```
  Constraints: email is required and must be a valid email.
- Response: ```{ "message": "string" }```

### 3) Confirm and persist account
- Method: POST
- Path: /users/persist
- Purpose: Validate the verification code and persist the registered user into the database.
- Request body schema (ConfirmAccountDto):
```json
{
    "email": "string (valid email)",
    "code": "string (exactly 6 digits)"
}
```
  Constraints:
  - email: required and valid
  - code: required, length = 6
- Response: ```{ "message": "string" }```

Validation summary
- EmailDto.email: not blank, valid email
- ConfirmAccountDto.email: not blank, valid email
- ConfirmAccountDto.code: not blank, length = 6
- UserRegisterDto.password: not empty, length >= 8, must include upper/lower/digit

Notes
- If the email has not been registered (cached) yet, /codes/create returns 400.
- If the verification time expires or code/email is missing, /users/persist returns 400.
- If username or email already exists, /users/register returns 409.
