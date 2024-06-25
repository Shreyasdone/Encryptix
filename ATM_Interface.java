import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM_Interface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            sc.close();
            return;
        }
        User eugene = new User("Eugene Garza", 10_000, 1265);
        User dennis = new User("Dennis Gomez", 20_000, 8759);
        User tyler = new User("Tyler Steele", 5_000, 4356);
        User.addAccount(eugene);
        User.addAccount(dennis);
        User.addAccount(tyler);

        int choice;
        System.out.println("*************** Welcom To ATM Interface ****************");
        System.out.println("1. Add User");
        System.out.println("2. Start");
        choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter Your Name for Your Account (First Name space Last Name)");
                String newName = sc.nextLine();
                System.out.println("Enter The Blance You want to Deposite");
                int balance = sc.nextInt();
                System.out.println("Enter pin for you account");
                int newPin = sc.nextInt();
                sc.nextLine();
                User newUser = new User(newName, balance, newPin);
                User.addAccount(newUser);
                System.out.println("User "+newUser.getName()+" has been added successfully");
            case 2:
                System.out.println("Enter Your Name: ");
                String name = sc.nextLine();
                System.out.println("Inputted name = " + name);
                User temp = User.findUser(name);
                if (temp == null) {
                    System.out.println("User not found");
                    break;
                }
                ATM atm = new ATM(temp);
                boolean session = false;
                for (int i = 3; i > 0; i--) {
                    System.out.print("Enter Your Pin: ");
                    char[] pinArray = console.readPassword();
                    String pinString = new String(pinArray);
                    int pin = Integer.parseInt(pinString);
                    if (pin == temp.getPassword()) {
                        session = true;
                        break;
                    } else {
                        System.out.println("You have entered wrong password!!");
                        System.out.println("Press 1 to reset your pin");
                        int resetChoice = sc.nextInt();
                        switch (resetChoice) {
                            case 1:
                                System.out.println("Enter the new pin");
                                int reset = sc.nextInt();
                                atm.resetPin(reset);
                                break;
                            default:
                                System.out.println("Try again...");
                                System.out.println(i - 1 + " attempts remaining");
                                break;
                        }
                    }
                }
                while (session) {
                    System.out.println("Enter a choice (1-4)");
                    System.out.println("1. Withdraw");
                    System.out.println("2. Deposite");
                    System.out.println("3. Check Balance");
                    System.out.println("4. Exit");
                    int input = sc.nextInt();
                    switch (input) {
                        case 1:
                            System.out.println("Enter the amount you want to withdraw: ");
                            int withdraw = sc.nextInt();
                            int remainingBalance = atm.withdraw(withdraw);
                            if (remainingBalance != -1) {
                                System.out.println("Remaining Balace = " + remainingBalance);
                            }
                            break;
                        case 2:
                            System.out.println("Enter the amount you want to deposite: ");
                            int deposite = sc.nextInt();
                            System.out.println("Balance = " + atm.deposite(deposite));
                            break;
                        case 3:
                            System.out.println("Available Balance = " + atm.checkBalance());
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            System.exit(0);
                        default:
                            break;
                    }
                }
                if (!session) {
                    System.out.println("You have inputted wrong pin 3 times!!!");
                    System.out.println("Try again after some time");
                }
                
            default:
                System.out.println("Invalid input");
                break;
        }
        sc.close();
    }
}

class User {
    public static ArrayList<User> users = new ArrayList<>();
    private String name;
    private int balance;
    private int bankAccNo;
    private int password;
    static private int accountNoTrack = 100;

    User(String name, int balance, int password) {
        this.name = name;
        this.balance = balance;
        this.bankAccNo = ++accountNoTrack;
        this.password = password;
    }

    User() {
        this.name = "User";
        this.balance = 0;
        this.password = 1234;
    }

    /***** Getters *****/
    public String getName() {
        return name;
    }

    protected int getBalance() {
        return balance;
    }

    protected int getPassword() {
        return password;
    }

    protected int getBankAccNo() {
        return bankAccNo;
    }

    /****** Setters ******/
    protected void setPassword(int password) {
        this.password = password;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setBalance(int balance) {
        this.balance = balance;
    }

    public static void addAccount(User user) {
        users.add(user);
    }

    public static User findUser(String name) {
        for (User user : users) {
            if (user.name.equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

}

class ATM {
    User user;

    ATM(User user) {
        this.user = user;
    }

    int withdraw(int amount) {
        int balance = user.getBalance();
        if (amount > balance) {
            System.out.println("Not Enough Balance");
            return -1;
        }
        user.setBalance(balance - amount);
        return checkBalance();
    }

    int deposite(int amount) {
        user.setBalance(user.getBalance() + amount);
        return checkBalance();
    }

    int checkBalance() {
        return user.getBalance();
    }

    void resetPin(int pin) {
        if(pin == user.getPassword()){
            System.out.println("You can't use an old pin");
            return;
        }
        user.setPassword(pin);
        System.out.println("Pin has been Changed successfully!!");
    }
}
