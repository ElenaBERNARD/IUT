import java.util.Random;

public class test{
    static double randomGenerator(long seed) {
        Random generator = new Random(seed);
        double num = generator.nextDouble();
    
        return num;
    }

    static double[] GenerateNumbers(long seed, int amount) {
        double[] randomList = new double[amount];
        for (int i=0;i<amount;i++) {
            Random generator = new Random(seed);
            randomList[i] = Math.abs((double) (generator.nextLong() % 0.001) * 10000);
            seed--;
        }
        return randomList;
    }
    public static void main(String[] args) {

        double[] randomList = GenerateNumbers(6,10);
        for(int i = 0; i < 10; i++){
            System.out.println(randomList[i]);
        }

    }
}