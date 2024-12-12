import java.io.*;
import java.util.*;

abstract class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract void displayInfo();
}

class Member extends User {
    private String membershipStatus;
    private String membershipType;
    private List<String> attendanceLog;
    private List<String> paymentHistory;
    private List<String> exerciseLog;

    public Member(String username, String password, String membershipType) {
        super(username, password);
        this.membershipType = membershipType;
        this.membershipStatus = "Active";
        this.attendanceLog = new ArrayList<>();
        this.paymentHistory = new ArrayList<>();
        this.exerciseLog = new ArrayList<>();
    }

    public String getPaymentStatus() {
        if (paymentHistory.isEmpty()) {
            return "No payment made";
        }
        String lastPayment = paymentHistory.get(paymentHistory.size() - 1);
        if (lastPayment.contains("Approved")) {
            return "Approved";
        }
        return "Pending"; 
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void logAttendance(String date) {
        attendanceLog.add(date);
        saveAttendanceLog();
    }

    public void logExercise(String date) { 
        exerciseLog.add(date);
        saveExerciseLog();
    }    

    private void saveExerciseLog() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUsername() + "_exercise.dat"))) {
            oos.writeObject(exerciseLog);
        } catch (IOException e) {
            System.out.println("Failed to save exercise log: " + e.getMessage());
        }
    }

    private void saveAttendanceLog() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getUsername() + "_attendance.dat"))) {
            oos.writeObject(attendanceLog);
        } catch (IOException e) {
            System.out.println("Failed to save attendance log: " + e.getMessage());
        }
    }

    public List<String> getAttendanceLog() {
        return attendanceLog;
    }

    public List<String> getPaymentHistory() {
        return paymentHistory;
    }

    public void addPayment(String payment) {
        paymentHistory.add(payment);
    }

    @Override
    public void displayInfo() {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Username: " + getUsername());
        System.out.println("Membership Type: " + membershipType);
        System.out.println("Membership Status: " + membershipStatus);
        System.out.println("-----------------------------------------------------------------");
    }

    public void viewPaymentHistory() {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Payment History:");
        System.out.println(" ");
        if (paymentHistory.isEmpty()) {
            System.out.println("No payments recorded.");
            System.out.println("-----------------------------------------------------------------");
            System.out.println(" ");
        } else {
            for (String payment : paymentHistory) {
                System.out.println(payment);
            }
        }
    }
    public void viewLoggedExercises() {
        System.out.println(" ");
        System.out.println("========= Logged Exercises =========");
        if (exerciseLog.isEmpty()) {
            System.out.println("No exercises logged.");
        } else {
            for (String exercise : exerciseLog) {
                System.out.println(exercise);
            }
        }
    }

    public void viewAttendanceLog() {
        System.out.println(" ");
        System.out.println("========= Attendance Log =========");
        if (attendanceLog.isEmpty()) {
            System.out.println("No attendance recorded.");
        } else {
            for (String date : attendanceLog) {
                System.out.println(date);
            }
        }
    }
    
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin Username: " + getUsername());
    }

    public void approvePayment(Member member) {
        System.out.println("Approving payment for: " + member.getUsername());
        member.addPayment("Payment Approved on " + new Date());
        System.out.println("Payment approved.");
    }

    public void viewUsers(List<User> users) {
        System.out.println(" ");
        System.out.println("========== User Details ===========");
        for (User user : users) {
            user.displayInfo();
            System.out.println("-----------------------------");
        }
    }

    public void updateEquipmentStatus(Scanner scanner, List<Equipment> equipmentList) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter equipment name to update status: ");
        String equipmentName = scanner.nextLine();

        for (Equipment equipment : equipmentList) {
            if (equipment.getName().equalsIgnoreCase(equipmentName)) {
                System.out.println(" ");
                System.out.println("Current status: " + equipment.getStatus());
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Enter new status (Available, In Maintenance, Out of Order):");
                String newStatus = scanner.nextLine();
                equipment.setStatus(newStatus);
                System.out.println(" ");
                System.out.println("Status updated successfully.");
                GymManagementSystem.saveData("equipment.dat", equipmentList);
                return;
            }
        }
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Equipment not found.");
    }

    public void deleteUser(Scanner scanner, List<User> users) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();
        User userToDelete = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToDelete = user;
                break;
            }
        }
        if (userToDelete != null) {
            users.remove(userToDelete);
            System.out.println("-----------------------------------------------------------------");
            System.out.println("User deleted successfully.");
            GymManagementSystem.saveData("users.dat", users);
        } else {
            System.out.println("User not found.");
        }
    }

    public void addEquipment(Scanner scanner, List<Equipment> equipmentList) {
        System.out.println("");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter equipment name:");
        String equipmentName = scanner.nextLine();
        Equipment newEquipment = new Equipment(equipmentName);
        equipmentList.add(newEquipment);
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Equipment added successfully.");
        GymManagementSystem.saveData("equipment.dat", equipmentList);
    }
    
    public static void updateUserStatus(Scanner scanner, List<User> users) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter username to update status: ");
        String username = scanner.nextLine();
        User userToUpdate = null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToUpdate = user;
                break;
            }
        }

        if (userToUpdate != null && userToUpdate instanceof Member member) {
            System.out.println(" ");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Current status: " + member.getMembershipStatus());
            System.out.println("Choose new status: ");
            System.out.println("[1] Active");
            System.out.println("[2] Expired");
            System.out.println("[3] Suspended");
            System.out.println(" ");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> member.setMembershipStatus("Active");
                case 2 -> member.setMembershipStatus("Expired");
                case 3 -> member.setMembershipStatus("Suspended");
                default -> System.out.println("Invalid option.");
            }
            System.out.println(" ");
            System.out.println("User status updated to: " + member.getMembershipStatus());
            GymManagementSystem.saveData("users.dat", users);
        } else {
            System.out.println(" ");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("User not found or not a member.");
        }
    }    
}

