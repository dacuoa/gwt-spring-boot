# Architecture Documentation

## System Overview

The Bike Inspection System is a full-stack web application implementing a modern, distributed architecture with a rich browser-based client and a RESTful backend service.

```
┌─────────────────────────────────────────────────────────────────┐
│                        Browser (Client)                          │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │           GWT Compiled JavaScript Module                   │  │
│  │  ┌──────────────────────────────────────────────────────┐ │  │
│  │  │  MainView                                             │ │  │
│  │  │  ├─ FormView (Page 1)                               │ │  │
│  │  │  │  ├─ TextBox: Inspector Name                      │ │  │
│  │  │  │  ├─ TextBox: Inspection Date                     │ │  │
│  │  │  │  ├─ TextBox: Bike Serial Number                 │ │  │
│  │  │  │  ├─ ListBox: Frame Condition                    │ │  │
│  │  │  │  ├─ ListBox: Brakes                             │ │  │
│  │  │  │  ├─ ListBox: Tyres                              │ │  │
│  │  │  │  ├─ CheckBox: Lights Present                    │ │  │
│  │  │  │  ├─ TextArea: Notes                             │ │  │
│  │  │  │  └─ Button: Submit                              │ │  │
│  │  │  │                                                   │ │  │
│  │  │  └─ SearchView (Page 2)                            │ │  │
│  │  │     ├─ TextBox: Search Term                         │ │  │
│  │  │     ├─ Button: Search                              │ │  │
│  │  │     └─ CellTable: Results                          │ │  │
│  │  │        ├─ ID Column                                │ │  │
│  │  │        ├─ Inspector Column                         │ │  │
│  │  │        ├─ Serial Number Column                     │ │  │
│  │  │        ├─ Date Column                              │ │  │
│  │  │        ├─ Frame Condition Column                   │ │  │
│  │  │        ├─ Brakes Column                            │ │  │
│  │  │        ├─ Tyres Column                             │ │  │
│  │  │        └─ Lights Column                            │ │  │
│  │  └──────────────────────────────────────────────────────┘ │  │
│  │                                                           │  │
│  │  Presenters:                                            │  │
│  │  ├─ FormPresenter (handles form events)                │  │
│  │  └─ SearchPresenter (handles search logic)             │  │
│  │                                                           │  │
│  │  Service:                                               │  │
│  │  └─ InspectionService (HTTP/REST client)               │  │
│  └───────────────────────────────────────────────────────────┘  │
│                           │                                       │
│                RequestBuilder (GWT HTTP)                         │
│                      JSON over HTTP                              │
│                           │                                       │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│              Server (Spring Boot Backend)                        │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  InspectionController                                     │  │
│  │  ├─ POST /api/inspections                               │  │
│  │  ├─ GET /api/inspections/search?q=term                  │  │
│  │  └─ GET /api/inspections/{id}                           │  │
│  └───────────────────────────────────────────────────────────┘  │
│                           │                                       │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  InspectionService (Business Logic)                       │  │
│  │  ├─ saveInspection(DTO)                                 │  │
│  │  ├─ search(searchTerm)                                  │  │
│  │  └─ getById(id)                                         │  │
│  └───────────────────────────────────────────────────────────┘  │
│                           │                                       │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  InspectionRepository (JPA)                              │  │
│  │  ├─ save(Inspection)                                    │  │
│  │  ├─ searchByInspectorNameOrSerialNumber(q)              │  │
│  │  └─ findById(id)                                        │  │
│  └───────────────────────────────────────────────────────────┘  │
│                           │                                       │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  Hibernate (ORM)                                          │  │
│  │  Inspection Entity (JPA Mapped)                          │  │
│  └───────────────────────────────────────────────────────────┘  │
│                           │                                       │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│              H2 Database (In-Memory SQL)                         │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  inspections table                                        │  │
│  │  ├─ id (BIGINT, PK)                                     │  │
│  │  ├─ inspector_name (VARCHAR)                            │  │
│  │  ├─ inspection_date (DATE)                              │  │
│  │  ├─ bike_serial_number (VARCHAR, UNIQUE)                │  │
│  │  ├─ frame_condition (VARCHAR)                           │  │
│  │  ├─ brakes (VARCHAR)                                    │  │
│  │  ├─ tyres (VARCHAR)                                     │  │
│  │  ├─ lights_present (BOOLEAN)                            │  │
│  │  └─ notes (VARCHAR)                                     │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                   │
│  H2 Console: http://localhost:8080/h2-console                   │
└─────────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Model-View-Presenter (MVP) Pattern

The MVP pattern separates the presentation logic from the business logic:

#### FormView + FormPresenter
```
┌─────────────────────────────────────┐
│         FormView                    │
│  - TextBox inspectorNameBox         │
│  - TextBox inspectionDateBox        │
│  - TextBox bikeSerialNumberBox      │
│  - ListBox frameConditionBox        │
│  - ListBox brakesBox                │
│  - ListBox tyresBox                 │
│  - CheckBox lightsPresentBox        │
│  - TextArea notesBox                │
│  - Button submitButton              │
│  - Label statusLabel                │
└─────────────────────────────────────┘
            ▲
            │ implements Display interface
            │
