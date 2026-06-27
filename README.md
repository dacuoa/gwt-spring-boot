# Bike Inspection GWT Application

A full-stack web application built with Google Web Toolkit (GWT) frontend and Spring Boot backend for managing bicycle safety inspections.

## Architecture Overview

### Frontend (GWT Client)
- **Language**: Java (compiled to JavaScript)
- **Framework**: Google Web Toolkit 2.10.0
- **Pattern**: MVP (Model-View-Presenter)
- **Components**:
  - `MainView`: Navigation and tab management
  - `FormView`: Inspection form with MVP presenter
  - `SearchView`: Search and results display with CellTable
  - `InspectionService`: Client-side API communication using RequestBuilder

### Backend (Spring Boot)
- **Framework**: Spring Boot 2.7.15
- **Database**: H2 in-memory with console enabled
- **Architecture**: REST API with JPA/Hibernate
- **Components**:
  - `InspectionController`: REST endpoints
  - `InspectionService`: Business logic
  - `InspectionRepository`: Data access layer
  - `Inspection` Entity: JPA model

### Communication
- **Protocol**: HTTP/REST
- **Format**: JSON (Jackson)
- **Client**: GWT RequestBuilder
- **CORS**: Enabled for cross-origin requests

## Project Structure

```
src/
├── main/
│   ├── java/com/dacuoa/
│   │   ├── BikeInspectionApp.java (Spring Boot entry point)
│   │   ├── BikeInspection.gwt.xml (GWT module descriptor)
│   │   ├── client/
│   │   │   ├── BikeInspection.java (GWT entry point)
│   │   │   ├── view/
│   │   │   │   ├── MainView.java
│   │   │   │   ├── FormView.java
│   │   │   │   └── SearchView.java
│   │   │   ├── presenter/
│   │   │   │   ├── FormPresenter.java
│   │   │   │   └── SearchPresenter.java
│   │   │   └── service/
│   │   │       └── InspectionService.java (HTTP client)
│   │   ├── controller/
│   │   │   └── InspectionController.java
│   │   ├── service/
│   │   │   └── InspectionService.java (Business logic)
│   │   ├── repository/
│   │   │   └── InspectionRepository.java
│   │   ├── model/
│   │   │   └── Inspection.java (JPA Entity)
│   │   └── dto/
│   │       └── InspectionDTO.java
│   ├── resources/
│   │   └── application.properties (Spring Boot config)
│   └── webapp/
│       ├── index.html (Main HTML page)
│       └── WEB-INF/
│           └── web.xml (Web application descriptor)
└── pom.xml
```

## Building the Project

### Prerequisites
- **Java**: JDK 8 or higher (NOT just JRE)
- **Maven**: 3.6.0 or higher
- **Git**: For version control

### Build Steps

```bash
# Clone or navigate to the project
cd bike-inspection

# Clean and package the application
mvn clean package -DskipTests

# The output WAR file will be created at:
# target/bike-inspection-1.0-SNAPSHOT.war
```

### Important Notes on Build Environment

**This project requires a full JDK installation**, not just the Java Runtime Environment (JRE). The system must have `javac` available for Maven to compile the Java source code.

