package lab1;

public class Calculations {
    public static long calculateFactorial(Integer n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static long calculateSum(int a, int b) {
        long result = 0;
        for (int i = a; i <= b; i++) {
            result += i;
        }
        return result;
    }
}
