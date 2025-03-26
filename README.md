## Student Attendance Management STD23014

**Overview**

This Spring Boot application provides a RESTful API for managing student attendance, teachers, courses, and administrative tasks. The API is designed to be easy to use and integrates seamlessly with your existing applications.

**Getting Started**

1. **Clone the repository:** `git clone <repository_url>`
2. **openAPI specification:** The ***specification*** file is located in `AttendanceRecordApp\Controller\openAPI Specification`
3. **The database** :
   - **Database name** : attendancerecorddb .
   - The files to create the database, views and the mock data are in `AttendanceRecordApp\Repository\SQL DUMP`
   -  Execute them in this order : **tableCreation.sql**>**views.sql**>**Mock_Data.sql** 

**Features**

* **Student Management:**
  * Create, read, update, and delete student records.
  * Search for students by name, student reference (STD), **cor_status** , or group name.
* **Teacher Management:**
  * Create, read, update, and delete teacher records.
  * Search for teacher by name
* **Course Management:**
  * Create, read, update, and delete course records.
  * Search for course by name
* **Attendance Management:**
  * Record student attendance for courses.
  * Filter attendance records by course, student, date, or status.
  * Search for attendance with one to three query parameters
  * Generate attendance summaries for individual students.
* **Admin Management:**
  * Manage administrative users for the application.
  * Search for an admin by name

**Endpoints**

The API exposes several endpoints for interacting with the application's data. Here are some examples:

* **Students:**
    * `GET /students`: Retrieve a list of all students.
    * `POST /students`: Create a new student.
    * `PUT /students/{std}`: Update a student's information.
    * `DELETE /students/{std}`: Delete a student.
* **Teachers:**
    * `GET /teachers`: Retrieve a list of all teachers.
    * `POST /teachers`: Create a new teacher.
    * `PUT /teachers/{teacherId}`: Update a teacher's information.
    * `DELETE /teachers/{teacherId}`: Delete a teacher.
* **Courses:**
    * `GET /courses`: Retrieve a list of all courses.
    * `POST /courses`: Create a new course.
    * `PUT /courses/{courseId}`: Update a course's information.
    * `DELETE /courses/{courseId}`: Delete a course.
* **Attendance:**
    * `GET /attendance`: Retrieve a list of attendance records.
    * `POST /attendance`: Create a new attendance record.
    * `PUT /attendance/{attendanceId}`: Update an attendance record.
    * `DELETE /attendance/{attendanceId}`: Delete an attendance record.

**Note:**
- **Query Parameters:** Refer to the OpenAPI specification for available query parameters for filtering and searching.
- **COR Status:** The `cor_status` is automatically created when a student has three unjustified absences.

# MCD : 
![MCD STD23014](https://github.com/user-attachments/assets/0fd3cb7b-66d6-4da8-9b86-c8e1588e6eaa)
