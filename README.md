# Gym Management System: Object Oriented Programming Final Project

# I. Project Overview
<p align="justify"> 
  The java system Gym Management System is designed to smoothen the operation of the gym with functionalities that both users and admins can avail. Members can sign into the system, track attendance, log exercises, and manage membership status and view their payment history. This also allows members to interact with the equipment at the gym, log exercise entries, and maintain activity logs. The system provides extra privileges for admins, including approval of payments, updating membership status, managing gym equipment, and deleting or updating user details. Admins can view and manage users' profiles, equipment, and user data easily. The project contains a data persistence layer where all user, equipment, attendance, and payment data will be stored in files, thus all data will be saved between sessions. Designed on object-oriented principles, the present Java-based project ensures scalability, modularity, and maintainability for any eventual modification or enhancement.
<p align="justify"> 
  I have read about a wide range of sources on Gym Management Systems, and they are vital in streamlining gym operations as well as in managing members and employees. The systems standardize the task of managing such activities as class timetabling, processing payment, and managing membership. All these can reduce mistakes and make work more efficient. They will enhance staff members' and customers' ability to communicate better in terms of managing an organized and customer-focused environment for a gym.
Regarding scalability and flexibility, GMS perfectly fits the gym concerning expansion. Many systems can be customized to the business needs, thus providing real-time data for the analysis of performance, thereby enabling the gym owner to make decisions regarding growth in the business. For more insight, you may refer to the sources that describe the usability of GMS in bettering the operations of the gyms and efficiency.

# II. Four Principles of Object Oriented Programming (OOP)

## Encapsulation
<p align="justify"> 
This principle is demonstrated when classes like User, Membership, and Payment are used to group data and functionality related to specific entities. For example, a User class may include attributes like name, membership_type, and methods like login() or update_profile(), encapsulating the data and operations associated with users. By keeping related data and behaviors together in a class, the system hides the internal workings from outside interference and provides a clear, user-friendly interface.

## Inheritance
<p align="justify"> 
Inheritance allows one class to inherit the properties and methods of another, promoting code reuse and reducing redundancy. For instance, a Trainer class may inherit from a general User class, gaining access to common attributes such as name and email. This allows the GMS to distinguish between users and trainers while minimizing the need to duplicate code for shared functionality.

## Polymorphism
<p align="justify"> 
Polymorphism enables methods to be implemented in different ways depending on the object type. In the GMS, polymorphism might be used to allow a method like generate_report() to work differently for Trainer, User, and Admin objects. Each class could override the method to provide specific functionality relevant to that class, such as generating membership statistics for a user or payment summaries for an admin, all while keeping a consistent interface.

## Abstraction
<p align="justify"> 
Abstraction simplifies complex systems by exposing only essential details and hiding unnecessary complexity. In the GMS, an abstract Payment class could define the basic payment methods, such as process_payment() and view_payment_status(), while concrete subclasses (like CreditCardPayment and CashPayment) provide specific implementations. This allows users to interact with payment-related functions without needing to know the underlying details of each payment method, making the system easier to use and maintain.

# III. SDG Integration

### SDG 3: Good Health and Well-being
<p align="justify"> 
The system directly supports health and well-being through the promotion of physical fitness. It assists the user in tracking progress, schedules, memberships, and payments to the gym, hence encouraging healthy lifestyle through regular exercise. This further facilitates the management of gyms and the training of the staff hence making it easy for the public to access fitness services hence contributing to improved public health.

### SDG 8: Decent Work and Economic Growth.
<p align="justify"> 
It also will be in a position to support jobs and economic development through this means. The optimality of its management in gyms creates room for a more effective business setup. It allows room where the owner or workers will have much time at work and much less on paper. As such, productivity will enhance as will sustainable fitness industry practice.

### SDG 9: Industry, Innovation, and Infrastructure
<p align="justify"> 
The system helps industry innovation through the integrat ion of modern software solutions to traditional gym management, which improves the operation and satisfaction of the customer. It simplifies the administration processes, improves better data management, and eventually streamlines infrastructure for any kind of gym operation to scale according to market demand.

# IV. Instruction for Running the Program

1. Download the zip file that is attached to this repository.
2. Make sure that your desktop or laptop has Java installed.
3. Unzip the file that you have downloaded.
4. Run it.


