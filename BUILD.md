# Build and Setup Guide

## Prerequisites

Before building and running this GWT + Spring Boot application, ensure you have the following installed:

### 1. Java Development Kit (JDK)

**IMPORTANT**: You need a full **JDK**, not just the JRE (Java Runtime Environment). The build process requires the Java compiler (`javac`).

**Download Options:**
- Oracle JDK 8 LTS: https://www.oracle.com/java/technologies/
- Eclipse Temurin (AdoptOpenJDK): https://adoptium.net/
- Amazon Corretto: https://aws.amazon.com/corretto/

**Minimum Version**: Java 8
**Recommended**: Java 11 or later

**Verify Installation**:
```bash
javac -version
# Should output something like: javac 1.8.0_xxx
```

### 2. Maven 3.6.0 or Higher

**Download**: https://maven.apache.org/download.cgi

**Verify Installation**:
```bash
mvn -version
# Should output Maven version and JDK path
```

**Important**: Ensure Maven is using the JDK, not just the JRE. Verify with:
```bash
mvn -version
```
The output should show a path to your JDK, not just JRE.

### 3. Git (Optional, for version control)

**Download**: https://git-scm.com/

## Environment Setup

### Windows

#### Step 1: Set JAVA_HOME Environment Variable

1. Open **System Properties**:
   - Press `Win + Pause`
   - Click "Advanced system settings"
   - Click "Environment Variables..."

2. Create a new system variable:
   - Variable name: `JAVA_HOME`
   - Variable value: Path to your JDK installation
     - Example: `C:\Program Files\Java\jdk1.8.0_311`

3. Update PATH:
   - Find "Path" variable in System variables
   - Add: `%JAVA_HOME%\bin` to the beginning of the list

4. Click OK and restart your command prompt

#### Step 2: Verify Setup

```bash
echo %JAVA_HOME%
# Should output your JDK path

javac -version
# Should output javac version

mvn -version
# Should output Maven version and correct JAVA_HOME
```

### macOS / Linux

Add to your `.bash_profile`, `.zprofile`, or `.bashrc`:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
export PATH=$JAVA_HOME/bin:$PATH
```

Then reload:
```bash
source ~/.bash_profile
# or
source ~/.zprofile
```

Verify:
```bash
echo $JAVA_HOME
javac -version
mvn -version
```

## Building the Application

### Quick Build (Skip Tests)

```bash
cd path/to/bike-inspection
mvn clean package -DskipTests
```

**Output**: `target/bike-inspection-1.0-SNAPSHOT.war`

### Full Build (With Tests)

```bash
mvn clean package
```

### Just Compile (No Packaging)

```bash
mvn clean compile
```

### GWT Specific Compilation

```bash
mvn gwt:compile
```

## Running the Application

### Option 1: Direct Spring Boot Execution

```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

### Option 2: Run Pre-built WAR

```bash
mvn clean package -DskipTests
java -jar target/bike-inspection-1.0-SNAPSHOT.war
```

### Option 3: Deploy to Application Server

Copy the generated `target/bike-inspection-1.0-SNAPSHOT.war` to your application server (Tomcat, Jetty, etc.)

## Accessing the Application

Once running, access the following URLs:

### Main Application
```
http://localhost:8080
```

### H2 Database Console
```
http://localhost:8080/h2-console
```

**H2 Console Credentials:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Common Build Issues and Solutions

### Issue: "No compiler is provided in this environment"

**Cause**: Maven is running with JRE instead of JDK

**Solution**:
1. Verify JDK is installed: `javac -version`
2. Set JAVA_HOME correctly to your JDK installation
3. Verify Maven sees the JDK: `mvn -version` (should show JAVA_HOME path)

### Issue: "Cannot find GWT module"

**Cause**: GWT compilation failed

**Solution**:
```bash
mvn clean gwt:compile
mvn clean package -DskipTests
```

### Issue: "Port 8080 already in use"