class Equipment implements Serializable {
    private String name;
    private String status;

    public Equipment(String name) {
        this.name = name;
        this.status = "Available";
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("Equipment: " + name);
        System.out.println("Status: " + status);
    }
}

public class GymManagementSystem {
    private static List<User> users = loadData("users.dat", new ArrayList<>());
    private static List<Equipment> equipmentList = loadData("equipment.dat", new ArrayList<>());
    private static final Admin admin = new Admin("admin", "admin123");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n============== GYM MANAGEMENT SYSTEM =============");
            System.out.println("|               WELCOME GYM MEMBERS              |");
            System.out.println("|                                                |");
            System.out.println("|   [1] Login                                    |");
            System.out.println("|   [2] Admin Login                              |");
            System.out.println("|   [3] Register                                 |");
            System.out.println("|   [4] Exit                                     |");
            System.out.println("|                                                |");
            System.out.println("==================================================");
            System.out.println(" ");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> adminLogin(scanner);
                case 3 -> register(scanner);
                case 4 -> {
                    System.out.println(" ");
                    System.out.println("====================================");
                    System.out.println("====Exiting the system. Goodbye!====");
                    System.out.println("====================================");
                    System.out.println(" ");
                    isRunning = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("-----------------------------------------------------------------");
    
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (user instanceof Member member) {
                    if (member.getMembershipStatus().equals("Expired")) {
                        System.out.println("Your membership has expired. Please contact the admin.");
                        return;
                    } else if (member.getMembershipStatus().equals("Suspended")) {
                        System.out.println("Your account has been suspended. Please contact the admin");
                        return;
                    }
                    System.out.println("Login successful!");
                    userMenu(member, scanner);
                    return;
                }
            }
        }
        System.out.println("Invalid credentials. Try again.");
    }

    private static void adminLogin(Scanner scanner) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        System.out.println("-----------------------------------------------------------------");
        System.out.println(" ");

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            System.out.println("Welcome Admin!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.println(" ");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Choose another.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("\n======Choose membership type=======\n| Basic[P50.00/1Day]              | \n| Premium[P300.00/1Month]         | \n| VIP[P1000.00/5 Months]          |\n");
        System.out.println("===================================");
        System.out.println(" ");
        System.out.print("Enter your choice: ");
        String membershipType = scanner.nextLine();

        users.add(new Member(username, password, membershipType));
        System.out.println(" ");
        System.out.println("Registration successful!");
        saveData("users.dat", users);
    }

    private static void userMenu(Member member, Scanner scanner) {
        boolean isRunning = true;
    
        while (isRunning) {
            System.out.println("\n========== USER MENU ==========");
            System.out.println("|                             |");
            System.out.println("|   1. View Details           |");
            System.out.println("|   2. View Payment History   |");
            System.out.println("|   3. Log Exercise           |");
            System.out.println("|   4. Log Attendance         |"); 
            System.out.println("|   5. View Attendance        |");  
            System.out.println("|   6. View Equipment         |");  
            System.out.println("|   7. View Logged Exercises  |");
            System.out.println("|   8. Logout                 |");
            System.out.println("|                             |");
            System.out.println("===============================");
            System.out.println(" ");
            System.out.print("Choose an option: ");
    
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> member.displayInfo();
                case 2 -> member.viewPaymentHistory();
                case 3 -> {
                    System.out.println(" ");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.print("Enter exercise details: ");
                    String exercise = scanner.nextLine();
                    member.logExercise(exercise);
                    System.out.println("Exercise logged successfully.");
                }
                case 4 -> {
                    System.out.println(" ");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.print("Enter attendance date: ");
                    String date = scanner.nextLine();
                    member.logAttendance(date);
                    System.out.println("Attendance logged successfully.");
                }
                case 5 -> member.viewAttendanceLog();
                case 6 -> viewEquipment();  
                case 7 -> member.viewLoggedExercises();
                case 8 -> isRunning = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }    

    private static void adminMenu(Scanner scanner) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=========== ADMIN MENU ===========");
            System.out.println("|                                |");
            System.out.println("|   [1] Approve Payments         |");
            System.out.println("|   [2] View Users               |");
            System.out.println("|   [3] Update Equipment Status  |");
            System.out.println("|   [4] Delete User              |");
            System.out.println("|   [5] Add Equipment            |");
            System.out.println("|   [6] View Equipment           |");  
            System.out.println("|   [7] Update User              |");
            System.out.println("|   [8] Logout                   |");
            System.out.println("|                                |");
            System.out.println("==================================");
            System.out.print("Choose an option: ");
    
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.println(" ");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.print("Enter username to approve payment: ");
                    String username = scanner.nextLine();
                    System.out.println(" ");
                    boolean paymentApproved = false;
    
                    for (User user : users) {
                        if (user instanceof Member member && member.getUsername().equals(username)) {
                            if (!member.getPaymentHistory().isEmpty() && member.getPaymentHistory().get(member.getPaymentHistory().size() - 1).contains("Approved")) {
                                System.out.println("Payment has already been approved for " + member.getUsername());
                            } else {
                                admin.approvePayment(member);
                                saveData("users.dat", users);
                                System.out.println("Payment approved for " + member.getUsername());
                            }
                            paymentApproved = true;
                            break;
                        }
                    }
    
                    if (!paymentApproved) {
                        System.out.println("User not found.");
                    }
                    System.out.println("-----------------------------------------------------------------");
                }
                case 2 -> admin.viewUsers(users);
                case 3 -> admin.updateEquipmentStatus(scanner, equipmentList);
                case 4 -> admin.deleteUser(scanner, users);
                case 5 -> admin.addEquipment(scanner, equipmentList);
                case 6 -> viewEquipment();
                case 7 -> Admin.updateUserStatus(scanner, users);
                case 8 -> {System.out.println("You have been logged out.");
                            isRunning = false;}
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void viewEquipment() {
        System.out.println("\n======== Equipment List ========");
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment available.");
        } else {
            for (Equipment equipment : equipmentList) {
                equipment.displayInfo();
                System.out.println("---------------------------");
            }
        }
    }

    public static <T> void saveData(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadData(String fileName, List<T> defaultData) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Data file not found. Initializing new data.");
            return defaultData;
        }
    }
}