┌─────────────────────────────────────┐
│      FormPresenter                  │
│  - Display display                  │
│  - InspectionService service        │
│  - bindEvents()                     │
│  - handleSubmit()                   │
└─────────────────────────────────────┘
            │
            ▼ calls
    InspectionService.submitInspection()
            │
            ▼
    ┌─────────────────┐
    │  HTTP POST      │
    │  /api/          │
    │  inspections    │
    └─────────────────┘
```

#### SearchView + SearchPresenter
```
┌─────────────────────────────────────┐
│       SearchView                    │
│  - TextBox searchBox                │
│  - Button searchButton              │
│  - CellTable resultsTable           │
│  - Label statusLabel                │
└─────────────────────────────────────┘
            ▲
            │ implements Display interface
            │
┌─────────────────────────────────────┐
│     SearchPresenter                 │
│  - Display display                  │
│  - InspectionService service        │
│  - bindEvents()                     │
│  - handleSearch()                   │
│  - parseAndDisplayResults()         │
└─────────────────────────────────────┘
            │
            ▼ calls
    InspectionService.search()
            │
            ▼
    ┌──────────────────────────┐
    │  HTTP GET                │
    │  /api/inspections/search │
    │  ?q=searchTerm           │
    └──────────────────────────┘
```

### 2. Layered Architecture (Backend)

```
┌──────────────────────────────────┐
│      Presentation Layer           │
│   InspectionController (REST)    │
└──────────────────────────────────┘
              ▼
┌──────────────────────────────────┐
│      Business Logic Layer         │
│   InspectionService              │
│   - saveInspection()             │
│   - search()                     │
│   - getById()                    │
└──────────────────────────────────┘
              ▼
┌──────────────────────────────────┐
│      Persistence Layer            │
│   InspectionRepository (JPA)     │
│   - save()                       │
│   - search()                     │
│   - findById()                   │
└──────────────────────────────────┘
              ▼
┌──────────────────────────────────┐
│      Data Access Layer            │
│   Hibernate ORM                   │
│   ↓                              │
│   H2 Database                    │
└──────────────────────────────────┘
```

### 3. Request/Response Flow

#### Form Submission Flow
```
1. User fills form fields
   └─> FormView widgets store data

2. User clicks Submit button
   └─> Button.ClickHandler fires in FormPresenter

3. FormPresenter.handleSubmit()
   ├─> Validates form data
   ├─> Calls InspectionService.submitInspection()
   └─> Provides callback handlers

4. InspectionService.submitInspection()
   ├─> Creates JSON object from parameters
   ├─> Creates RequestBuilder (POST /api/inspections)
   ├─> Sends JSON payload
   └─> RequestCallback handles response

5. Backend Processing:
   ├─> InspectionController.createInspection()
   ├─> InspectionService.saveInspection()
   ├─> Maps DTO to Entity
   ├─> InspectionRepository.save()
   ├─> Hibernate ORM generates SQL
   ├─> H2 Database persists data
   └─> Returns saved Entity as JSON

6. Frontend Callback:
   ├─> Response received with 200 status
   ├─> Callback.onSuccess() called
   ├─> FormPresenter updates View
   ├─> FormView displays success message
   └─> Form is cleared for next entry
```

#### Search Flow
```
1. User enters search term
   └─> SearchView.searchBox stores text

