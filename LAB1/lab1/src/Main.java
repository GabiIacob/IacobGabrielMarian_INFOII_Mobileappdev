public class Main {
    public static int hexToDecimal(String hex) {
        return Integer.parseInt(hex, 16);}

    public static void main(String[] args) {
        customSort c1= new customSort();
        friend a1= new friend();
        dealvale dv1= new dealvale();


        String text="CarTE";
        c1.changeText(text);


        int a=220,b=284;
        a1.checkFriends(a,b);
        String hexNumber = "1A3";
        int decimalNumber =hexToDecimal(hexNumber);
        System.out.println("Numarul decimal este: " + decimalNumber);
        String parcurs="UDDDUDUU";
        dv1.checkvalley(parcurs);


        EMAILNotification en=new EMAILNotification();
        SMSNotification sn=new SMSNotification();
        PUSHNotification pn= new PUSHNotification();

        en.sendNotification();
        sn.sendNotification();
        pn.sendNotification();

    }
}