package tdd;

import java.util.Arrays;

public class PrimeUtil {
    public static int[] getPrimes(int max) {
        if(max <= 2) {
            return new int[]{};
        }
        int[] primes = new int[max];
        int count = 0;
        for(int num = 2; num < max; num++) {
            if(isPrimes(num)) {
                primes[count++] = num;
            }
        }
        return Arrays.copyOf(primes, count);
    }

    private static boolean isPrimes(int num) {
        for(int i = 2; i < num / 2 + 1; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }

}
