
# Anagram BE Application

## About The Project
This is a simple kotlin-spring application that verifies and retrieves temporary stored anagrams.
The purpose of the application is to look into kotlin with springboot and experiment with different storage types.
 
## Features

### Endpoints
 The following endpoints are available 
  - **{url}/v1/anagrams/put**: Verifies if two texts are anagrams of each other.If true both texts are saved in memory for feuture requests.
  - **{url}/v1/anagrams/post**: Searches for all previously stored anagrams of the given text.

### Storage Options
The application supports two configurable non persistent storage options, providing flexibility based on your deployment environment and requirements:

1. **Built In ConcurrentHashMap (default)**
   Used for lightweight deployments 

  **Try ConcurrentHashMap with Docker Compose**
    ```bash
     docker compose -f docker-compose-map.yaml up
    ```  

2. **Hazelcast** 
   An in memmory storage for scalability. 
   See [https://hazelcast.com] for details.
   Set the following environment variables in the .env file or your runtime configuration:

    - STORAGE_TYPE=hazelcast
    - HAZELCAST_NODE_IPS=<ip:<port>, <ip>:<port>
    - HAZELCAST_CLUSTER_NAME=<hazelcast-cluster-name>

  **Try Hazelcast with Docker Compose**
    ```bash
     docker compose -f docker-compose-hazelcast.yaml up
    ```   

3. Redis
   Set the following environment variables in the .env file or your runtime configuration:

    - STORAGE_TYPE=redis
    - REDIS_URL=<ip>:<port>

  **Try Redis with Docker Compose**
    ```bash
     docker compose -f docker-compose-redis.yaml up
    ```  

### Configuration
The application can be configured using environment variables. Key variables include:

| Variable                  | Description                   |Values                       | Default          |
|---------------------------|------------------------------------|------------------------|------------------|
| `STORAGE_TYPE`            | Database connection string    |`map`, `haselcast`, `map`    | `map`            |
| `HAZELCAST_NODE_IPS`      | list of nodes                 |`<ip1>:<port1>,<ip2>:<port2>`| `none`           |
| `HAZELCAST_CLUSTER_NAME`  | Name of the DB cluster        |                             | `default-cluster`|
| `REDIS_URL`               | redis url                     |      `<ip=>:<port>`         | `localhost:6379` |

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
 
### Development 

For development in docker just go to the app folder and run
```bash
docker run -it -u $(id -u ${USER}):$(id -g ${USER}) -p 8080:8080 -v  $(pwd):/app maven:3.9.9-eclipse-temurin-21 bash
```

### Swagger API Documentation

The application includes a Swagger UI to provide an interactive interface for exploring and testing the available endpoints. 
This makes it easier to understand the API and test its functionality without requiring additional tools.


Once the application is running, the Swagger UI can be accessed at:  [http://localhost:8080]

This provides a user-friendly interface to view:

- API Endpoints,Request/Response schemas,Example payloads,Real-time testing options for endpoints
- Trying Out the API
    Navigate to the Swagger UI in your browser.
    Select an endpoint to expand its details.
    Enter the required parameters or request body.
    Click **"Try it out"** to execute the request directly from the interface.
    View the response, including status codes, headers, and body.
 
## Acknowledgments

### openapi-generator-cli
This project was generated with openapi-generator-cli. 
 - License  [https://github.com/OpenAPITools/openapi-generator-cli/blob/master/LICENSE]
 - Please visit [https://github.com/OpenAPITools/openapi-generator-cli] to find out more about it.



 