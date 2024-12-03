
# Anagram BE Application

 
## About The Project
This is a simple kotlin-spring application that verifies and retrieves temporary stored anagrams.
 
## Features

### Endpoints
 The following endpoints are available 
  - **{url}/v1/anagrams/put**: Verifies if two texts are anagrams of each other.If true both texts are saved in memory for feuture request
  - **{url}/v1/anagrams/post**: Searches for all previously stored anagrams of the given text.

### Storage Options
The application supports two configurable non persistent storage options, providing flexibility based on your deployment environment and requirements:

1. Built In ConcurrentHashMap (default)
   Used for lightweight deployments 

2. Hazelcast 
   A very expensive in memmory storage for scalability. 
   See [https://hazelcast.com] for details.
   Set the following environment variables in the .env file or your runtime configuration:

    - STORAGE_TYPE=hazelcast
    - HAZELCAST_NODE_IPS=<ip:<port>, <ip>:<port>
    - HAZELCAST_CLUSTER_NAME=<hazelcast-cluster-name>
 
### Configuration
The application can be configured using environment variables. Key variables include:
| Variable                  | Description                         | Default     |
|---------------------------|------------------------------------|--------------|
| `STORAGE_TYPE`            | Database connection string         | `map`        |
| `HAZELCAST_NODE_IPS`      | list of nodes                      |  none        |
| `HAZELCAST_CLUSTER_NAME`  | Dname of the DB cluster            |  none        |

### Deployment 

  - Directly on a host with Java and maven installed.
    ```bash
    mvn package && java -jar target/anagram-1.0.0.jar
    ```

  - In an isolated Docker container.
    ```bash
    docker build -t app-prod .
    docker run -p 8080:8080 app-prod
    ```

###  To test with two hazelcast instances
  - Run with Docker Compose

    Prerequisites
    Docker: Ensure Docker and Docker Compose are installed on your system.
    Environment configuration: Create a .env file in the project root with the necessary configuration.
    ```bash
    docker compose up
    ```    
 
### Development 
For development in docker just go to the app folder and run
```bash
docker run -it -u $(id -u ${USER}):$(id -g ${USER}) -p 8080:8080 -v  $(pwd):/app maven:3.9.9-eclipse-temurin-11 bash
```
 
### Swagger API Documentation

The application includes a Swagger UI to provide an interactive interface for exploring and testing the available endpoints. 
This makes it easier to understand the API and test its functionality without requiring additional tools.

Accessing Swagger UI
Once the application is running, the Swagger UI can be accessed at:

URL: [http://localhost:8080/]
This provides a user-friendly interface to view:

- API Endpoints,Request/Response schemas,Example payloads,Real-time testing options for endpoints
- Trying Out the API
    Navigate to the Swagger UI in your browser.
    Select an endpoint to expand its details.
    Enter the required parameters or request body.
    Click "Try it out" to execute the request directly from the interface.
    View the response, including status codes, headers, and body.

Notes
Swagger is automatically enabled when the application starts. No additional setup is required.

 
## Whom should I contact
Vera Voynovska

## Acknowledgments

### openapi-generator-cli
This project was generated with openapi-generator-cli. 
 - License  [https://github.com/OpenAPITools/openapi-generator-cli/blob/master/LICENSE]
 - Please visit [https://github.com/OpenAPITools/openapi-generator-cli] to find out more about it.



 