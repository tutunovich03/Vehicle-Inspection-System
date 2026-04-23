Vehicle Inspection System

A backend-focused application for managing vehicle inspection workflows, designed to support multiple user roles and complex scheduling logic.

Overview

The system supports four types of users: Administrator, Technician, Secretary, and Client. It handles scheduling, employee management, and inspection tracking, with a focus on clean architecture and maintainable code.

User Roles and Features
  Administrator
    - Manage employees (add and remove workers)
    - Configure shifts (1–3 per day)
    - Define working hours (by day or specific date)
    - Manage worker breaks
    - Review and approve vacation and sick leave requests
    
  All Workers
    - Submit requests for vacation or sick leave
    
  Technician
    - View daily appointments
    - Start and manage inspections
    - Mark inspections as passed or failed
    - Cancel appointments
    
  Secretary
    - Create appointments (e.g. phone requests)
    - View appointment history
    - Handle rescheduling when technicians become unavailable
    
  Client
    - View available time slots based on date and vehicle type
    - Schedule and cancel appointments
    - View upcoming and past appointments
    
Technologies
    Java (Swing)
    MySQL
  
Key Features
  Layered MVC architecture
  Clear separation of concerns (business logic, data access, UI)
  Validation and edge-case handling in scheduling logic
  Role-based system behavior
  
Design Focus
The main goal of this project was to design a flexible and maintainable system. The implementation emphasizes clean separation between layers, careful handling of scheduling constraints, 
and adaptability to different user roles and scenarios.
