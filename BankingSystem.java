import java.util.*;

class Account {
    protected String accNo;
    protected String holderName;
    protected double balance;

    public Account(String accNo, String holderName, double balance) {
        this.accNo = accNo;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void deposit(double amt) {
        balance += amt;
        System.out.println("Deposited Rs." + amt + ". New Balance: Rs." + balance);
    }

    public void withdraw(double amt) {
        if (amt <= balance) {
            balance -= amt;
            System.out.println("Withdrawn Rs." + amt + ". New Balance: Rs." + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void display() {
        System.out.println("Holder: " + holderName + " (" + accNo + ")");
        System.out.println("Current Balance: Rs." + balance);
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accNo, String holder, double bal, double rate) {
        super(accNo, holder, bal);
        this.interestRate = rate;
    }

        public void withdraw(double amt) {
        super.withdraw(amt);
        if (balance < 1000) {
            System.out.println(" Minimum balance warning!");
        }
    }

    public void addInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        System.out.println("Interest added: Rs." + interest + ". New Balance: Rs." + balance);
    }
}

class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accNo, String holder, double bal, double limit) {
        super(accNo, holder, bal);
        this.overdraftLimit = limit;
    }

    
    public void withdraw(double amt) {
        if (amt <= balance + overdraftLimit) {
            balance -= amt;
            System.out.println("Withdrawn Rs." + amt + ". New Balance: Rs." + balance);
            if (balance < 0) {
                System.out.println(" Penalty applicable: overdraft used");
            }
        } else {
            System.out.println("Withdrawal exceeds overdraft limit!");
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Bank Account Management System ===");
        System.out.println("Choose Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        sc.nextLine(); 

        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        Account acc = null;

        if (choice == 1) {
            System.out.print("Enter Interest Rate (%): ");
            double rate = sc.nextDouble();
            acc = new SavingsAccount(accNo, name, balance, rate);
        } else if (choice == 2) {
            System.out.print("Enter Overdraft Limit: ");
            double limit = sc.nextDouble();
            acc = new CurrentAccount(accNo, name, balance, limit);
        } else {
            System.out.println("Invalid choice!");
            System.exit(0);
        }

        System.out.println("\n--- Account Created Successfully ---");
        acc.display();

        System.out.print("\nEnter amount to deposit: ");
        double dep = sc.nextDouble();
        acc.deposit(dep);

        System.out.print("Enter amount to withdraw: ");
        double wd = sc.nextDouble();
        acc.withdraw(wd);

        System.out.println("\n--- Final Account Status ---");
        acc.display();

         if (choice == 1) {
            ((SavingsAccount) acc).addInterest();
        }

        sc.close();
    }
}
