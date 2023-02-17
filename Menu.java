package banking;
import java.util.Scanner;
public class Menu {
    static boolean loggedInExit = false;
    static boolean exit = false;
    public void mainMenu(){
        CreateAccount account=new CreateAccount();
        Scanner sc = new Scanner(System.in);
        Output.showStartingMenu();
        while (!exit) {
            int operation = sc.nextInt();
            switch (operation) {
                case 0 -> {
                    System.out.println("Bye!");
                    exit = true;
                }
                case 1 -> account.createAccount();
                case 2 -> logIn();
                default -> Output.showStartingMenu();
            }
        }
    }
    public static void logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        String logCard = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String logPin = scanner.nextLine();
        if (Data.cardsNumValid(logCard)&&Data.cardsPinValid(logPin)) {
            loggedInExit = true;
            System.out.println("You have successfully logged in!");
            loggedInMenu(logCard);
        } else {
            System.out.println("Wrong card number or PIN!");
            Output.showStartingMenu();
        }
    }
    public static void loggedInMenu(String number) {
        Scanner sc = new Scanner(System.in);

        while (loggedInExit && !exit) {
            System.out.println();
            Output.showLogInMenu();
            int operation = sc.nextInt();
            switch (operation) {
                case 0 -> {
                    System.out.println("Bye!");
                    exit = true;
                }
                case 1 -> System.out.printf("%nBalance: %d%n",Data.balance(number));
                case 2 -> Data.addMoney(number);
                case 3->Data.transfer(number);
                case 4 ->Data.closeAc(number);
                case 5 -> {
                    System.out.println("You have successfully logged out!");
                    loggedInExit = false;
                    Output.showStartingMenu();
                }
                default -> Output.showStartingMenu();
            }
        }
    }
}
