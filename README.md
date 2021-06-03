# Koppeltaal-2.0-Spring-JWKS

spring-boot-starter-jwks dependency that automatically adds the jwks endpoint to a Spring Boot
application

Simply add the dependency to your project, configure the jwt properties (generates keypair if not
configured), and verify that the endpoint has been added
at `http://<HOST:PORT>/.well-known/jwks.json`

## properties

The following properties can be configured

```properties
# Leave empty to get from request
jwks.aud=
# Leave empty to get from request
jwks.iss=
# Leave empty to auto generate
jwks.signingPublicKey=
# Leave empty to auto generate
jwks.signingPrivateKey=
jwks.signingAlgorithm=RSA512
```

## Adding this dependency to other projects

This project writes the jar
to [GitHub Packages](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry). 
In order to retrieve the jar, you'll need to have a GitHub server with
a [Personal Access Token](https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token)
(PAT) configured in your `~/.m2/settings.xml`. The PAT needs at least the `read:packages` scope.

The `<server>` tag should be added like this, replace the  username and password:
```xml
    <server>
      <id>github</id>
      <username>{{YOUR_GITHUB_USERNAME}}</username>
      <password>{{YOUR_GITHUB_PERSONAL_ACCESS_TOKEN}}</password>
    </server>
```