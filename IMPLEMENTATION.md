# SimpleAdServiceDemo - Implementation Documentation

## Overview
SimpleAdServiceDemo is a Spring-based REST web service that allows partners to create and manage ad campaigns. The application demonstrates a lightweight ad campaign management system with in-memory data storage, built using Spring MVC and deployed on Jetty server.

## Architecture

### Technology Stack
- **Framework**: Spring Framework 4.3.x (MVC, Web, Transaction)
- **Build Tool**: Maven 3.x
- **Server**: Jetty 9.2.5
- **Serialization**: Jackson JSON (2.8.3)
- **Language**: Java 1.8
- **Data Storage**: In-memory HashMap (no database)

### Dependencies
- `spring-webmvc:4.3.2.RELEASE` - Web MVC framework
- `spring-tx:4.3.3.RELEASE` - Transaction management
- `jackson-databind:2.8.3` - JSON serialization/deserialization
- `javax.servlet-api:3.1.0` - Servlet API
- `slf4j-api:1.7.24` - Logging
- **Test Dependencies**: JUnit 4.11, Mockito 1.9.5, Spring Test 3.2.3, json-path 0.8.1

---

## Project Structure

```
SimpleAdServiceDemo/
├── src/
│   ├── main/java/com/tharun/rest/ad/
│   │   ├── config/               # Spring configuration classes
│   │   ├── controller/           # REST endpoints
│   │   ├── service/              # Business logic interface and implementation
│   │   ├── model/                # Data models
│   │   ├── error/                # Error handling
│   │   └── initializer/          # Application initializer
│   └── test/java/com/tharun/rest/ad/  # Unit and integration tests
├── pom.xml                       # Maven configuration
└── README.md                     # User guide
```

---

## Core Components

### 1. **Model Layer** - `AdCampaign`

**File**: `src/main/java/com/tharun/rest/ad/model/AdCampaign.java`

Represents an ad campaign with the following properties:
- `partnerId` (String) - Unique identifier for the partner/advertiser
- `duration` (int) - Campaign duration in seconds from creation
- `adContent` (String) - The actual ad content to display
- `timestamp` (Timestamp) - Auto-generated creation timestamp in milliseconds

**Key Features**:
- Immutable after creation (no setters)
- Automatically captures creation time upon instantiation
- Two constructors: default and parameterized

### 2. **Service Layer**

#### Interface - `AdCampaignService`
**File**: `src/main/java/com/tharun/rest/ad/service/AdCampaignService.java`

Defines four core operations:
```java
void saveAdCampaign(AdCampaign adCampaign);        // Create/overwrite campaign
AdCampaign fidByPartnerId(String partnerId);       // Retrieve active campaign
void updateAdCampaign(AdCampaign adCampaign);      // Update existing campaign
void deleteAdCampaign(String partnerId);           // Delete campaign
```

#### Implementation - `AdCampaignServiceImpl`
**File**: `src/main/java/com/tharun/rest/ad/service/impl/AdCampaignServiceImpl.java`

**Annotation**: `@Service("AdCampaignService")` with `@Transactional`

**Storage**: In-memory HashMap with initial capacity of 100

**Key Methods**:

1. **saveAdCampaign(AdCampaign)**
   - Stores campaign in HashMap using `partnerId` as key
   - Supports "one active campaign per partner" by overwriting existing campaigns
   - Null-safe implementation

2. **fidByPartnerId(String)** ⚠️ *Note: Method name has typo - should be `findByPartnerId`*
   - Validates if campaign exists for partner
   - **Checks campaign expiration**: Compares `timestamp + duration` with current time
   - Returns campaign only if still active (not expired)
   - Throws `NoSuchElementException` if campaign doesn't exist or has expired

3. **updateAdCampaign(AdCampaign)**
   - Updates existing campaign
   - Throws `NoSuchElementException` if campaign doesn't exist

4. **deleteAdCampaign(String)**
   - Removes campaign by partnerId

**Expiration Logic**:
```
Expiration Time = Campaign Creation Timestamp + Duration (in milliseconds)
Is Active = Expiration Time > Current Time
```

### 3. **Controller Layer** - `AddServiceDemo`

**File**: `src/main/java/com/tharun/rest/ad/controller/AddServiceDemo.java`

**Annotation**: `@RestController`

#### Endpoints:

