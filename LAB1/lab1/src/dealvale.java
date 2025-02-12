public class dealvale {
    void checkvalley(String drum){
        char[] a = drum.toCharArray();
        int n=a.length,parcurs=0,valleys=0;
        boolean poatefivale=false;
        for (int i =0;i<n;++i){
            if (parcurs>0) poatefivale=true;
            if (parcurs==0&& poatefivale==true) valleys++;
            if (parcurs<0) poatefivale=false;
            if (a[i]=='U') parcurs++;
            if (a[i]=='D') parcurs--;}
        System.out.println("Valleys number: "+valleys);}}