2. User clicks Search button
   └─> Button.ClickHandler fires in SearchPresenter

3. SearchPresenter.handleSearch()
   ├─> Validates search term
   ├─> Calls InspectionService.search()
   └─> Provides callback handlers

4. InspectionService.search()
   ├─> Constructs URL with query parameter
   ├─> Creates RequestBuilder (GET /api/inspections/search?q=term)
   ├─> Sends HTTP request
   └─> RequestCallback handles response

5. Backend Processing:
   ├─> InspectionController.search()
   ├─> InspectionService.search()
   ├─> InspectionRepository.searchByInspectorNameOrSerialNumber()
   ├─> Hibernate generates SQL with LIKE queries
   ├─> H2 executes query
   ├─> Results mapped to DTOs
   └─> Returns JSON array

6. Frontend Processing:
   ├─> Response received with JSON array
   ├─> SearchPresenter.parseAndDisplayResults()
   ├─> JSON.parse() converts string to objects
   ├─> Creates InspectionRow objects for each result
   ├─> SearchView.displayResults() updates CellTable
   └─> User sees results in table
```

## Class Relationships

### Frontend (GWT Client-side)

```
BikeInspection (EntryPoint)
└─> onModuleLoad()
    └─> MainView
        ├─> FormView
        │   └─> FormPresenter
        │       └─> InspectionService (client)
        │           └─> RequestBuilder
        │               └─> HTTP
        └─> SearchView
            └─> SearchPresenter
                └─> InspectionService (client)
                    └─> RequestBuilder
                        └─> HTTP
```

### Backend (Spring Boot Server-side)

```
BikeInspectionApp (SpringBootApplication)
└─> DispatcherServlet (Spring)
    ├─> InspectionController
    │   └─> InspectionService
    │       └─> InspectionRepository
    │           └─> Hibernate
    │               └─> H2 Database
    └─> BikeInspectionApp.corsConfigurer()
        └─> WebMvcConfigurer
            └─> CORS Configuration
```

## Data Models

### Inspection Entity (JPA)

```java
@Entity
@Table(name = "inspections")
public class Inspection {
    @Id @GeneratedValue
    Long id;
    
    @Column(nullable = false)
    String inspectorName;
    
    @Column(nullable = false)
    LocalDate inspectionDate;
    
    @Column(nullable = false, unique = true)
    String bikeSerialNumber;
    
    @Column(nullable = false)
    String frameCondition; // Good, Fair, Poor
    
    @Column(nullable = false)
    String brakes; // Good, Fair, Poor
    
    @Column(nullable = false)
    String tyres; // Good, Fair, Poor
    
    @Column(nullable = false)
    Boolean lightsPresent;
    
    @Column(length = 1000)
    String notes;
}
```

### InspectionDTO (Data Transfer Object)

```java
public class InspectionDTO {
    Long id;
    String inspectorName;
    LocalDate inspectionDate;
    String bikeSerialNumber;
    String frameCondition;
    String brakes;
    String tyres;
    Boolean lightsPresent;
    String notes;
}
```

### InspectionRow (GWT Client-side)

```java
public static class InspectionRow {
    Long id;
    String inspectorName;
    String inspectionDate;
    String bikeSerialNumber;
    String frameCondition;
    String brakes;
    String tyres;
    Boolean lightsPresent;
    String notes;
}
```

## REST API Contract

### POST /api/inspections

**Request**:
```json
{
  "inspectorName": "John Smith",
  "inspectionDate": "2026-06-27",
  "bikeSerialNumber": "SN-2026-001",
  "frameCondition": "Good",
  "brakes": "Good",
  "tyres": "Fair",
  "lightsPresent": true,
  "notes": "All components functioning properly"
}
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "inspectorName": "John Smith",
  "inspectionDate": "2026-06-27",
  "bikeSerialNumber": "SN-2026-001",
  "frameCondition": "Good",
  "brakes": "Good",
  "tyres": "Fair",
  "lightsPresent": true,
  "notes": "All components functioning properly"
}
```

### GET /api/inspections/search?q=searchTerm

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "inspectorName": "John Smith",
    "inspectionDate": "2026-06-27",
    "bikeSerialNumber": "SN-2026-001",
    "frameCondition": "Good",
    "brakes": "Good",
    "tyres": "Fair",
    "lightsPresent": true,
    "notes": "All components functioning properly"
  },
  {
    "id": 2,
    "inspectorName": "Jane Doe",
    "inspectionDate": "2026-06-26",
    "bikeSerialNumber": "SN-2026-002",
    "frameCondition": "Fair",
    "brakes": "Fair",
    "tyres": "Poor",
    "lightsPresent": false,
    "notes": "Rear light missing, brakes need adjustment"
  }
]
```

