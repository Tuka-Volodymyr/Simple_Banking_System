package banking;
import java.util.Arrays;
import java.util.Random;
public class CreateAccount {
    final String BIN = "400000";
    String[] cardData = new String[2];
    public void createAccount() {
        StringBuilder cardNumber = new StringBuilder();
        StringBuilder PIN = new StringBuilder();
        System.out.println("Your card has been created");
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            cardNumber.append(rand.nextInt(10));
        }
        for (int i = 0; i < 4; i++) {
            PIN.append(rand.nextInt(10));
        }
        cardNumber.append(Luhn(BIN+cardNumber));
        cardData[0] = BIN + cardNumber;
        cardData[1] = PIN.toString();
        System.out.println("Your card number: ");
        System.out.println(BIN + cardNumber);
        System.out.println("Your card PIN: ");
        System.out.println(PIN);
        Output.showStartingMenu();
        Data.addData(cardData);
    }
    public static int Luhn(String cardNum){
        int[] array=new int[15];
        for(int i=0;i<15;i++){
            array[i]=Integer.parseInt(String.valueOf(cardNum.charAt(i)));
        }
        for(int i=0;i<15;i+=2){
            array[i]*=2;
            if(array[i]>9)array[i]-=9;
        }
        int suma= Arrays.stream(array).sum();
        int result=suma%10;
        if(result!=0){
            return 10-result;
        }
        return result;
    }
}