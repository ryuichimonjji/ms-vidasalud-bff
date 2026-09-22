# VidaSalud — BFF (Backend For Frontend)

Microservicio Spring Boot que actúa de intermediario entre el frontend y `ms-vidasalud-appointments`. Valida el JWT emitido por Azure AD y aplica autorización por rol.

## Stack
- Java 17, Spring Boot 4, Spring Security (OAuth2 Resource Server)

## Cómo levantarlo localmente

\`\`\`bash
mvn clean package -DskipTests
java -jar target/ms-vidasalud-bff-0.0.1-SNAPSHOT.jar
\`\`\`

Corre en el puerto `8080`.

## Configuración (`src/main/resources/application.yml`)

\`\`\`yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://login.microsoftonline.com/<TENANT_ID>/v2.0
          audiences:
            - <API_CLIENT_ID>

appointments:
  service:
    url: http://localhost:8081
\`\`\`

## Endpoints

- `GET /api/appointments` — lista atenciones
- `GET /api/appointments/{id}` — detalle de una atención
- `POST /api/appointments` — crea una atención
- `PUT /api/appointments/{id}/status` — cambia el estado

Todos requieren `Authorization: Bearer <token>` con rol `Admin`, `Operador` o `Cliente`.
