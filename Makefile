# make file for unix like systems (or use wsl)

dev-prepare:
	docker compose up -d --build postgres pgadmin
	echo "Prepared needed services (db, db admin)"
	docker ps

dev-up-locally:
	docker compose stop app
	echo "Started locally"
	./local-start.sh

dev-up-container:
	docker-compose up -d --build app
	echo "App is started"
	docker ps

dev-down-container:
	docker-compose stop app
	echo "App stopped"
	docker ps

dev-down-all:
	docker compose down
	echo "All services stopped"

migrate-diff:
	./mvnw compile liquibase:diff

migrate-update:
	./mvnw liquibase:update