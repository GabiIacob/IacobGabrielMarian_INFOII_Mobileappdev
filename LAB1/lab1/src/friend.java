public class friend {
    void checkFriends(int a, int b){
        int suma=0,sumb=0;
        for (int i=1;i<a;i++) {
            if (a % i == 0)
                suma+=i;
        }
        for (int j=1;j<b;j++) {
            if (b % j == 0)
                sumb+=j;
        }
        if ((suma==b)&&(sumb==a))
            System.out.println("Numerele sunt prietene");
        else
            System.out.println("Numerele nu sunt prietene");
    }
}