**1. Create Ad Campaign - POST /ad**
```
Method: POST
Path: /ad
Input: JSON AdCampaign object
{
  "partnerId": "unique_partner_id",
  "duration": 3600,
  "adContent": "Your ad content here"
}
Output: AdCampaign (same object that was stored)
```

**Implementation**:
- Calls `adCampaignService.saveAdCampaign(adCampaign)`
- Returns the saved campaign
- One campaign per partner enforcement: overwriting existing campaigns

**2. Get Ad Campaign - GET /ad/{partnerId}**
```
Method: GET
Path: /ad/{partnerId}
Parameter: partnerId (path variable)
Output: Active AdCampaign for the partner (JSON)
Error: Exception if campaign doesn't exist or has expired
```

**Implementation**:
- Calls `adCampaignService.fidByPartnerId(partnerId)`
- Returns campaign if active (not expired)
- Propagates `NoSuchElementException` if not found/expired

### 4. **Error Handling**

#### Global Exception Handler - `ControllerAdvisor`
**File**: `src/main/java/com/tharun/rest/ad/controller/ControllerAdvisor.java`

**Annotation**: `@ControllerAdvice`

**Handler Method**: `@ExceptionHandler`
- Catches all exceptions across controllers
- Logs errors with request method and URI
- Returns HTTP 417 (Expectation Failed) with exception message
- Centralized error response formatting

#### Client Error Handler - `RestClientErrorHandler`
**File**: `src/main/java/com/tharun/rest/ad/error/RestClientErrorHandler.java`

**Implementation**: `ResponseErrorHandler`
- Handles HTTP client errors for RestTemplate calls
- Checks for non-OK responses
- Specifically handles HTTP 403 (Forbidden) status
- Used in test client configuration

### 5. **Configuration Classes**

#### MainConfig
**File**: `src/main/java/com/tharun/rest/ad/config/MainConfig.java`

- Base application configuration
- Component scan: `com.tharun.rest.ad` package
- Excludes controllers and @ControllerAdvice beans
- Sets up service layer and business logic beans

#### WebMvcConfig
**File**: `src/main/java/com/tharun/rest/ad/config/WebMvcConfig.java`

- Web MVC specific configuration
- Extends `WebMvcConfigurerAdapter`
- Annotation: `@EnableWebMvc`
- Component scan: `com.tharun.rest.ad.controller` package
- **Custom HttpMessageConverter**: Configures `MappingJackson2HttpMessageConverter` for JSON serialization

#### ClientConfig
**File**: `src/main/java/com/tharun/rest/ad/config/ClientConfig.java`

- Test client configuration
- Component scan: `com.tharun.rest.ad.error` package
- Provides RestTemplate bean with custom error handler
- Used for integration testing

### 6. **Application Initializer**

**File**: `src/main/java/com/tharun/rest/ad/initializer/SpringWebMvcInitializer.java`

- Extends `AbstractAnnotationConfigDispatcherServletInitializer`
- Java-based configuration (no web.xml needed)
- Root context configs: `MainConfig`, `ClientConfig`
- Servlet context configs: `WebMvcConfig`
- Servlet mappings: Maps all requests (`/`) to DispatcherServlet

---

## Testing

### Test Classes

#### 1. PlainRestApplicationTest
**File**: `src/test/java/com/tharun/rest/ad/PlainRestApplicationTest.java`

**Purpose**: Manual integration testing without Spring context

**Methods**:
- `getAdCampign()` - GET request to retrieve campaign (note: typo in method name)
- `createAdCampaign()` - POST request to create campaign
- `main()` - Executes create then retrieve

**Note**: This is a manual test class; not executed by Maven test plugin

#### 2. SpringRestTestClient
**File**: `src/test/java/com/tharun/rest/ad/SpringRestTestClient.java`

**Purpose**: Integration testing with Spring context

**Annotations**:
- `@RunWith(SpringJUnit4ClassRunner.class)` - Spring test runner
- `@ContextConfiguration` - Loads MainConfig, WebMvcConfig, ClientConfig
- `@WebAppConfiguration` - Enables web application testing

**Test Methods**:

1. **findAdCampaignbyIdTest()** - Positive case
   - Creates campaign with partnerId "12"
   - Retrieves and validates partnerId matches
   - Expects HTTP 200 with AdCampaign object

2. **findAdCampaignbyIdTest_FailCase()** - Negative case
   - Attempts to find campaign
   - Asserts response body is NOT an instance of AdCampaign
   - Logic issue: Test may not work as intended without creating campaign first

