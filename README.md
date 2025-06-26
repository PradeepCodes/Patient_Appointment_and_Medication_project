🔧 Prerequisites

Java 17+

Maven 3.5.0+

MySQL (for local) / PostgreSQL (for deployment)

IDE: IntelliJ IDEA (recommended)

Optional: Postman for testing REST APIs

⚙️ 1. Project Setup & Configuration

▶️ Clone the repository

git clone https://github.com/PradeepCodes/Patient_Appointment_and_Medication_projec.git
cd Patient_Managment

▶️ Database Configuration

Local MySQL Configuration (src/main/resources/application.properties):

# MySQL Connection

spring.datasource.url=jdbc:mysql://localhost:3306/patient_appointment_db
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

PostgreSQL Configuration (For Render Deployment):

#spring.datasource.url=jdbc:postgresql://dpg-d13v49ggjchc73fjpim0-a.oregon-postgres.render.com:5432/employee_management_am10?sslmode=require
#spring.datasource.username=pradeep
#spring.datasource.password=jDo54X1Qm2YmiOZSvgs2nvtzZTKGcVYh

spring.jpa.hibernate.ddl-auto=update

▶️ Create MySQL Database (Local)
CREATE DATABASE patient_management;

▶️ 2. Running the Application

mvn clean install
mvn spring-boot:run

Thymeleaf UI: http://localhost:8080/

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs


🔗 3. API Endpoints

Authentication

Method

Endpoint

Description

GET

/register

Show patient registration page

POST

/register

Register a new patient

GET

/login

Login form

GET

/default

Role-based redirection

Appointments

Method

Endpoint

Description

GET

/patient/appointments/register

Show appointment booking page

POST

/appointments

Book a new appointment

Medications (Doctor Only)

Method

Endpoint

Description

POST

/doctor/medications/add

Add new medication

GET

/edit/{id}

Edit existing medication

GET

/delete/{id}

Delete a medication

Admin

Method

Endpoint

Description

GET

/admin/dashboard

Admin overview

POST

/admin/doctors/add

Add new doctor

🔄 4. Sample Requests & Responses

POST /register (Patient)

POST /register
Content-Type: application/x-www-form-urlencoded

name=Raj&email=raj@example.com&password=secret&contact=9876543210&medicalHistory=None

POST /doctor/medications/add

POST /doctor/medications/add
Content-Type: application/x-www-form-urlencoded

patientName=Raj&medication=Paracetamol&dosage=500mg&prescribedBy=Dr. Arjun

✅ 5. Data Validation Rules

Field

Validation

name

@NotBlank, length 2–50

email

@Email, unique

password

Min 6 characters

contact

10-digit number

medicalHistory

Optional

Validation Error Format (400 Bad Request)

{
  "timestamp": "2025-06-27T15:00:00",
  "status": 400,
  "errors": [
    "email: must be a well-formed email address",
    "password: must not be blank"
  ]
}

💻 6. Swagger Documentation

Add to pom.xml

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.7.0</version>
</dependency>

Access:

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

📩 7. Postman Collection

Export OpenAPI from Swagger UI: "Export" → "Download JSON"

In Postman: File → Import → Upload the downloaded JSON

You can also manually test:

GET: http://localhost:8080/api/patients

POST: http://localhost:8080/api/patients

Headers: Content-Type: application/json

🚀 8. Running Tests

mvn test

Service tests: use Mockito

Repository tests: @DataJpaTest

Controller tests: @SpringBootTest + @AutoConfigureMockMvc

REST API tests: @WebMvcTest

🔧 Deployment URLs

Resource

URL

DB (Render)

https://dashboard.render.com/d/dpg-d13v49ggjchc73fjpim0-a

Frontend

https://patient-appointment-and-medication.onrender.com/

Swagger

https://patient-appointment-and-medication.onrender.com/swagger-ui/index.html

