package banking;
import java.sql.*;
import org.sqlite.*;
import java.util.Scanner;
public class Data {
    public static SQLiteDataSource dataSource;
    public static String insertSQL = "INSERT INTO card VALUES (?,?,?,?)";
    public static void createDataBase(String arg) {
        String ulr = String.format("jdbc:sqlite:%s", arg);
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(ulr);
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addData(String[] cardData) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            int i = 1;
            while (true) {
                try {
                    statement.setInt(1, i);
                    statement.setString(2, String.format("%s", cardData[0]));
                    statement.setString(3, String.format("%s", cardData[1]));
                    statement.setInt(4, 0);
                    statement.executeUpdate();
                    break;
                } catch (SQLException e) {
                    i += 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int balance(String number) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(String.format("SELECT balance FROM card WHERE number ='%s'",number));
            return resultSet.getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void addMoney(String number) {
        String changeBalance = "UPDATE card SET balance=? WHERE number=?";
        Scanner scanner=new Scanner(System.in);
        System.out.println("\nEnter income:");
        int sum=scanner.nextInt();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(changeBalance);
            statement.setInt(1,balance(number)+sum);
            statement.setString(2,number);
            statement.executeUpdate();
            System.out.println("Income was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeAc(String number){
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("DELETE FROM card WHERE number='%s'",number));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean cardsNumValid(String number){
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT number FROM card");
            while (resultSet.next()){
                if(resultSet.getString("number").equals(number)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean cardsPinValid(String pin){
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT pin FROM card ");
            while (resultSet.next()){
                if(resultSet.getString("pin").equals(pin)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void transfer(String number){
        Scanner scanner=new Scanner(System.in);
        String changeBalance = "UPDATE card SET balance=? WHERE number=?";
        System.out.println("Transfer\n" +
                "Enter card number:");
        String toNumber=scanner.nextLine();
        if(Character.getNumericValue(toNumber.charAt(15))!=CreateAccount.Luhn(toNumber)){
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }else if(number.equals(toNumber)){
            System.out.println("You can't transfer money to the same account!");
        }else if(!cardsNumValid(toNumber)){
            System.out.println("Such a card does not exist.");
        }else {
            System.out.println("Enter how much money you want to transfer:");
            int sum=scanner.nextInt();
            if (balance(number)<sum){
                System.out.println("Not enough money!");
            }else{
                try (Connection connection = dataSource.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement(changeBalance);
                    statement.setInt(1,balance(number)-sum);
                    statement.setString(2,number);
                    statement.executeUpdate();

                    statement.setInt(1,balance(toNumber)+sum);
                    statement.setString(2,toNumber);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}