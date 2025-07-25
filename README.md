# gcp-iprange-api

REST API in Kotlin (Spring Boot) to fetch and filter live GCP IP ranges by region and IP version.

### Setup & Start mit Docker Compose ###
#### 1. Java-Projekt bauen ####
Stelle sicher, dass dein Java-Projekt gebaut ist und die JAR-Datei unter build/libs/ liegt.
Beispiel mit Gradle:
./gradlew build
#### 2. Docker Compose starten ####
Im Projektverzeichnis (wo docker-compose.yml liegt) den Container bauen und starten:
docker compose up --build
#### 3. Anwendung nutzen ####
Die Anwendung ist erreichbar unter: http://localhost:8080
#### 4. Container stoppen ####
Um die Container zu stoppen, kannst du im selben Verzeichnis:
docker compose down

