public class customSort {
    public void changeText(String text) {
        char[] a = text.toCharArray();
        int n = a.length;
        char[] uppercase = new char[n];
        int upperIndex = 0, lowerIndex = 0;

        for (int i = 0; i < n; ++i) {
            if (Character.isUpperCase(a[i])) {
                uppercase[upperIndex++] = a[i];
            } else {
                a[lowerIndex++] = a[i];
            }
        }

        int index = lowerIndex;
        for (int i = 0; i < upperIndex; ++i) {
            a[index++] = uppercase[i];
        }

        System.out.println(new String(a));
    }


}