3. **createAdCampignTest()** - Create and retrieve
   - Creates campaign with partnerId "12" and 2345 second duration
   - Verifies POST returns a location URI
   - Retrieves campaign and validates partnerId matches

**Setup**:
- `@Before` method configures HttpHeaders with `Accept: application/json`

---

## Data Flow

### Create Campaign Flow
```
POST /ad
    ↓
AddServiceDemo.createAdCampaign()
    ↓
AdCampaignService.saveAdCampaign()
    ↓
HashMap.put(partnerId, adCampaign)
    ↓
Return AdCampaign (JSON)
```

### Retrieve Campaign Flow
```
GET /ad/{partnerId}
    ↓
AddServiceDemo.getAdCampaign()
    ↓
AdCampaignService.fidByPartnerId()
    ↓
Check if exists in HashMap
Check if expired (timestamp + duration > now)
    ↓
If valid: Return AdCampaign (JSON)
If expired/not found: Throw NoSuchElementException
    ↓
Exception caught by ControllerAdvisor
Response: HTTP 417 + error message
```

---

## Campaign Expiration Logic

The system implements time-based campaign expiration:

**Storage**: `Timestamp` (milliseconds since epoch) at creation

**Expiration Check**:
```
expireTime = campaign.timestamp (ms) + campaign.duration (seconds) × 1000
isActive = expireTime > System.currentTimeMillis()
```

**Example**:
- Created at: 1493297600000 ms (2017-04-27 12:00:00 UTC)
- Duration: 3600 seconds (1 hour)
- Expires at: 1493301200000 ms (2017-04-27 13:00:00 UTC)
- After expiration, GET requests return error

---

## Functional Requirements Fulfillment

### ✅ Requirement 1: Create Ad Campaign via POST /ad

**Status**: IMPLEMENTED

- Accepts JSON with `partnerId`, `duration`, `adContent`
- Enforces "one active campaign per partner" (via HashMap overwrite)
- Returns campaign data in response
- Automatic error handling for invalid requests

### ✅ Requirement 2: Fetch Ad Campaign via GET /ad/{partnerId}

**Status**: IMPLEMENTED

- Accepts partner ID as path parameter
- Returns active campaign as JSON
- Returns error if campaign expired or doesn't exist
- Time-based expiration: `createdTime + duration`

---

## Known Issues & Observations

### 1. **Method Name Typo**
- `AdCampaignService.fidByPartnerId()` should be `findByPartnerId()`
- Also in Controller: `AddServiceDemo.getAdCampaign()` (typo in test method names)

### 2. **Limited Error Information**
- Generic HTTP 417 responses without structured error format
- Would benefit from custom error response DTOs

### 3. **Immutable Campaign Data**
- No setters on AdCampaign; cannot modify content after creation
- Only way to "update" is overwrite with new campaign

### 4. **In-Memory Storage Limitations**
- Data lost on server restart
- Not suitable for production
- No thread-safety considerations documented

### 5. **Test Coverage Issues**
- `findAdCampaignbyIdTest_FailCase()` logic is questionable
- Manual test class not integrated with Maven build
- Missing edge case tests (null inputs, negative durations, etc.)

### 6. **Deprecated Code**
- `WebMvcConfigurerAdapter` is deprecated in Spring 5+
- Should implement `WebMvcConfigurer` interface for future compatibility

---

## Deployment & Running

### Prerequisites
- Java 1.8+
- Maven 3.x

### Build
```bash
mvn clean install
```
Generates `SimpleAdService.war` file

### Run on Jetty
```bash
mvn jetty:run
```

**Default Settings**:
- Port: 8080
- Context: `/SimpleAdService`
- Base URL: `http://localhost:8080/SimpleAdService`

### Access the Service
- **Create Campaign**: `POST http://localhost:8080/SimpleAdService/ad`
- **Get Campaign**: `GET http://localhost:8080/SimpleAdService/ad/{partnerId}`

---

## Summary

The SimpleAdServiceDemo is a well-structured Spring MVC application that successfully implements a basic ad campaign management service. It demonstrates solid architectural practices with clear separation of concerns (model, service, controller), centralized configuration, and global error handling. While the in-memory storage and basic test coverage limit production readiness, the implementation effectively demonstrates REST API design principles and Spring framework proficiency.

The application correctly enforces the "one active campaign per partner" constraint and implements time-based campaign expiration as specified in the requirements.

