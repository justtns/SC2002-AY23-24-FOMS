import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminInterface {

    Scanner scanner = new Scanner(System.in);
    Admin currentUser;

    public AdminInterface(Admin currentUser) {
        this.currentUser = currentUser;
    }

    public void staffOperations() {

        System.out.println("------------------Staff Menu------------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. Add Staff\t2. Edit Staff\t3. Remove Staff\t4. Display Staff: ");
        scanner.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.next());
        } catch (InputMismatchException e) {
            System.out.println("Invalid Choice..\n");
            return;
        }
        switch (choice) {
            case 1: {
                System.out.println("Enter Staff Name: ");
                String name = scanner.nextLine(); // Consuming newline character
                name = scanner.nextLine(); // Taking input

                System.out.println("Choose Gender:\n1. Male\t2. Female ");
                int g = 1;
                try {
                    g = Integer.parseInt(scanner.next());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input...");
                    return;
                }
                String gender = g == 1 ? "M" : g == 2 ? "F" : null;
                if (gender == null) {
                    System.out.println("Invalid Input..");
                    return;
                }
                System.out.println("Enter age: ");
                int age = scanner.nextInt();
                if (age < 0 || age >= 100) {
                    System.out.println("Invalid Age..");
                    return;
                }
                System.out.println("Choose a Branch: ");
                for (int i = 0; i < DataManager.branchStrategy.allBranches.size(); i++) {
                    System.out.println(i + ": " + DataManager.branchStrategy.allBranches.get(i).getBranchName());
                }
                System.out.println("Enter Branch Index (0-" + (DataManager.branchStrategy.allBranches.size() - 1) + "): ");
                int index=-1;
                try {


                    index = Integer.parseInt(scanner.next());
                }catch (InputMismatchException e)
                {
                    System.out.println("Invalid Input..");
                    return;
                }

                if (index < 0 || index >= DataManager.branchStrategy.allBranches.size()) {
                    System.out.println("Invalid Input...");
                    return;
                }
                Branch branch = DataManager.branchStrategy.allBranches.get(index);

                // Create new Staff object
                Staff staff = new Staff(name, "password", Role.Staff, gender, age, branch.getBranchName());
                DataManager.staffStrategy.allStaff.add(staff);
                DataManager.saveAllData();
                System.out.println("New Staff is Added with Following details: ");
                System.out.println(staff);
                break;
            }
            case 2: {
                // Edit Staff
                System.out.println("Enter the name of the Staff to edit: ");
                String editName = scanner.next();
                boolean found = false;
                for (Staff staff : DataManager.staffStrategy.allStaff) {
                    if (staff.getName().equalsIgnoreCase(editName)) {
                        found = true;
                        // Edit staff details
                        System.out.println("Enter new password: ");
                        String newPassword = scanner.next();
                        System.out.println("Choose new Gender:\n1. Male\t2. Female ");
                        int newGender = scanner.nextInt();
                        String gender = newGender == 1 ? "Male" : newGender == 2 ? "Female" : null;
                        if (gender == null) {
                            System.out.println("Invalid Input..");
                            return;
                        }
                        System.out.println("Enter new age: ");
                        int newAge = scanner.nextInt();
                        if (newAge < 0 || newAge >= 100) {
                            System.out.println("Invalid Age..");
                            return;
                        }
                        System.out.println("Choose a new Branch: ");
                        for (int i = 0; i < DataManager.branchStrategy.allBranches.size(); i++) {
                            System.out.println(i + ": " + DataManager.branchStrategy.allBranches.get(i).getBranchName());
                        }
                        System.out.println("Enter new Branch Index (0-" + (DataManager.branchStrategy.allBranches.size() - 1) + "): ");
                        int newIndex = scanner.nextInt();
                        if (newIndex < 0 || newIndex >= DataManager.branchStrategy.allBranches.size()) {
                            System.out.println("Invalid Input...");
                            return;
                        }
                        Branch newBranch = DataManager.branchStrategy.allBranches.get(newIndex);
                        // Update staff details
                        staff.setPassword(newPassword);
                        staff.setGender(gender);
                        staff.setAge(newAge);
                        staff.setBranch(newBranch.getBranchName());
                        System.out.println("Staff details updated successfully.");
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Staff not found.");
                }
                break;
            }
            case 3: {
                // Remove Staff
                System.out.println("Enter the Login ID of the Staff to remove: ");
                String removeID= scanner.next();
                boolean removed = false;
                for (Staff staff : DataManager.staffStrategy.allStaff) {
                    if (staff.getLoginID().equalsIgnoreCase(removeID)) {
                        DataManager.staffStrategy.allStaff.remove(staff);
                        removed = true;
                        System.out.println("Staff removed successfully.");
                        break;
                    }
                }
                if (!removed) {
                    System.out.println("Staff not found.");
                }
                break;
            }
            case 4: {
                // Display Staff with filters
                System.out.println("Filter options:");
                System.out.println("1. By Branch\t2. By Role\t3. By Gender\t4. By Age");
                System.out.println("Enter filter option: ");
                int filterOption = scanner.nextInt();

                switch (filterOption) {
                    case 1: {
                        // Filter by Branch
                        System.out.println("Enter branch name to filter: ");
                        scanner.nextLine();//consume nextLine
                        String branchFilter = scanner.next();
                        System.out.println("Staff List (Filtered by Branch - " + branchFilter + "):");
                        for (Staff staff : DataManager.staffStrategy.allStaff) {
                            if (staff.getBranch().equalsIgnoreCase(branchFilter)) {
                                System.out.println(staff);
                            }
                        }
                        break;
                    }
                    case 2: {
                        // Filter by Role
                        System.out.println("Enter role to filter (S for Staff, M for Manager, A for Admin): ");
                        String input= scanner.next().toUpperCase();
                        char roleFilter=input.charAt(0);
                        if(roleFilter=='S' || roleFilter=='M' || roleFilter=='A') {
                            System.out.println("Staff List (Filtered by Role - " + roleFilter + "):");
                            for (Staff staff : DataManager.staffStrategy.allStaff) {
                                if (staff.getRole().toString().charAt(0) == roleFilter) {
                                    System.out.println(staff);
                                }
                            }
                        }
                        else System.out.println("Invalid Choice...");
                        break;
                    }
                    case 3: {
                        // Filter by Gender
                        System.out.println("Enter gender to filter (Male or Female): ");
                        String genderFilter = scanner.next();
                        System.out.println("Staff List (Filtered by Gender - " + genderFilter + "):");
                        for (Staff staff : DataManager.staffStrategy.allStaff) {
                            if (staff.getGender().equalsIgnoreCase(genderFilter)) {
                                System.out.println(staff);
                            }
                        }
                        break;
                    }
                    case 4: {
                        // Filter by Age
                        System.out.println("Enter minimum age: ");
                        int minAge = scanner.nextInt();
                        System.out.println("Enter maximum age: ");
                        int maxAge = scanner.nextInt();
                        System.out.println("Staff List (Filtered by Age - " + minAge + " to " + maxAge + "):");
                        for (Staff staff : DataManager.staffStrategy.allStaff) {
                            if (staff.getAge() >= minAge && staff.getAge() <= maxAge) {
                                System.out.println(staff);
                            }
                        }
                        break;
                    }
                    default:
                        System.out.println("Invalid filter option..\n");
                }
                break;
            }

            default:
                System.out.println("Invalid Choice..\n");
        }
    }


    // Method to assign a manager to a branch
    public void assignManagerToBranch() {
        System.out.println("Available Managers for Assignment:");
        int managerIndex = 0;

        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getRole() == Role.Manager ) {
                System.out.println(managerIndex + ". " + staff.getName() + " (" + staff.getRole() + ")");
                managerIndex++;

            }
        }


        scanner.nextLine();
        System.out.println("Select a manager to assign to a branch:");
        int choice = scanner.nextInt();
        if (choice < 0 || choice >= managerIndex) {
            System.out.println("Invalid choice.");
            return;
        }
        String managerName = getManagerNameByIndex(choice);
        Branch branch = selectBranch();
        if (branch == null) {
            System.out.println("Invalid branch selection.");
            return;
        }
        int staffCount = countStaffInBranch(branch);
        int managerCount = countManagersInBranch(branch);


        if(staffCount==0 || managerCount==0)
            assignManagerToBranchAndUpdate(branch, managerName);
        else if (staffCount >= 1 && staffCount <= 4 && managerCount < 1) {
            assignManagerToBranchAndUpdate(branch, managerName);
        } else if (staffCount >= 5 && staffCount <= 8 && managerCount < 2) {
            assignManagerToBranchAndUpdate(branch, managerName);
        } else if (staffCount >= 9 && staffCount <= 15 && managerCount < 3) {
            assignManagerToBranchAndUpdate(branch, managerName);
        } else {
            System.out.println("The Branch "+branch.getBranchName()+" already has the maximum number of managers allowed.");
        }
        DataManager.saveAllData();
    }


    // Method to promote a staff member to a manager
    public void promoteStaffToManager() {
        System.out.println("Available Staff for Promotion:");
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getRole() == Role.Staff) {
                System.out.println(staff.getName());
            }
        }

        scanner.nextLine();
        System.out.println("Enter Staff name:");
        String staffName = scanner.next();
        Branch branch = getBranchFromStaff(staffName);

        boolean found = false;
        int staffCount = countStaffInBranch(branch);
        int managerCount = countManagersInBranch(branch);
        if(staffCount==0 || managerCount==0)
           found= promoteStaffToManagerAndAssign(branch, staffName);
        else
       if (staffCount >= 1 && staffCount <= 4 && managerCount < 1) {
            found = promoteStaffToManagerAndAssign(branch, staffName);
        } else if (staffCount >= 5 && staffCount <= 8 && managerCount < 2) {
            found = promoteStaffToManagerAndAssign(branch, staffName);
        } else if (staffCount >= 9 && staffCount <= 15 && managerCount < 3) {
            found = promoteStaffToManagerAndAssign(branch, staffName);
        } else {
           System.out.println("The Branch "+branch.getBranchName()+" already has the maximum number of managers allowed.");

           return;
        }
        if (!found) {
            System.out.println("Staff not found or not eligible for promotion.");
        }
        DataManager.saveAllData();
    }



    // Method to transfer a staff member or manager among branches
    public void transferStaffOrManagerAmongBranches() {
        System.out.println("Choose who you want to transfer:");
        System.out.println("1. Staff");
        System.out.println("2. Manager");
        System.out.print("Enter your choice (1-2): ");
        int transferChoice = scanner.nextInt();

        switch (transferChoice) {
            case 1: // Transfer a staff member
                System.out.println("Available Staff for Transfer:");
                for (Staff staff : DataManager.staffStrategy.allStaff) {
                    if (staff.getRole() == Role.Staff) {
                        System.out.println("- " + staff.getName());
                    }
                }
                scanner.nextLine();
                System.out.print("Enter the name of the staff member to transfer: ");
                String staffName = scanner.next();
                Staff selectedStaff = getStaffByName(staffName);
                if (selectedStaff != null) {
                    Branch selectedBranch = selectBranch();
                    if (selectedBranch != null) {
                        selectedStaff.setBranch(selectedBranch.getBranchName());
                        System.out.println(selectedStaff.getName() + " transferred to " + selectedBranch.getBranchName());
                        DataManager.saveAllData();
                    } else {
                        System.out.println("Invalid branch selection.");
                    }
                } else {
                    System.out.println("Staff member not found.");
                }
                break;

            case 2: // Transfer a manager
                System.out.println("Available Managers for Transfer:");
                for (Staff staff : DataManager.staffStrategy.allStaff) {
                    if (staff.getRole() == Role.Manager) {
                        System.out.println("- " + staff.getName());
                    }
                }
                System.out.print("Enter the name of the manager to transfer: ");
                String managerName = scanner.next();
                Staff selectedManager = getStaffByName(managerName);
                if (selectedManager != null) {
                    Branch selectedBranch = selectBranch();
                    if (selectedBranch != null) {
                        int staffCount = countStaffInBranch(selectedBranch);
                        int managerCount = countManagersInBranch(selectedBranch);
                        if (staffCount < 1 || managerCount < 1) {
                            // No restriction on manager assignment if there are no staff or managers in the branch
                            selectedManager.setBranch(selectedBranch.getBranchName());
                            System.out.println(selectedManager.getName() + " transferred to " + selectedBranch.getBranchName());
                            DataManager.saveAllData();
                        } else if (staffCount >= 1 && staffCount <= 4 && managerCount < 1) {
                            selectedManager.setBranch(selectedBranch.getBranchName());
                            System.out.println(selectedManager.getName() + " transferred to " + selectedBranch.getBranchName());
                            DataManager.saveAllData();
                        } else if (staffCount >= 5 && staffCount <= 8 && managerCount < 2) {
                            selectedManager.setBranch(selectedBranch.getBranchName());
                            System.out.println(selectedManager.getName() + " transferred to " + selectedBranch.getBranchName());
                            DataManager.saveAllData();
                        } else if (staffCount >= 9 && staffCount <= 15 && managerCount < 3) {
                            selectedManager.setBranch(selectedBranch.getBranchName());
                            System.out.println(selectedManager.getName() + " transferred to " + selectedBranch.getBranchName());
                            DataManager.saveAllData();
                        } else {
                            System.out.println("The Branch " + selectedBranch.getBranchName() + " already has the maximum number of managers allowed.");
                        }
                    } else {
                        System.out.println("Invalid branch selection.");
                    }
                } else {
                    System.out.println("Manager not found.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    //method to open or close a branch
    // Method to open or close a branch
    public void openCloseBranch() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Branch");
        System.out.println("2. Remove Branch");
        System.out.print("Enter your choice (1-2): ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                addBranch();
                break;
            case 2:
                removeBranch();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to add a branch
    // Method to add a new branch
    public void addBranch() {
        System.out.println("Enter Branch Name:");
        String branchName = scanner.nextLine(); // Consuming newline character
        branchName = scanner.nextLine(); // Taking input
        System.out.println("Enter Location:");
        String location = scanner.nextLine();
        System.out.println("Enter Total Staff:");
        int totalStaff = 0;
        try {
            totalStaff = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for total staff.");
            return;
        }

        // Create a new branch object
        Branch branch = new Branch(branchName, location, totalStaff);
        // Add the branch to the list of all branches
        DataManager.branchStrategy.allBranches.add(branch);
        // Save data
        DataManager.saveAllData();
        System.out.println("New branch added successfully.");
    }

    public  void changePassword()
    {
        scanner.nextLine();
        System.out.println("Enter new Password:");
        String password=scanner.next();
        currentUser.setPassword(password);
        DataManager.saveAllData();
        System.out.println("Password change successfully");

    }

    // Method to remove a branch
    private void removeBranch() {
        System.out.println("Enter the name of the branch to remove: ");
        String branchName = scanner.next();
        boolean found = false;
        for (Branch branch : DataManager.branchStrategy.allBranches) {
            if (branch.getBranchName().equalsIgnoreCase(branchName)) {
                DataManager.branchStrategy.allBranches.remove(branch);
                System.out.println("Branch '" + branchName + "' removed successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Branch '" + branchName + "' not found.");
        }
    }







    //private helper Methods


    // Method to get the branch associated with a staff member
    private Branch getBranchFromStaff(String staffName) {
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getName().equalsIgnoreCase(staffName)) {
                // Check if the staff is a manager

                // Iterate through all branches to find the matching branch name
                for (Branch branch : DataManager.branchStrategy.allBranches) {
                    if (branch.getBranchName().equalsIgnoreCase(staff.getBranch())) {
                        return branch;


                    }
                }
                break; // Stop searching after finding the staff member
            }
        }
        return null; // Return null if the staff member is not found or not associated with a branch
    }
    // Method to get staff by name
    private Staff getStaffByName(String name) {
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getName().equalsIgnoreCase(name)) {
                return staff;
            }
        }
        return null;
    }



    // Method to get manager name by index
    private String getManagerNameByIndex(int index) {
        int managerIndex = 0;
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getRole() == Role.Manager ) {
                if (managerIndex == index) {
                    return staff.getName();
                }
                managerIndex++;
            }
        }
        return null;
    }
    private Branch selectBranch() {
        System.out.println("Available Branches:");
        for (int i = 0; i < DataManager.branchStrategy.allBranches.size(); i++) {
            System.out.println(i + ": " + DataManager.branchStrategy.allBranches.get(i).getBranchName());
        }
        System.out.println("Enter the index of the branch: ");
        int index = scanner.nextInt();
        if (index >= 0 && index < DataManager.branchStrategy.allBranches.size()) {
            return DataManager.branchStrategy.allBranches.get(index);
        } else {
            System.out.println("Invalid branch selection.");
            return null;
        }
    }

    // Method to count the number of staff members (excluding managers) in a branch
    private int countStaffInBranch(Branch branch) {
        int count = 0;
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getBranch().equalsIgnoreCase(branch.getBranchName()) && staff.getRole() == Role.Staff) {
                count++;
            }
        }
        return count;
    }

    // Method to count the number of managers in a branch
    private int countManagersInBranch(Branch branch) {
        int count = 0;
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getBranch().equalsIgnoreCase(branch.getBranchName()) && staff.getRole() == Role.Manager) {
                count++;
            }
        }
        return count;
    }

    // Method to assign a manager to a branch and update the manager's branch
    private void assignManagerToBranchAndUpdate(Branch branch, String managerName) {
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getName().equalsIgnoreCase(managerName) ) {
                staff.setBranch(branch.getBranchName());
                System.out.println("Manager assigned to the branch successfully.");
                return;
            }
        }
        System.out.println("Manager not found or already assigned to a branch.");
    }






    // Method to promote a staff member to a manager and assign them to a branch
    private boolean promoteStaffToManagerAndAssign(Branch branch, String staffName) {
        for (Staff staff : DataManager.staffStrategy.allStaff) {
            if (staff.getName().equalsIgnoreCase(staffName) ) {

                BranchManager manager = new BranchManager(staff.getName(), staff.getPassword(), staff.getGender(), staff.getAge(), branch.getBranchName());
                DataManager.staffStrategy.allStaff.remove(staff);
                DataManager.staffStrategy.allStaff.add(manager);
                System.out.println("Staff promoted as manager and assigned to the branch successfully.");
                return true;
            }
        }
        return false;
    }


    // Method to add or remove payment methods
    public void addRemovePaymentMethods() {
        scanner.nextLine();
        System.out.println("Choose an option:");
        System.out.println("1. Add Payment Method");
        System.out.println("2. Remove Payment Method");
        System.out.print("Enter your choice (1-2): ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice.");
            return;
        }

        switch (choice) {
            case 1:
                addPaymentMethod();
                break;
            case 2:
                removePaymentMethod();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to add a new payment method
    private void addPaymentMethod() {
        System.out.println("Enter the name of the new payment method:");
        String methodName = scanner.nextLine(); // Consuming newline character
        methodName = scanner.nextLine(); // Taking input
        PaymentMethod newMethod = new PaymentMethod(methodName);
        DataManager.paymentStrategy.addItem(newMethod);
        System.out.println("New payment method added successfully.");
    }

    // Method to remove a payment method
    private void removePaymentMethod() {
        if (DataManager.paymentStrategy.paymentMethods.isEmpty()) {
            System.out.println("There are no payment methods to remove.");
            return;
        }
        System.out.println("Current Payment Methods:");
        for (int i = 0; i < DataManager.paymentStrategy.paymentMethods.size(); i++) {
            System.out.println((i + 1) + ". " + DataManager.paymentStrategy.paymentMethods.get(i).getMethod());
        }
        System.out.print("Enter the index of the payment method to remove: ");
        int index = 0;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for index.");
            return;
        }

        if (index < 1 || index > DataManager.paymentStrategy.paymentMethods.size()) {
            System.out.println("Invalid index.");
            return;
        }

        // Remove the payment method at the specified index
        DataManager.paymentStrategy.paymentMethods.remove(index - 1);
        System.out.println("Payment method removed successfully.");
    }


}
