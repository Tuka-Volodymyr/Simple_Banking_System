package banking;
public class Output {
    public static void showLogInMenu() {
        System.out.println("""
                1. Balance
                2. Add income
                3. Do transfer
                4. Close account
                5. Log out
                0. Exit""");
    }
    public static void showStartingMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }
}