# Event Management System

> A secure, role-based online event management platform built with **JavaFX** and **MySQL**. Enable admins to manage users and events, event organizers to create and promote events, and attendees to discover and register for events.

[![Java](https://img.shields.io/badge/Java-11%2B-ED8936?logo=java)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-20-4FC3F7?logo=java)](https://gluonhq.com/products/javafx/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-00758F?logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Security](#security)
- [Usage](#usage)
- [Development](#development)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

### 🔐 Three-Tier User System

**Admin**
- User management (Create, Read, Update, Delete)
- Event approvals and rejections
- System-wide settings management
- View reports and statistics

**Event Organizer**
- Create and manage events
- Design and manage ticket pricing
- Communicate with registered attendees
- Track event statistics and revenue

**Attendee**
- Browse and discover events
- Register for events with one click
- Purchase event tickets securely
- Receive event updates and notifications

### 🛡️ Security Features

- **BCrypt password hashing** (strength 12) - Industry-standard security
- **Session management** with 30-minute timeout
- **SQL injection prevention** via parameterized queries
- **Input validation & sanitization** on all forms
- **Role-based access control (RBAC)** - Fine-grained permissions
- **Secure error handling** without exposing system details

### 🎯 Core Functionality

- Real-time user authentication
- Event lifecycle management (creation → approval → registration)
- Ticket inventory management with availability tracking
- Registration and payment workflow
- Attendee communication system
- Database persistence with MySQL

---

## 🏗️ Architecture

### MVC Pattern with Service Layer

```
┌─────────────────────────────────────┐
│         JavaFX GUI Layer            │
│    (Controllers + FXML)             │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Service Layer                  │
│  (Business Logic & Validation)      │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      DAO Layer                      │
│  (Database Operations)              │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│     MySQL Database                  │
│  (Data Persistence)                 │
└─────────────────────────────────────┘
```

### Database Schema

**6 Core Tables:**
- `users` - User accounts and authentication
- `events` - Event details with approval workflow
- `tickets` - Event ticket pricing and inventory
- `registrations` - Attendee event bookings
- `messages` - Organizer-attendee communication
- `system_settings` - System configuration

**Relationships:**
```
users (1) ←→ (many) events
users (1) ←→ (many) registrations
events (1) ←→ (many) tickets
events (1) ←→ (many) registrations
events (1) ←→ (many) messages
```

---

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **UI Framework** | JavaFX | 20+ |
| **Language** | Java | 11+ |
| **Build Tool** | Maven | 3.8+ |
| **Database** | MySQL | 8.0+ |
| **JDBC Driver** | MySQL Connector | 8.0.33 |
| **Password Hashing** | BCrypt | 0.9.0 |
| **JSON Processing** | GSON | 2.10.1 |
| **Logging** | SLF4J + Logback | 2.0.7 |

---

## 🚀 Quick Start

### Prerequisites

- **Java 11 or higher** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** - [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download MySQL](https://www.mysql.com/downloads/)
- **Git** - [Download Git](https://git-scm.com/)

### Installation (5 minutes)

**1. Clone the repository**
```bash
git clone https://github.com/yourusername/event-management-system.git
cd event-management-system
```

**2. Configure MySQL connection**

Edit `src/main/java/com/eventmanagement/config/DatabaseConfig.java`:

```java
private static final String DB_URL = 
    "jdbc:mysql://your-host:3306/your-database" +
    "?ssl-mode=REQUIRED&user=your-username&password=your-password";
```

Or use environment variables:
```bash
export DB_USER=your-username
export DB_PASSWORD=your-password
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=event_management
```

**3. Build the project**
```bash
mvn clean install
```

**4. Run the application**
```bash
mvn javafx:run
```

### Demo Login Credentials

The application creates demo users on first run:

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Organizer | `organizer1` | `org123456` |
| Attendee | `attendee1` | `att123456` |

**⚠️ Security Note:** Change these credentials in production! Set a new admin password:
```bash
// In InitialDataLoader.java, modify admin user password
admin.setPassword(PasswordUtil.hashPassword("your-secure-password"));
```

---

## 📁 Project Structure

```
event-management-system/
├── src/main/java/com/eventmanagement/
│   ├── app/
│   │   └── EventManagementApp.java          # Main application entry point
│   ├── config/
│   │   └── DatabaseConfig.java              # Database initialization & connection
│   ├── models/
│   │   ├── User.java                        # User entity with roles
│   │   ├── Event.java                       # Event details
│   │   ├── Ticket.java                      # Ticket pricing & inventory
│   │   ├── Registration.java                # Registration records
│   │   └── Message.java                     # Communication
│   ├── dao/
│   │   ├── BaseDAO.java                     # Abstract base class
│   │   ├── UserDAO.java                     # User CRUD operations
│   │   ├── EventDAO.java                    # Event management
│   │   ├── TicketDAO.java                   # Ticket operations
│   │   ├── RegistrationDAO.java             # Registration handling
│   │   └── MessageDAO.java                  # Message operations
│   ├── services/
│   │   ├── AuthenticationService.java       # Login/authentication
│   │   ├── UserService.java                 # User business logic
│   │   ├── EventService.java                # Event business logic
│   │   ├── TicketService.java               # Ticket business logic
│   │   ├── RegistrationService.java         # Registration logic
│   │   └── MessageService.java              # Message logic
│   ├── controllers/
│   │   ├── LoginController.java             # Login screen
│   │   ├── AdminDashboardController.java    # Admin functions
│   │   ├── OrganizerDashboardController.java# Organizer functions
│   │   └── AttendeeDashboardController.java # Attendee functions
│   ├── utils/
│   │   ├── PasswordUtil.java                # BCrypt hashing/verification
│   │   ├── ValidationUtil.java              # Input validation
│   │   ├── EncryptionUtil.java              # Data encryption
│   │   ├── SessionManager.java              # Session management
│   │   └── DateUtil.java                    # Date utilities
│   └── exceptions/
│       ├── AuthenticationException.java
│       ├── AuthorizationException.java
│       ├── ValidationException.java
│       └── DatabaseException.java
├── src/main/resources/
│   ├── fxml/
│   │   ├── login.fxml                       # Login screen layout
│   │   ├── admin-dashboard.fxml             # Admin dashboard
│   │   ├── organizer-dashboard.fxml         # Organizer dashboard
│   │   └── attendee-dashboard.fxml          # Attendee dashboard
│   ├── css/
│   │   └── styles.css                       # UI styling
│   └── application.properties               # Application config
├── pom.xml                                   # Maven dependencies
├── README.md                                 # This file
└── .gitignore
```

---

## ⚙️ Configuration

### pom.xml Dependencies

```xml
<!-- JavaFX -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>20</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>20</version>
</dependency>

<!-- MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- Security -->
<dependency>
    <groupId>at.favre.lib</groupId>
    <artifactId>bcrypt</artifactId>
    <version>0.9.0</version>
</dependency>

<!-- Logging -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.13</version>
</dependency>
```

### Database Setup

```sql
-- Create database
CREATE DATABASE event_management;
USE event_management;

-- Tables are auto-created on first application run
-- But you can manually create them with the schema provided
```

### Environment Variables

```bash
# Database
export DB_HOST=mysql-134b9aef-developapp007-06b2.g.aivencloud.com
export DB_PORT=23811
export DB_NAME=defaultdb
export DB_USER=your-username
export DB_PASSWORD=your-password

# Application
export APP_DEBUG=false
export SESSION_TIMEOUT=1800
```

---

## 🔐 Security

### Password Security

- **Algorithm:** BCrypt with strength 12
- **Requirements:**
  - Minimum 8 characters
  - At least one uppercase letter
  - At least one lowercase letter
  - At least one digit

### Session Management

- **Timeout:** 30 minutes of inactivity
- **Session Storage:** In-memory (can be upgraded to Redis)
- **Validation:** Per-request session verification

### SQL Injection Prevention

All database queries use **parameterized statements**:

```java
// ✅ SAFE - Parameterized query
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, username);

// ❌ UNSAFE - String concatenation
String sql = "SELECT * FROM users WHERE username = '" + username + "'";
```

### Input Validation

```java
// Username: 4-20 chars, alphanumeric, underscore, dash
ValidationUtil.isValidUsername(username);

// Email: Standard email format
ValidationUtil.isValidEmail(email);

// Input sanitization
String safe = ValidationUtil.sanitizeInput(userInput);
```

---

## 💻 Usage

### Admin Dashboard

1. **Login** with admin credentials
2. **User Management:** Add/edit/delete users, assign roles
3. **Event Approvals:** Review pending events, approve/reject
4. **System Settings:** Configure system parameters
5. **View Reports:** Analytics and statistics

### Event Organizer Dashboard

1. **Login** with organizer credentials
2. **Create Events:** Define event details, date, venue, capacity
3. **Manage Tickets:** Set pricing, manage inventory
4. **Send Messages:** Communicate with registered attendees
5. **Track Stats:** Monitor registrations and revenue

### Attendee Dashboard

1. **Login** with attendee credentials
2. **Browse Events:** Search and filter available events
3. **Register:** Register for events of interest
4. **Purchase Tickets:** Select ticket type and quantity
5. **Receive Updates:** Get notifications about registered events

---

## 🔧 Development

### Building from Source

```bash
# Clean build
mvn clean package

# Run tests
mvn test

# Run with Maven
mvn javafx:run

# Generate Javadoc
mvn javadoc:javadoc
```

### IDE Setup

**IntelliJ IDEA:**
1. File → Open → Select pom.xml
2. File → Project Structure → Project SDK (Java 11+)
3. Add JavaFX SDK to Libraries
4. Run → Edit Configurations → VM options: `--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml`

**Eclipse:**
1. Import → Existing Maven Projects
2. Configure JavaFX as user library
3. Run → Run Configurations → Arguments → VM args

**NetBeans:**
1. File → Open Project
2. Right-click project → Properties
3. Add JavaFX SDK
4. Configure main class as `com.eventmanagement.app.EventManagementApp`

### Adding New Features

**1. Create Model** (if needed)
```java
public class NewEntity {
    private int id;
    // properties and getters/setters
}
```

**2. Create DAO**
```java
public class NewEntityDAO {
    public boolean create(NewEntity entity) { /* ... */ }
    public NewEntity getById(int id) { /* ... */ }
    public List<NewEntity> getAll() { /* ... */ }
}
```

**3. Create Service**
```java
public class NewEntityService {
    private NewEntityDAO dao = new NewEntityDAO();
    public NewEntity getById(int id) { return dao.getById(id); }
}
```

**4. Create Controller** and **FXML**

---

## 🐛 Troubleshooting

### Cannot find symbol: HBox, VBox, GridPane

**Fix:** Add import to controller:
```java
import javafx.scene.layout.*;
```

### Cannot connect to MySQL

**Check:**
- MySQL server is running
- Credentials are correct
- Database exists and is accessible
- Network connectivity if using remote host

```bash
# Test connection
mysql -h localhost -u username -p database_name
```

### FXML file not found

**Ensure file structure:**
```
src/main/resources/fxml/
├── login.fxml
├── admin-dashboard.fxml
├── organizer-dashboard.fxml
└── attendee-dashboard.fxml
```

### Event handler not found in FXML

**Ensure controller has method:**
```java
// FXML: onAction="#refreshUsers"
@FXML
private void refreshUsers() {
    // implementation
}
```

### Password verification fails

**Ensure:**
- Not double-hashing password
- Using `PasswordUtil.verifyPassword()` for verification
- Hash stored correctly in database

---

## 📚 Additional Resources

- **Architecture Design:** See `Event_Management_Architecture.pdf`
- **Implementation Guide:** See `Implementation_Deployment_Guide.pdf`
- **Quick Reference:** See `Quick_Reference_Guide.md`
- **API Documentation:** Run `mvn javadoc:javadoc` and open `target/site/apidocs/index.html`

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

**Guidelines:**
- Write clean, documented code
- Follow Java naming conventions
- Add unit tests for new features
- Update README if needed

---

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🙋 Support

For questions, issues, or suggestions:

1. **Check existing documentation** - Quick Reference Guide has solutions for common issues
2. **Review error logs** - Console output provides detailed error information
3. **Inspect database** - Use MySQL CLI to check data integrity
4. **Debug mode** - Enable debug logging in `application.properties`

---

## 📊 Roadmap

### Phase 2: Payment Integration
- [ ] Stripe/PayPal integration
- [ ] Multiple payment methods
- [ ] Transaction history

### Phase 3: Advanced Features
- [ ] Email notifications (JavaMail)
- [ ] PDF ticket generation (iText)
- [ ] Event cancellation & refunds
- [ ] Advanced analytics

### Phase 4: Web & Mobile
- [ ] REST API development
- [ ] Flutter mobile app
- [ ] React web dashboard
- [ ] Real-time updates (WebSocket)

### Phase 5: Enterprise Features
- [ ] PostgreSQL migration
- [ ] Horizontal scaling
- [ ] Redis caching
- [ ] Load balancing
- [ ] Microservices architecture

---

## 🎉 Acknowledgments

- JavaFX framework for modern UI development
- MySQL for reliable data persistence
- BCrypt for secure password hashing
- Maven for dependency management

---

**Made with ❤️ for event management**

Last Updated: December 2025
Version: 1.0.1
