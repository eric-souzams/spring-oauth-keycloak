<h1 align="center">OAuth2 Implementation</h1>

<h4 align="center">
  <a href="#tecnologies">Tecnologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#http">HTTP</a>
</h4>

## Tecnologies
This project was developed using the following technologies:
- [Spring Boot](https://spring.io/)
- [Spring Security](https://spring.io/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok](https://projectlombok.org/)
- [MYSQL](https://www.mysql.com/)
- [OAuth2]()
- [KeyCloak]()
- [Spring Cloud]()
- [Cloud Gateway]()
- [Eureka]()
- [Cloud Config]()
- [Distributed Log Tracing]()
- [Circuit Breaker]()
- [Redis]()
- [Mockito]()

## HTTP

### AUTH
#### GET `/login/auth`

Get OAuth2 credentials

#### Response body

```json
{
  "userId": "example@gmail.com",
  "accessToken": "{accessToken}",
  "refreshToken": "{refreshToken}",
  "expiresAt": 1721959899,
  "authorities": [
    "OIDC_USER",
    "SCOPE_email",
    "SCOPE_internal",
    "SCOPE_offline_access",
    "SCOPE_openid",
    "SCOPE_profile"
  ]
}
```
<br />

### PRODUCTS
#### GET `/v1/products/:productId`

Return data from single product
#### Response body

```json
{
  "productId": 1,
  "productName": "Notebook",
  "price": 200.99,
  "quantity": 10
}
```

#### POST `/v1/products`

Create a new product
#### Request body

```json
{
  "productName": "Notebook",
  "price": 200.99,
  "quantity": 10
}
```

#### POST `/v1/products`

Create a new product
#### Request body

```json
{
  "productName": "Notebook",
  "price": 200.99,
  "quantity": 10
}
```

<br />

### PAYMENTS
#### GET `/v1/payments/orders/:orderId`

Return data from single payments
#### Response body

```json
{
  "paymentId": 2,
  "orderId": 508,
  "paymentMode": "PIX",
  "referenceNumber": "",
  "paymentDate": "2024-07-20T01:05:07.058159Z",
  "paymentStatus": "SUCCESS",
  "totalAmount": 200.99
}
```

#### POST `/v1/payments`

Do a payment
#### Request body

```json
{
  "orderId": 508,
  "paymentMode": "PIX",
  "referenceNumber": "",
  "totalAmount": 200.99
}
```

<br />

### ORDERS
#### GET `/v1/orders/:orderId`

Return data from single order
#### Response body

```json
{
  "orderId": 508,
  "orderDate": "2024-07-20T01:05:07.044158Z",
  "totalAmount": 200.99,
  "orderStatus": "PLACED",
  "productDetails": {
    "productId": 1,
    "productName": "Notebook"
  },
  "transactionDetails": {
    "paymentId": 2,
    "paymentStatus": "SUCCESS",
    "paymentMode": "PIX",
    "paymentDate": "2024-07-20T01:05:07.058159Z"
  }
}
```

#### GET `/v1/orders/place-order`

Create a new product
#### Request body

```json
{
  "productId": 1,
  "totalAmount": 200.99,
  "quantity": 1,
  "paymentMode": "PIX"
}
```

<br />

#### CLOUD GATEWAY BASE URL `http://localhost:9090`


## Building
You'll need [Java 17+](https://www.oracle.com/br/java/technologies/javase-jdk17-downloads.html) and [Maven](https://maven.apache.org/download.cgi) installed on your computer in order to build this app.

```bash
$ git clone https://github.com/eric-souzams/spring-oauth-keycloak.git
$ cd spring-oauth-keycloak

$ mvn clean install -DskipTests
$ mvn spring-boot:run
```