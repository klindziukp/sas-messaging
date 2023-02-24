# Messaging system
Messaging system with one `producers` and two `consumers` based on RabbitMQ

## System Services
- Messaging Producer Service
- Messaging Heartbeat Consumer Service
- Messaging Reply Consumer Service
## Services set up via docker-compose
- Build Docker images via command(it will take time)
```bash
./gradlew clean build bootBuildImage
```
- Start system with docker compose
```bash
docker-compose -f docker-compose.yml up
```
## Service set up via docker-compose + IntelliJ
- Start infra system with docker compose
```bash
docker-compose -f docker-compose-infra.yml up
```
- Start `MessagingProducerApplication` application with IntelliJ or
```bash
./gradlew :messaging-producer:clean build :messaging-producer:bootRun 
```
- Start `MessagingHeartbeatApplication` application with IntelliJ or
```bash
./gradlew :messaging-heartbeat-consumer:clean build :messaging-heartbeat-consumer:bootRun 
```
- Start `MessagingReplyApplication` application with IntelliJ or
```bash
./gradlew :messaging-reply-consumer:clean build :messaging-reply-consumer:bootRun 
```

## Messaging Flow
#### Heartbeat flow
```
Call to Producer Service ---> MessageBroker ---> Message Handling via Heartbeat Consumer
```
#### Request Reply flow
```
Call to Producer Service ---> MessageBroker ---> Message Handling via Reply Consumer Service ---> MessageBroker ---> Message Handling via Producer Service

```
## Open API documentation
- Open 'http://{server}:{port}/api-docs' to view documentation in JSON format for TDM service
```bash
http://localhost:8080/v3/api-docs
```
- Open 'http://{server}:{port}/swagger-ui.html' to view api endpoints for TDM service
```bash
http://localhost:8080/swagger-ui/index.html
```

<p align="left">
    <img src="docs/images/openapi.png" width="1000px" alt="open-api">
</p>

## Database setup
* Run command from __project root__ directory `docker-compose -f docker-compose-mysql.yml up`
* Verify that MySQL container is started with command `docker ps`
## Service setup
### IntelliJ
Hit play button for `com.klindziuk.sas.tdm.gen.TdmApplication`  class
### Gradle
```bash
./gradlew clean build bootRun 
```

## Data generation
### Data generation via gradle
- Fill db with synthetic data
```bash
./gradlew cleanup {gradle-task-name-for-generate-db-items} 
```
- Fill db with real data
```bash
./gradlew cleanup insertRealData  
```
#### Data generation before for performance test execution
```bash
./gradlew cleanup insertRealData(Optional) {gradle-task-name-for-generate-db-items}
./gradlew clean gatlingRun-tdm.TdmSimulation
```

### Data generation via API
Data generator is an app, so it can be started as regular Spring boot app
### IntelliJ
- Hit play button for `com.klindziuk.sas.tdm.gen.GenApplication`  class
### Gradle
```bash
./gradlew :tdm-data-generator:clean build :tdm-data-generator:bootRun 
```
### API Endpoints
Open 'http://{server}:{port}/swagger-ui.html' to view api endpoints for Test data generator
```bash
http://localhost:8081/swagger-ui/index.html
```
<p align="left">
    <img src="docs/images/open-api-generator.png" width="1000px" alt="open-api">
</p>