**Solution**: Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Issue: "Maven downloads not working"

**Solution**: Check your Maven settings at `~/.m2/settings.xml` or configure a repository mirror.

## Development Workflow

### For Development/Testing

```bash
# Terminal 1: Run the application
mvn spring-boot:run

# Terminal 2: In your IDE, make changes and rebuild
# Changes are automatically picked up during development
```

### For Production Build

```bash
# Clean build with optimizations
mvn clean package -DskipTests -Dmaven.compiler.args=-nowarn

# Create an executable JAR
java -jar target/bike-inspection-1.0-SNAPSHOT.war
```

## IDE Setup

### IntelliJ IDEA

1. File → Open → Select `pom.xml`
2. IntelliJ will automatically detect Maven project
3. Wait for Maven dependencies to download
4. Right-click `pom.xml` → "Run Maven" → `clean package`

### Eclipse

1. File → Import → Existing Maven Projects
2. Select project directory
3. Eclipse will download dependencies
4. Right-click project → Run As → Maven build → Goals: `clean package`

### Visual Studio Code

1. Install "Extension Pack for Java" extension
2. Open project folder
3. VS Code will detect Maven project
4. Use Terminal: `mvn clean package`

## Build Artifacts

After a successful build, you'll have:

```
target/
├── bike-inspection-1.0-SNAPSHOT/
│   ├── WEB-INF/
│   │   ├── classes/
│   │   │   ├── com/dacuoa/**/*.class (compiled Java classes)
│   │   │   └── application.properties
│   │   ├── lib/ (all dependencies as JAR files)
│   │   └── web.xml
│   ├── bikeInspection/ (compiled GWT JavaScript module)
│   │   ├── bikeInspection.nocache.js
│   │   ├── [hash]/ (cache files)
│   │   └── [hash].cache.html
│   ├── index.html
│   └── META-INF/
├── bike-inspection-1.0-SNAPSHOT.war (deployable archive)
├── classes/ (compiled classes for IDE)
└── maven-archiver/
```

## Troubleshooting Maven

### Check Maven Configuration

```bash
mvn help:describe -Dplugin=compiler
mvn help:active-profiles
```

### Force Maven to Download Dependencies

```bash
mvn dependency:resolve -U
```

### View Dependency Tree

```bash
mvn dependency:tree
```

### Clean Maven Cache

```bash
# Remove local repository (be careful!)
rm -rf ~/.m2/repository
# Then run Maven again to re-download dependencies
mvn clean package -DskipTests
```

## Performance Tips

1. **Skip Tests During Development**:
   ```bash
   mvn clean package -DskipTests
   ```

2. **Use Maven offline mode** (after initial build):
   ```bash
   mvn clean package -o
   ```

3. **Parallel build**:
   ```bash
   mvn clean package -T 1C  # Uses 1 thread per core
   ```

## Docker Build (Optional)

Create a `Dockerfile`:

```dockerfile
FROM maven:3.8-jdk-8 as builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=builder /build/target/bike-inspection-1.0-SNAPSHOT.war app.war
CMD ["java", "-jar", "app.war"]
```

Build and run:
```bash
docker build -t bike-inspection .
docker run -p 8080:8080 bike-inspection
```

## Next Steps

1. **Build the application**: Follow the "Quick Build" section
2. **Run the application**: Use Option 1 or 2 under "Running the Application"
3. **Test the application**: Navigate to http://localhost:8080
4. **Inspect the database**: Visit http://localhost:8080/h2-console

## Additional Resources

- [Maven Documentation](https://maven.apache.org/guides/)
- [GWT Getting Started](https://www.gwtproject.org/overview.html)
- [Spring Boot Getting Started](https://spring.io/guides/gs/spring-boot/)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)

---

If you encounter issues not covered here, please check:
1. The README.md for architecture information
2. The ARCHITECTURE.md for detailed design information
3. Maven and Java official documentation