### GET /api/inspections/{id}

**Response (200 OK)**:
```json
{
  "id": 1,
  "inspectorName": "John Smith",
  "inspectionDate": "2026-06-27",
  "bikeSerialNumber": "SN-2026-001",
  "frameCondition": "Good",
  "brakes": "Good",
  "tyres": "Fair",
  "lightsPresent": true,
  "notes": "All components functioning properly"
}
```

## Database Schema

### inspections Table

```sql
CREATE TABLE inspections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inspector_name VARCHAR(255) NOT NULL,
    inspection_date DATE NOT NULL,
    bike_serial_number VARCHAR(255) NOT NULL UNIQUE,
    frame_condition VARCHAR(50) NOT NULL,
    brakes VARCHAR(50) NOT NULL,
    tyres VARCHAR(50) NOT NULL,
    lights_present BOOLEAN NOT NULL,
    notes VARCHAR(1000)
);

-- Indexes for performance
CREATE INDEX idx_inspector_name ON inspections(inspector_name);
CREATE INDEX idx_bike_serial_number ON inspections(bike_serial_number);
CREATE INDEX idx_inspection_date ON inspections(inspection_date);
```

## Dependencies and Versions

### Frontend
- **GWT**: 2.10.0
  - gwt-servlet: GWT runtime for server
  - gwt-user: GWT client API
  - gwt-dev: GWT development utilities

### Backend
- **Spring Boot**: 2.7.15
- **Spring Data JPA**: 2.7.15
- **Hibernate**: 5.6.x (included via Spring Data)
- **H2 Database**: Latest (included via Spring Boot)
- **Jackson**: Latest (included via Spring Boot)

### Build
- **Maven**: 3.6.0+
- **Java**: JDK 8+

## Security Considerations

### Current Implementation
- CORS enabled for all origins (development only)
- No authentication/authorization
- No input validation beyond null checks
- Direct database access via ORM

### Production Recommendations
1. **CORS**: Restrict to specific domains
   ```java
   registry.addMapping("/api/**")
           .allowedOrigins("https://yourdomain.com")
           .allowedMethods("GET", "POST")
   ```

2. **Authentication**: Implement JWT or OAuth2
   ```java
   @EnableWebSecurity
   public class SecurityConfig extends WebSecurityConfigurerAdapter
   ```

3. **Input Validation**: Use Bean Validation
   ```java
   @Column(nullable = false)
   @NotBlank(message = "Inspector name is required")
   String inspectorName;
   ```

4. **Rate Limiting**: Implement throttling
   ```java
   @RateLimiter(max = 100, per = "MINUTE")
   ```

5. **HTTPS**: Enable SSL/TLS in production

## Scalability Considerations

### Current Limitations
- Single instance only
- In-memory database (not persistent)
- No caching
- No horizontal scaling

### Scaling Improvements
1. **Database**: Replace H2 with PostgreSQL/MySQL
2. **Persistence**: Add Redis caching layer
3. **Load Balancing**: Use Nginx/HAProxy
4. **Microservices**: Split into separate services
5. **API Gateway**: Add Kong/Spring Cloud Gateway
6. **Message Queue**: Add Kafka/RabbitMQ for async operations

## Performance Optimization

### Frontend
1. GWT compiler optimizations
2. Lazy loading of views
3. Pagination for large result sets
4. Client-side filtering

### Backend
1. Database indexes on frequently queried fields
2. Query optimization
3. Connection pooling
4. Response caching headers
5. Gzip compression

## Testing Strategy

### Unit Testing
- MVP presenter logic
- Service layer business logic
- Repository query methods

### Integration Testing
- End-to-end API testing
- Database operations
- Hibernate mappings

### UI Testing
- GWT view rendering
- User interactions
- Form validation

---

**Document Version**: 1.0
**Last Updated**: 2026-06-27
**Authors**: Bike Inspection System Team
