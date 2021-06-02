# Koppeltaal-2.0-Spring-JWKS
spring-boot-starter-jwks dependency that automatically adds the jwks endpoint to a Spring Boot application

Simply add the dependency to your project (as a maven dependency or git submodule), configure the jwt properties (generates keypair if not configured), and verify that the endpoint is added at `http://<HOST:PORT>/.well-known/jwks.json`