If you encounter the error "No compiler is provided in this environment":
1. Download and install a JDK from [oracle.com](https://www.oracle.com/java/technologies/) or [adoptopenjdk.net](https://adoptopenjdk.net/)
2. Set the `JAVA_HOME` environment variable to point to your JDK installation
3. Verify with: `javac -version`

## Running the Application

### Option 1: Using Spring Boot Maven Plugin
```bash
mvn spring-boot:run
```

### Option 2: Running the WAR file
```bash
mvn clean package
java -jar target/bike-inspection-1.0-SNAPSHOT.war
```

### Access the Application
- **Main Application**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - User: `sa`
  - Password: (leave empty)

## API Endpoints

### Create Inspection
```
POST /api/inspections
Content-Type: application/json

{
  "inspectorName": "John Doe",
  "inspectionDate": "2026-06-27",
  "bikeSerialNumber": "SN123456",
  "frameCondition": "Good",
  "brakes": "Good",
  "tyres": "Fair",
  "lightsPresent": true,
  "notes": "Everything looks good"
}
```

### Search Inspections
```
GET /api/inspections/search?q=John
```

### Get Inspection by ID
```
GET /api/inspections/{id}
```

## Inspection Form Fields

1. **Inspector Name** (Text): Name of the person conducting the inspection
2. **Inspection Date** (Date): Date of inspection (YYYY-MM-DD format)
3. **Bike Serial Number** (Text): Unique identifier for the bicycle
4. **Frame Condition** (Select): Good / Fair / Poor
5. **Brakes** (Select): Good / Fair / Poor
6. **Tyres** (Select): Good / Fair / Poor
7. **Lights** (Checkbox): Present or not present
8. **Notes** (Textarea): Additional inspection notes

## Technology Stack Details

### Frontend Technologies
- **GWT 2.10.0**: JavaScript compiler for Java
- **GWT Widgets**: TextBox, ListBox, CheckBox, TextArea, CellTable, Button, Label
- **MVP Pattern**: 
  - Model: Data objects (InspectionRow, InspectionDTO)
  - View: UI components (FormView, SearchView)
  - Presenter: Logic layers (FormPresenter, SearchPresenter)

### Backend Technologies
- **Spring Boot 2.7.15**: Application framework
- **Spring Data JPA**: ORM framework
- **Hibernate**: JPA implementation
- **H2 Database**: Embedded SQL database
- **Jackson**: JSON serialization/deserialization

### Build & Deployment
- **Maven**: Build automation
- **GWT Maven Plugin**: Compiles GWT modules to JavaScript
- **WAR**: Web application archive for deployment

## Configuration (application.properties)

```properties
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Jackson
spring.jackson.serialization.write-dates-as-timestamps=false
```

## MVP Pattern Implementation

### FormView & FormPresenter
1. **View** (FormView) displays form widgets
2. **User** enters inspection data and clicks Submit
3. **Presenter** (FormPresenter) validates data
4. **Presenter** calls InspectionService to send HTTP request
5. **Callback** returns success/error to Presenter
6. **Presenter** updates View with status message

### SearchView & SearchPresenter
1. **View** (SearchView) displays search input and CellTable
2. **User** enters search term and clicks Search
3. **Presenter** (SearchPresenter) validates input
4. **Presenter** calls InspectionService to query backend
5. **JSON Response** is parsed into InspectionRow objects
6. **Presenter** updates CellTable with results

## Client-Server Communication Flow

```
Client (GWT)                          Server (Spring Boot)
    |                                        |
    |-- POST /api/inspections ------>       |
    |    (JSON: InspectionDTO)              |
    |                                        v
    |                         InspectionController.createInspection()
    |                                        |
    |                                        v
    |                         InspectionService.saveInspection()
    |                                        |
    |                                        v
    |                         InspectionRepository.save()
    |                                        |
    |                                        v
    |                         H2 Database (Persistence)
    |                                        |
    |<------ 200 OK + InspectionDTO --------
    |
    |-- GET /api/inspections/search?q=term ->
    |                                        |
    |                                        v
    |                      InspectionRepository.searchByInspectorNameOrSerialNumber()
    |                                        |
    |                                        v
    |<------ 200 OK + JSON Array -----------
    |
    v
Parse JSON & Update CellTable in SearchView
```

## Development Notes

### GWT Compilation
- GWT code is compiled from Java to JavaScript during the Maven build
- The resulting `bikeInspection` directory is served as a static web resource
- GWT's RequestBuilder provides automatic CORS and HTTP handling

### Database
- H2 is configured to run in-memory only (`jdbc:h2:mem:testdb`)
- The `create-drop` strategy means the database is created on startup and dropped on shutdown
- Use the H2 Console to view/query the database at runtime

### CORS Configuration
- CORS is enabled for all `/api/**` endpoints to allow requests from the GWT client
- Configured in `BikeInspectionApp.WebMvcConfigurer` bean

## Troubleshooting

### "No compiler is provided in this environment"
- **Cause**: Only JRE is installed, not JDK
- **Solution**: Install a JDK with javac compiler and update JAVA_HOME

### GWT module not found
- **Cause**: GWT compilation failed or module path is incorrect
- **Solution**: Check BikeInspection.gwt.xml module descriptor

### CORS errors in browser console
- **Cause**: Backend CORS configuration not matching request
- **Solution**: Verify CORS headers in BikeInspectionApp.corsConfigurer()

### Database connection errors
- **Cause**: H2 database driver not found
- **Solution**: Verify h2 dependency in pom.xml is included

## Future Enhancements

1. **Offline Storage**: Add Cordova offline storage plugin for offline inspection submissions
2. **Authentication**: Add Spring Security with JWT tokens
3. **Mobile App**: Convert to responsive design for mobile inspection tools
4. **Reporting**: Add PDF export functionality for inspection reports
5. **Analytics**: Dashboard with inspection statistics and trends
6. **Push Notifications**: Alert inspectors about overdue inspections
7. **User Management**: Multiple inspectors with role-based access
8. **Attachments**: Support for photo uploads during inspection

## License

This project is provided as-is for educational and demonstration purposes.

## Support

For issues or questions, refer to the project documentation or relevant framework documentation:
- [GWT Official Documentation](https://www.gwtproject.org/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [H2 Database Documentation](https://www.h2database.com/)
