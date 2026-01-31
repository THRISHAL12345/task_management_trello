# Task Management Backend (Trello-Like)

A robust and feature-rich task management REST API built with Spring Boot, designed to provide Trello-like functionality for managing boards, lists, and tasks with user authentication and analytics.

## 📋 Features

### Core Functionality
- **User Authentication & Authorization**
  - JWT-based authentication
  - User registration and login
  - Role-based access control (USER, ADMIN)
  - Secure password encryption

- **Board Management**
  - Create, read, update, and delete boards
  - Pagination support for board listings
  - User-specific board access

- **Task List Management**
  - Create multiple lists within boards
  - Update and delete lists
  - Order and organize lists

- **Task Management**
  - Create, update, and delete tasks
  - Task priorities (LOW, MEDIUM, HIGH)
  - Task statuses (TODO, IN_PROGRESS, DONE)
  - Due date tracking
  - File attachment support (up to 10MB)
  - Task filtering and pagination

- **Analytics & Reporting**
  - Task distribution by status
  - Tasks per user analytics
  - Overall task statistics

### Additional Features
- **Email Notifications** - Spring Mail integration
- **Rate Limiting** - API rate limiting with Bucket4j
- **API Documentation** - Interactive Swagger/OpenAPI UI
- **File Upload** - Attachment support for tasks
- **MongoDB Integration** - NoSQL database for flexible schema

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.10
- **Language**: Java 17
- **Database**: MongoDB 7
- **Security**: Spring Security + JWT
- **Documentation**: SpringDoc OpenAPI 3
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Additional Libraries**:
  - Lombok - Boilerplate code reduction
  - Bucket4j - Rate limiting
  - JJWT - JSON Web Token implementation
  - Spring Mail - Email functionality

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use included Maven wrapper)
- **MongoDB 7** (or use Docker Compose)
- **Docker & Docker Compose** (optional, for containerized deployment)

## 🚀 Installation & Setup

### Option 1: Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/THRISHAL12345/task_management_trello.git
   cd taskmanagement
   ```

2. **Start MongoDB**
   
   If you have MongoDB installed locally:
   ```bash
   mongod
   ```
   
   Or use Docker:
   ```bash
   docker run -d -p 27017:27017 --name mongodb mongo:7
   ```

3. **Configure application** (Optional)
   
   Edit `src/main/resources/application.yaml` or set environment variables:
   ```bash
   export SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/taskmanager
   export JWT_SECRET=your-secret-key-at-least-32-characters
   export MAIL_USERNAME=your-email@gmail.com
   export MAIL_PASSWORD=your-app-password
   ```

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   
   On Windows:
   ```cmd
   mvnw.cmd clean install
   ```

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   On Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

6. **Access the application**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/v3/api-docs

### Option 2: Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/THRISHAL12345/task_management_trello.git
   cd taskmanagement
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

4. **Stop all services**
   ```bash
   docker-compose down
   ```

5. **Stop and remove volumes**
   ```bash
   docker-compose down -v
   ```

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATA_MONGODB_URI` | MongoDB connection URI | `mongodb://localhost:27017/taskmanager` |
| `JWT_SECRET` | Secret key for JWT token generation (min 32 chars) | Auto-generated |
| `JWT_EXPIRATION` | JWT token expiration time in milliseconds | `3600000` (1 hour) |
| `MAIL_USERNAME` | SMTP email username | `dummy@gmail.com` |
| `MAIL_PASSWORD` | SMTP email password | `dummy-password` |
| `FILE_UPLOAD_DIR` | Directory for file uploads | `uploads` |

### Application Properties

Key configurations in `application.yaml`:
- **Server Port**: 8080
- **Max File Size**: 10MB
- **Max Request Size**: 10MB
- **Mail Server**: smtp.gmail.com:587

## 📚 API Endpoints

### Authentication (`/api/auth`)
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Boards (`/api/boards`)
- `POST /api/boards` - Create a new board
- `GET /api/boards` - Get all boards (paginated)
- `GET /api/boards/{id}` - Get board by ID
- `PUT /api/boards/{id}` - Update board
- `DELETE /api/boards/{id}` - Delete board

