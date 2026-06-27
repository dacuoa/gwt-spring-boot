# Quick Start Guide

## 5-Minute Setup for Developers

### Prerequisites Check (1 minute)

```bash
# Check Java
javac -version
# Output should be: javac 1.8.0_xxx or higher

# Check Maven
mvn -version
# Output should show Maven version and Java path
```

If these commands fail, see [BUILD.md](BUILD.md) for setup instructions.

### Build (2 minutes)

```bash
cd bike-inspection
mvn clean package -DskipTests
```

### Run (1 minute)

```bash
mvn spring-boot:run
```

Or use the pre-built WAR:
```bash
java -jar target/bike-inspection-1.0-SNAPSHOT.war
```

### Access Application (1 minute)

1. **Main App**: Open browser to http://localhost:8080
2. **Form Page**: Fill out the bicycle inspection form
3. **Submit**: Click "Submit Inspection" to save
4. **Search Page**: Click the Search Results tab
5. **Search**: Enter inspector name or serial number to find inspections
6. **Database**: View raw data at http://localhost:8080/h2-console
   - URL: `jdbc:h2:mem:testdb`
   - User: `sa`
   - Password: (blank)

## Project Structure at a Glance

```
bike-inspection/
├── src/main/java/com/dacuoa/
│   ├── BikeInspectionApp.java          ← Spring Boot entry point
│   ├── client/
│   │   ├── BikeInspection.java         ← GWT entry point
│   │   ├── view/
│   │   │   ├── MainView.java
│   │   │   ├── FormView.java
│   │   │   └── SearchView.java
│   │   ├── presenter/
│   │   │   ├── FormPresenter.java
│   │   │   └── SearchPresenter.java
│   │   └── service/
│   │       └── InspectionService.java
│   ├── controller/
│   │   └── InspectionController.java   ← REST endpoints
│   ├── service/
│   │   └── InspectionService.java      ← Business logic
│   ├── repository/
│   │   └── InspectionRepository.java   ← Database queries
│   ├── model/
│   │   └── Inspection.java             ← Database entity
│   └── dto/
│       └── InspectionDTO.java
├── src/main/resources/
│   └── application.properties           ← Spring Boot config
├── src/main/webapp/
│   ├── index.html                       ← Main page
│   └── WEB-INF/
│       └── web.xml
├── pom.xml                              ← Maven configuration
├── README.md                            ← Full documentation
├── BUILD.md                             ← Build instructions
└── ARCHITECTURE.md                      ← Design details
```

## Key Technologies

| Layer | Technology | Version |
|-------|-----------|---------|
| Frontend | Google Web Toolkit (GWT) | 2.10.0 |
| Backend | Spring Boot | 2.7.15 |
| Database | H2 | Latest |
| Build | Maven | 3.6.0+ |
| Java | JDK | 1.8+ |

## What This App Does

### Page 1: Inspection Form
- Collects bicycle safety inspection data
- Fields: Inspector name, date, bike serial, condition ratings, lights, notes
- Submits data to backend via REST API
- Shows success/error messages

### Page 2: Search Results
- Search by inspector name or bike serial number
- Displays results in a sortable table
- Shows all inspection details
- Real-time query of backend database

## API Endpoints

```
POST   /api/inspections              Create new inspection
GET    /api/inspections/search?q=X   Search inspections
GET    /api/inspections/{id}         Get single inspection
GET    /h2-console                   Database console
```

## MVP Architecture Explained

**Model**: Data objects (Inspection, InspectionDTO, InspectionRow)
**View**: UI components (FormView, SearchView)
**Presenter**: Event handlers (FormPresenter, SearchPresenter)

Flow:
1. User interacts with View (clicks button, enters text)
2. View triggers Presenter event handler
3. Presenter validates & calls Service
4. Service makes HTTP request to backend
5. Backend processes and returns JSON
6. Presenter parses response and updates View
7. User sees updated UI

## Customization

### Change Server Port
Edit `src/main/resources/application.properties`:
```properties
server.port=9000
```

### Modify Form Fields
Edit `src/main/java/com/dacuoa/client/view/FormView.java`

### Add New Inspection Fields
1. Add field to `Inspection.java` entity
2. Add field to `InspectionDTO.java`
3. Add widget to `FormView.java`
4. Add column to `SearchView.java` CellTable
5. Update form in `FormPresenter.java`

### Change Database
Edit `src/main/resources/application.properties`:
```properties
# For PostgreSQL:
spring.datasource.url=jdbc:postgresql://localhost/bike_inspection
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## Troubleshooting

### "Cannot find symbol" compile error
- Check all imports are present
- Run `mvn clean compile`

### Application won't start
- Check port 8080 is free: `netstat -an | findstr :8080`
- Change port in `application.properties`

### CORS errors in browser
- These are expected with GWT + Spring Boot
- CORS is enabled in `BikeInspectionApp.java`

### H2 console shows no tables
- Database is recreated each time app starts
- Submit an inspection first to create data

## Development Workflow

### Running Tests
```bash
mvn test
```

### Live Development
```bash
# Terminal 1
mvn spring-boot:run

# Terminal 2 (make code changes)
# Changes are auto-detected and reloaded
```

### Package for Deployment
```bash
mvn clean package -DskipTests
# Creates: target/bike-inspection-1.0-SNAPSHOT.war
```

### Deploy to Tomcat
```bash
# Copy WAR to Tomcat webapps directory
cp target/bike-inspection-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/

# Access at: http://localhost:8080/bike-inspection-1.0-SNAPSHOT
```

## Next Steps

1. ✅ Build the app: `mvn clean package -DskipTests`
2. ✅ Run the app: `mvn spring-boot:run`
3. ✅ Test form: Submit an inspection
4. ✅ Test search: Search for your inspector name
5. ✅ View database: Check H2 console
6. 📖 Read [README.md](README.md) for full features
7. 🏗️ Read [ARCHITECTURE.md](ARCHITECTURE.md) for design details
8. 🔨 Read [BUILD.md](BUILD.md) for advanced build options

## Quick Reference

### Common Commands
```bash
mvn clean              # Clean build artifacts
mvn compile            # Compile source code
mvn test               # Run tests
mvn package            # Build WAR
mvn spring-boot:run    # Run application
mvn dependency:tree    # Show dependencies
mvn help:describe      # Help on plugins
```

### Quick Fixes
```bash
# Fix encoding issues
export MAVEN_OPTS="-Dfile.encoding=UTF-8"

# Use offline mode (faster, after first build)
mvn clean package -o

# Skip long GWT compilation
mvn clean package -DskipTests -DskipGWT

# Update all dependencies
mvn versions:use-latest-versions
```

### File Locations
- Source code: `src/main/java/com/dacuoa/`
- Configuration: `src/main/resources/application.properties`
- Web files: `src/main/webapp/`
- Build output: `target/`
- Dependencies: `~/.m2/repository/` (automatically downloaded)

## Getting Help

1. **Build errors**: Check [BUILD.md](BUILD.md)
2. **Architecture questions**: Check [ARCHITECTURE.md](ARCHITECTURE.md)
3. **Feature questions**: Check [README.md](README.md)
4. **GWT issues**: See [GWT Documentation](https://www.gwtproject.org)
5. **Spring Boot issues**: See [Spring Boot Docs](https://spring.io/projects/spring-boot)
6. **Database issues**: See [H2 Docs](https://www.h2database.com)

---

**You're ready to go!** 🚀

Start with: `mvn clean package -DskipTests && mvn spring-boot:run`
