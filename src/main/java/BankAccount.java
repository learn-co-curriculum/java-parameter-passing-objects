public class BankAccount {
    private String id;
    private int balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void transfer(int amount, BankAccount depositAccount) {
        this.withdraw(amount);
        depositAccount.deposit(amount);
    }

    @Override
    public String toString() {
        return id + " balance = " + balance ;
    }

    public static void main(String[] args) {
        BankAccount checkingAccount = new BankAccount();
        checkingAccount.id = "checking account";
        BankAccount savingsAccount = new BankAccount();
        savingsAccount.id = "savings account";

        checkingAccount.deposit(100);
        System.out.println(checkingAccount);  //checking account balance = 100

        savingsAccount.deposit(2000);
        System.out.println(savingsAccount);  //savings account balance = 2000

        savingsAccount.withdraw(75);
        System.out.println(savingsAccount);  //savings account balance = 1925

        savingsAccount.transfer(500, checkingAccount);
        System.out.println(checkingAccount);  //checking account balance = 600
        System.out.println(savingsAccount);  //savings account balance = 1425

    }
}
