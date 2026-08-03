COMPOSE = docker compose

build:
	$(COMPOSE) up --build

down:
	$(COMPOSE) down

ps:
	$(COMPOSE) ps

stop:
	$(COMPOSE) stop

clean:
	$(COMPOSE) down -v
