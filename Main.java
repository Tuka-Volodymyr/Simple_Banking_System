package banking;
public class Main {
    public static void main(String[] args) {
        Menu menu=new Menu();
        Data.createDataBase(args[1]);
        menu.mainMenu();
    }
}
