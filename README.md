# GCP IP Range API – Kotlin Spring Boot Application

This project is a Spring Boot application written in Kotlin that fetches public IP ranges from Google Cloud Platform (GCP) and exposes a filtered list via a REST API. It is useful for generating IP filters for security groups based on region and IP version.

## Description

The application dynamically loads IP range data from the official GCP source:

> https://www.gstatic.com/ipranges/cloud.json

It provides a REST endpoint that filters the ranges by region and IP version (IPv4/IPv6), returning the results in plain text format.

## Features

- Spring Boot 3 application using Kotlin
- REST API for accessing filtered IP ranges
- Supports regions:
    - `EU`, `US`, `ME`, `NA`, `SA`, `AS`, `AF`, `AUS`, `ALL`
- Supports IP versions:
    - `ipv4`, `ipv6`, `all` (default)
- Output in MIME type `text/plain` (one IP range per line)
- No data storage – the file is processed live on each request
- Includes unit tests
- Dockerfile and GitHub Actions pipeline

## API Endpoint

### `GET /ip-ranges`
Returns a list of IP ranges filtered by query parameters.

#### Query Parameters:

| Name        | Required | Values                                                           | Description                                |
|-------------|----------|------------------------------------------------------------------|--------------------------------------------|
| `region`    | No       | `EU`, `US`, `ME`, `NA`, `SA`, `AS`, `AF`, `AUS`, `ALL` (default) | Region to filter                            |
| `ipVersion` | No       | `IPV4`, `IPV6`, `ALL` (default)                                  | IP version to filter                        |

#### Example Requests:

```http
GET /api/v1/gcp-ipranges
GET /api/v1/gcp-ipranges?region=EU
GET /api/v1/gcp-ipranges?region=US&ipVersion=ipv6
GET /api/v1/gcp-ipranges?region=ALL&ipVersion=ipv4
```

## Setup & Start with Docker Compose

### 1. Build the Kotlin project

Make sure your Kotlin project is built and the JAR file is located in `build/libs/`.
Example using Gradle:
```bash
./gradlew build
```

### 2. Start Docker Compose
In the project directory (where docker-compose.yml is located), build and start the container:
```bash
docker compose up --build
```

### 3. Use the application
The application is accessible at: http://localhost:8080

### 4. Stop the containers
To stop the containers, run in the same directory:
```bash
docker compose down
```

