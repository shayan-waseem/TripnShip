# Trip & Ship

Trip & Ship is a hybrid travel booking and cargo logistics demo application built with Java Spring Boot and Thymeleaf. It demonstrates a small production-like web application architecture and several classic design patterns while using JSON files for lightweight demo persistence.

## Features
- Book travel packages (builder-backed `TravelPackage` model)
- Register and track cargo shipments (State Pattern for shipment lifecycle)
- Configurable shipping and pricing strategies (Strategy Pattern)
- Admin portal for managing packages, cargo, and users
- Simple JSON-backed persistence (for demo/testing)

## Tech stack
- Java 21
- Spring Boot 3.5.x (spring-boot-starter-web, thymeleaf, security)
- Thymeleaf server-side templates
- Jackson for JSON read/write
- Maven build

## Repository layout (important paths)
- `src/main/java/com/tripandship/` — main Java sources (controllers, services, models, patterns, utils)
- `src/main/resources/templates/` — Thymeleaf templates (user & admin views)
- `src/main/resources/application.properties` — application configuration
- `database/` — demo JSON persistence files (users.json, travelpackages.json, cargoShipments.json, etc.)
- `pom.xml` — Maven build descriptor

## Design patterns used (representative classes)
- State: `patterns/state/*` (`CargoState`, `PendingState`, `InTransitState`, `DeliveredState`)
- Strategy: `patterns/strategy/*` (shipping/payment strategies)
- Builder: `model/TravelPackage.java` (fluent builder for travel packages)
- Command: `patterns/command/*` (admin commands for package/cargo actions)
- Decorator: `patterns/decorator/*` (insurance, express delivery)
- Singleton: `patterns/singleton/DatabaseManager.java`
- Observer: `patterns/observer/*` (portal notifications)
- Adapter/Facade/Factory: assorted helpers for external integration and simplified APIs

## How to run (developer)
1. Requirements: Java 21 and Maven installed.
2. From project root:

```bash
mvn -DskipTests spring-boot:run
```

3. Open http://localhost:8082/

## Configuration
- `src/main/resources/application.properties` controls `server.port` and `database.path` (default `database/`).

## Quick admin demo
- Demo admin credentials are in `database/users.json` (for development only): username `admin` / password `password123`.
- Admin pages: `/admin/dashboard`, `/admin/users`, `/admin/packages`, `/admin/cargo`.

## Data & persistence
- Demo persistence uses JSON files under the `database/` folder. Files include `users.json`, `travelpackages.json`, `cargoShipments.json`, `bookings.json`, `payments.json`, `analytics.json`, `reviews.json`, and `wishlist.json`.

## Security & important notes (read before real use)
- This project is a demo: **do not** use the included JSON files or credentials in production.
- `database/users.json` currently contains plaintext passwords. Replace with a secure store and hashed passwords (BCrypt) before production use.
- Payment details (card numbers / CVV) must never be persisted in plain text or logged. Integrate a PCI-compliant payment gateway and tokenize card data.
- Ensure HTTPS/TLS, CSRF protection, input validation, and proper session management for public deployments.

## Testing & verification
- Basic development smoke test:

```bash
# start app
mvn -DskipTests spring-boot:run

# (example) login as admin via curl and fetch users page
curl -i -c cookies.txt -d "username=admin&password=password123" -X POST http://localhost:8082/login
curl -i -b cookies.txt http://localhost:8082/admin/users
```

## Known issues & TODOs
- Replace JSON persistence with a proper database (H2/Postgres) for multi-user tests.
- Improve payment handling: add Luhn/expiry validation, remove storage of card/CVV, integrate a payment gateway.
- Add password hashing (BCrypt) and secure authentication flows.
- Add audit logging around admin `Command` executions.
- Add automated tests (unit + integration) and CI pipeline.

## Contributing
- Add features under `src/main/java/com/tripandship/` and corresponding templates.
- Keep template edits in `src/main/resources/templates/` (avoid editing `target/classes` directly for permanent changes).

## Contact
- Project owner / maintainer: add your name and contact information here.

---
If you want, I can also generate a one-page project report (Markdown or PDF) with file-level references and a routes list. Tell me which format you prefer.