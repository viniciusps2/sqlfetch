# SQL Fetch - Spring Boot with Virtual Threads

This is a Spring Boot implementation of the SQL Fetch tool that uses Java 21 Virtual Threads (Project Loom) for improved performance and scalability.

## Features

- **Virtual Threads**: Uses Spring Boot 3.2+ with Java 21 virtual threads for efficient request handling
- **REST API**: Exposes endpoints for building SQL queries
- **SQL Query Builder**: Dynamically constructs SQL queries based on configuration

## Requirements

- Java 21 or higher
- Maven 3.6+

## Building the Application

```bash
mvn clean package
```

## Running the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/sqlfetch-1.0.0.jar
```

The application will start on port 8080 by default.

## Virtual Threads Configuration

Virtual threads are enabled in `application.properties`:

```properties
spring.threads.virtual.enabled=true
```

This configuration enables virtual threads for all web requests, allowing the application to handle many concurrent requests efficiently.

## API Endpoints

### Health Check

```bash
GET /api/health
```

Returns the application status and information about the current thread (which will be a virtual thread).

Response:
```json
{
  "status": "UP",
  "virtualThreads": "enabled",
  "threadInfo": "VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1"
}
```

### Build Query

```bash
POST /api/build-query
Content-Type: application/json
```

Request body example:
```json
{
  "tables": [
    {
      "name": "configuration",
      "primaryKeys": ["id"]
    }
  ],
  "joins": [
    {
      "leftTable": "configuration",
      "rightTable": "feature_category",
      "keys": [
        {
          "left": "id",
          "right": "configurationid"
        }
      ]
    }
  ],
  "fetch": {
    "table": "configuration",
    "keys": [
      {
        "name": "id",
        "value": "1"
      }
    ]
  }
}
```

Response:
```json
{
  "query": "SELECT configuration.id AS configuration@id FROM configuration LEFT JOIN feature_category ON feature_category.configurationid = configuration.id WHERE configuration.id = '1'"
}
```

## Testing

Run the tests:

```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/sqlfetch/
│   │   ├── SqlFetchApplication.java       # Main Spring Boot application
│   │   ├── controller/
│   │   │   └── QueryController.java       # REST API endpoints
│   │   ├── service/
│   │   │   └── QueryBuilderService.java   # Query building logic
│   │   └── model/                         # Data models
│   │       ├── Config.java
│   │       ├── Table.java
│   │       ├── Join.java
│   │       ├── JoinKey.java
│   │       ├── Fetch.java
│   │       └── FetchKey.java
│   └── resources/
│       └── application.properties         # Application configuration
└── test/
    └── java/com/sqlfetch/
        └── service/
            └── QueryBuilderServiceTest.java # Unit tests
```

## Why Virtual Threads?

Virtual threads (Project Loom) provide several benefits:

1. **Scalability**: Can handle millions of concurrent requests with minimal memory overhead
2. **Simplicity**: Write blocking code that performs like async code
3. **Resource Efficiency**: Much lighter than platform threads (about 1KB vs 1MB per thread)
4. **Performance**: Better throughput for I/O-bound operations like database queries

In this application, every HTTP request is handled by a virtual thread, allowing the application to efficiently serve many concurrent query building requests.
