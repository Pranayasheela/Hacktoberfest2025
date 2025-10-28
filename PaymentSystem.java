import java.util.*;


interface PaymentMethod {
    boolean authenticate(String pin);
    boolean processPayment(double amt);
}

interface Rewardable {
    double calculateReward(double amt);
}

abstract class Transaction implements PaymentMethod, Rewardable {
   String user;
    protected boolean authStatus;
    protected double rewardPoints;

    public Transaction(String user) {
        this.user = user;
        this.authStatus = false;
        this.rewardPoints = 0.0;
    }
}
class CreditCard extends Transaction {
     static final double LIMIT = 50000.0;

    public CreditCard(String user) {
        super(user);
    }

        public boolean authenticate(String pin) {
        authStatus = pin.equals("1234");
        return authStatus;
    }

        public boolean processPayment(double amt) {
        if (!authStatus) return false;
        return amt <= LIMIT;
    }

        public double calculateReward(double amt) {
        return amt * 0.01;
    }
}

class UPIPayment extends Transaction {
    public UPIPayment(String user) {
        super(user);
    }

       public boolean authenticate(String pin) {
        authStatus = true; 
        return true;
    }

       public boolean processPayment(double amt) {
        return true;
    }

       public double calculateReward(double amt) {
        return amt * 0.005;
    }
}

class Wallet extends Transaction {
    private double balance;

    public Wallet(String user, double balance) {
        super(user);
        this.balance = balance;
    }

        public boolean authenticate(String pin) {
        authStatus = true;
        return true;
    }

        public boolean processPayment(double amt) {
        if (amt <= balance) {
            balance -= amt;
            return true;
        }
        return false;
    }

       public double calculateReward(double amt) {
        return amt * 0.002;
    }
}

public class PaymentSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

         System.out.print("Enter Credit Card user: ");
        String ccUser = sc.nextLine();
        CreditCard cc = new CreditCard(ccUser);
        System.out.print("Enter amount: ");
        double ccAmt = sc.nextDouble();
        sc.nextLine();
        cc.authenticate("1234");
        boolean ccStatus = cc.processPayment(ccAmt);
        double ccReward = ccStatus ? cc.calculateReward(ccAmt) : 0;
        System.out.println("Credit Card Payment: " + (ccStatus ? "Success" : "Failed") + ", Reward ₹" + ccReward);

         System.out.print("Enter UPI user: ");
        String upiUser = sc.nextLine();
        UPIPayment upi = new UPIPayment(upiUser);
        System.out.print("Enter amount: ");
        double upiAmt = sc.nextDouble();
        upi.authenticate("0000");
        boolean upiStatus = upi.processPayment(upiAmt);
        double upiReward = upi.calculateReward(upiAmt);
        System.out.println("UPI Payment: " + (upiStatus ? "Success" : "Failed") + ", Reward ₹" + upiReward);

         sc.nextLine();
        System.out.print("Enter Wallet user: ");
        String wUser = sc.nextLine();
        System.out.print("Enter balance: ");
        double bal = sc.nextDouble();
        System.out.print("Enter payment amount: ");
        double wAmt = sc.nextDouble();

        Wallet wallet = new Wallet(wUser, bal);
        wallet.authenticate("0000");
        boolean wStatus = wallet.processPayment(wAmt);
        double wReward = wStatus ? wallet.calculateReward(wAmt) : 0;
        System.out.println("Wallet Payment: " + (wStatus ? "Success" : "Failed (Insufficient Balance)") + ", Reward ₹" + wReward);

        double maxReward = Math.max(ccReward, Math.max(upiReward, wReward));
        String highest = (maxReward == ccReward) ? "Credit Card" :
                         (maxReward == upiReward) ? "UPI" : "Wallet";
        System.out.println("Highest Reward: " + highest);

        sc.close();
    }
}
