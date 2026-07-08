package company.pwc;

public class pwc {

    // Online Java Compiler
// Use this editor to write, compile and run your Java code online

    /*class Main {
        public static void main(String[] args) {
            System.out.println("Start small. Ship something.");
        }
    }

    Public-API methods, utility classes
    Private- Encapsulation, data hiding
    Protected- Inheritance, controlled extension
    balance field
    deposit and  withdraw methods
    calculate interest method (to be overridden by subclasses like savingsaccount




            class AccountInfo {
        private int balance;
        private String accountNumber;
        private String accountHolderName;
        private String accountType;


        //getter and setter method -> public

    }

    interface Account {
        public int getBalance(AccountInfo a);
        public boolean transferMoney(AccountInfo a, AccountInfo b) ;
        public int calInterest(AccountInfo accountInfo);
        public boolean deposite(AccountInfo accountInfo, int amount);
        public boolean withdrawn(AccountInfo accountInfo, int amount);
    }

    class BankAccount implements Account{

        public int getBalance(AccountInfo a) {

        }
        public boolean transferMoney(AccountInfo a, AccountInfo b) {

        }
        public int calInterest(AccountInfo accountInfo) {

        }
        public boolean deposite(AccountInfo accountInfo, int amount) {

        }
        public boolean withdrawn(AccountInfo accountInfo, int amount){

        }


    }




    design a simple banking system with the following requirements:
    Encapsulation: Balance should be private and only accessible via methods.
            Inheritance & Overriding: Create a savings account that extends account and overrides deposit to add interest.
            Polymorphism: Demonstrate calling overridden methods via a parent reference.
    Exception Handling: Throw a custom exception if withdrawal exceeds balance.
            Collections & Streams: Store multiple accounts and filter accounts with balance > 1000.

    interface Account {

        public void deposite(int inerestAmount);
        public boolean withrawn(int amount);

    }

    class SavingAccount implements Account {

        private int balance;
        private String accountNumber;
        private String accountHolderName;
        private String accountType;


        public int getBalance() {
            return balance;
        }

        public void setBalance(int balanace) {
            return this.balance = balance;
        }

        public void deposite(int interestAmount) {

            balance =  balance + interestAmount;

        }

        public boolean withrawn(int amount) {

            try{
                if(balance < amount) {
                    throw new WithdrwanException("balance should be not less than the withrawn amount");
                }

                balanace = balance - amount;
            }
        }
    }

    class ListAccount {
        List<Account> listOfSavingBalance = new ArrayList<>();
        SavingAccount savingAccount = new SavingAccount();
    listOfSavingBalance.add(savingAccount);

        void addAccount(Account account) {

            listOfSavingBalance.add(account);
        }

        List<Account> getFilterAccount() {
            return listOfSavingBalance.stream().filter(account -> account.balance> 1000).toList();
        }
    }*/
}
