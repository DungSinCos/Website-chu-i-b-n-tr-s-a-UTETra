# springboot-jsp-starter

A minimal Spring Boot starter that wires together JSP/JSTL, SiteMesh, Bootstrap, JPA (MySQL or SQL Server), JWT, WebSocket and Cloudinary. It ships with a single page, `home.jsp`, so you can build on a working skeleton.

## Tech stack

| Area | Technology |
|------|-----------|
| Framework | Spring Boot 3.3.4, Java 17, WAR packaging |
| View layer | JSP + JSTL (Jakarta), `tomcat-embed-jasper` |
| Layout | SiteMesh 3.2.0 (decorator) |
| UI | Bootstrap 5.3.3 (via WebJars) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL (default) or SQL Server |
| Security | Spring Security (stateless) + JJWT 0.12.6 |
| Realtime | Spring WebSocket (STOMP + SockJS) |
| Media | Cloudinary |

## Project structure

```
pom.xml
src/main/java/com/example/app/
  Application.java                    Entry point (also a SpringBootServletInitializer)
  controller/HomeController.java      Maps / and /home to the "home" view
  config/
    SiteMeshConfig.java               Registers the SiteMesh filter
    SecurityConfig.java               Stateless security skeleton (permits all for now)
    WebSocketConfig.java              STOMP endpoint /ws, brokers /topic and /queue
    CloudinaryConfig.java             Cloudinary bean built from properties
src/main/resources/
  application.properties              Common settings, JWT, Cloudinary
  application-mysql.properties        MySQL datasource profile
  application-sqlserver.properties    SQL Server datasource profile
src/main/webapp/WEB-INF/
  views/home.jsp                      The home page
  decorators/main.jsp                 SiteMesh layout (navbar, footer, Bootstrap)
  sitemesh3.xml                       Decorator mappings and exclusions
```

## Prerequisites

- JDK 17+
- Maven 3.9+ (or the Maven support bundled with Eclipse / m2e)
- A running MySQL or SQL Server instance
- A Cloudinary account (only needed once you actually upload media)

## Getting started

### Run in Eclipse
1. Unzip the project.
2. Go to **File -> Import -> Maven -> Existing Maven Projects** and select the folder.
3. Right-click `Application.java` and choose **Run As -> Java Application** (or **Spring Boot App** if you have STS installed).
4. Open <http://localhost:8080/>.

### Run from the command line
```bash
mvn spring-boot:run
```

### Build a WAR
```bash
mvn clean package
java -jar target/springboot-jsp-starter.war
```

## Configuration

### Database
The app connects to the database at startup, so it will not start if the database is unreachable.

**MySQL (default)** - edit `application-mysql.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```
The database is created automatically if it does not exist.

**SQL Server** - switch the active profile in `application.properties`:
```properties
spring.profiles.active=sqlserver
```
Then edit `application-sqlserver.properties`. The database (`mydb` by default) must already exist.

You can also override the profile without editing files:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=sqlserver
```

### Environment variables

| Variable | Purpose | Default (dev only) |
|----------|---------|--------------------|
| `JWT_SECRET` | Base64-encoded HMAC key, at least 256 bits | Built-in dev key |
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name | `your_cloud_name` |
| `CLOUDINARY_API_KEY` | Cloudinary API key | `your_api_key` |
| `CLOUDINARY_API_SECRET` | Cloudinary API secret | `your_api_secret` |

Generate a secure JWT secret with:
```bash
openssl rand -base64 48
```
**Never deploy with the default JWT secret.**

## How the pieces fit together

- **JSP views** live in `WEB-INF/views/` and are resolved by `spring.mvc.view.prefix/suffix`. A controller returning `"home"` renders `WEB-INF/views/home.jsp`.
- **SiteMesh** wraps every page response with `WEB-INF/decorators/main.jsp`. The page's `<title>`, `<head>` and `<body>` are inserted where the decorator has `<sitemesh:write property="..."/>`. Paths under `/webjars/*` and `/ws/*` are excluded in `sitemesh3.xml`.
- **Bootstrap** is served from WebJars, for example `/webjars/bootstrap/5.3.3/css/bootstrap.min.css`. If you change `bootstrap.version` in `pom.xml`, update the paths in `main.jsp` too.
- **Security** is currently open (`permitAll`) with CSRF disabled and stateless sessions, ready for a JWT filter. `jjwt` is on the classpath, but no token code exists yet.
- **WebSocket** clients connect to `/ws` (SockJS) and subscribe to `/topic/**` or `/queue/**`. Messages sent to `/app/**` are routed to `@MessageMapping` methods.
- **Cloudinary** is exposed as an injectable `Cloudinary` bean.

## Adding a new page

1. Create `WEB-INF/views/about.jsp` with `<title>` and `<body>` (no need to repeat the layout):
   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" %>
   <html>
   <head><title>About</title></head>
   <body><h1>About</h1></body>
   </html>
   ```
2. Add a controller method:
   ```java
   @GetMapping("/about")
   public String about() { return "about"; }
   ```

## Suggested next steps

- Add JPA entities and repositories (`@Entity`, `JpaRepository`).
- Implement a `JwtService` and a `OncePerRequestFilter` that validates the `Authorization: Bearer` header, then restrict routes in `SecurityConfig`.
- Add `@MessageMapping` handlers and a STOMP client script for realtime features.
- Add an upload service that uses the `Cloudinary` bean.
- Replace `ddl-auto=update` with Flyway or Liquibase migrations for production.

## Troubleshooting

| Problem | Likely cause / fix |
|---------|--------------------|
| Startup fails with a connection error | Database is not running or credentials are wrong. |
| Home page shows raw JSP or a 404 | Run the project as a Maven project so `src/main/webapp` is used, and check the view prefix/suffix. |
| Page is not decorated | Check `sitemesh3.xml` and confirm the request path is not excluded. |
| `sitemesh:3.2.0` cannot be resolved | Check Maven Central for the latest 3.2.x version and update `sitemesh.version` in `pom.xml`. |
| Eclipse shows red errors after import | Right-click the project -> **Maven -> Update Project** (Alt+F5). |

## License

Add your license here.