### Task Lists (`/api/boards/{boardId}/lists`)
- `POST /api/boards/{boardId}/lists` - Create a new list
- `GET /api/boards/{boardId}/lists` - Get all lists in a board
- `PUT /api/boards/{boardId}/lists/{listId}` - Update list
- `DELETE /api/boards/{boardId}/lists/{listId}` - Delete list

### Tasks (`/api/tasks`)
- `POST /api/tasks` - Create a new task
- `GET /api/tasks` - Get tasks with filters (paginated)
- `PUT /api/tasks/{taskId}` - Update task
- `DELETE /api/tasks/{taskId}` - Delete task
- `POST /api/tasks/{taskId}/attachments` - Upload attachment

### Analytics (`/api/analytics`)
- `GET /api/analytics/tasks-by-status` - Get tasks grouped by status
- `GET /api/analytics/tasks-per-user` - Get tasks per user
- `GET /api/analytics/task-analytics` - Get overall task statistics

### API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🏗️ Project Structure

```
taskmanagement/
├── src/
│   ├── main/
│   │   ├── java/com/taskmanager/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   ├── PasswordConfig.java
│   │   │   │   ├── RateLimitFilter.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── AnalyticsController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── BoardController.java
│   │   │   │   ├── TaskController.java
│   │   │   │   └── TaskListController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── exception/           # Custom exceptions & handlers
│   │   │   ├── model/               # Domain models
│   │   │   │   ├── Board.java
│   │   │   │   ├── Task.java
│   │   │   │   ├── TaskList.java
│   │   │   │   └── User.java
│   │   │   ├── repository/          # MongoDB repositories
│   │   │   ├── security/            # JWT & security components
│   │   │   ├── service/             # Business logic interfaces
│   │   │   │   └── impl/            # Service implementations
│   │   │   └── util/                # Utility classes
│   │   └── resources/
│   │       ├── application.yaml     # Application configuration
│   │       ├── static/              # Static resources
│   │       └── templates/           # Email templates
│   └── test/                        # Unit and integration tests
├── docker-compose.yml               # Docker Compose configuration
├── Dockerfile                       # Docker image definition
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Rate Limiting**: API request rate limiting to prevent abuse
- **Input Validation**: Bean validation on all requests
- **CORS Configuration**: Configurable cross-origin resource sharing
- **Exception Handling**: Global exception handler for consistent error responses

## 🧪 Testing

Run tests with Maven:
```bash
./mvnw test
```

On Windows:
```cmd
mvnw.cmd test
```

## 📦 Building for Production

Create a production-ready JAR:
```bash
./mvnw clean package -DskipTests
```

The JAR file will be created at `target/taskmanagement-0.0.1-SNAPSHOT.jar`

Run the JAR:
```bash
java -jar target/taskmanagement-0.0.1-SNAPSHOT.jar
```

## 🐳 Docker Deployment

Build Docker image:
```bash
docker build -t taskmanagement-backend .
```

Run container:
```bash
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host.docker.internal:27017/taskmanager \
  taskmanagement-backend
```

## 📝 Example Usage

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "SecurePass123"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123"
  }'
```

### 3. Create a Board
```bash
curl -X POST http://localhost:8080/api/boards \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Project",
    "description": "Project management board"
  }'
```

### 4. Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Implement feature",
    "description": "Add new functionality",
    "priority": "HIGH",
    "status": "TODO",
    "listId": "list-id-here"
  }'
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

- **GitHub**: [@THRISHAL12345](https://github.com/THRISHAL12345)
- **Repository**: [task_management_trello](https://github.com/THRISHAL12345/task_management_trello)

## 🐛 Issues & Support

If you encounter any issues or have questions, please [open an issue](https://github.com/THRISHAL12345/task_management_trello/issues) on GitHub.

## 📈 Future Enhancements

- [ ] WebSocket support for real-time updates
- [ ] Task comments and activity history
- [ ] Labels and tags for tasks
- [ ] Task assignments and notifications
- [ ] Advanced search and filtering
- [ ] Export boards and tasks
- [ ] Integration with external services
- [ ] Mobile app support

---

**Built with ❤️ using Spring Boot**
