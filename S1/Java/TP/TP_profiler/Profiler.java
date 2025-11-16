import java.util.function.Function;

public class Profiler {
    // public static double analyse(Function<Double, Double> oneMethod, double p) {
    // long t = timestamp();
    // double res = oneMethod.apply(p);
    // System.out.println(timestamp(t));
    // return res;
    // }
    private static long globalTime;
    private static int compt;

    @FunctionalInterface
    interface IntFloat4Consumer {
        void apply(int n, float xa, float ya, float xb, float yb);
    }

    public static void analyse(IntFloat4Consumer oneMethod, int n, float xa, float ya, float xb, float yb){
        long t = timestamp();
        oneMethod.apply(n, xa, ya, xb, yb);
        globalTime += timestamp() - t;
        compt++;
    }

    public static boolean analyse(Function<Double, Boolean> oneMethod, double p) {
        long t = timestamp();
        boolean res = oneMethod.apply(p);
        globalTime += timestamp() - t;
        compt++;
        return res;
    }

    /**
     * Si clock0 est >0, retourne une chaîne de caractères
     * représentant la différence de temps depuis clock0.
     * 
     * @param clock0 instant initial
     * @return expression du temps écoulé depuis clock0
     */
    public static String timestamp(long clock0) {
        String result = null;

        if (clock0 > 0) {
            double elapsed = (System.nanoTime() - clock0) / 1e9;
            String unit = "s";
            if (elapsed < 1.0) {
                elapsed *= 1000.0;
                unit = "ms";
            }
            result = String.format("%.4g%s elapsed", elapsed, unit);
        }
        return result;
    }

    /**
     * retourne l'heure courante en ns.
     * 
     * @return
     */
    public static long timestamp() {
        return System.nanoTime();
    }

    public static void initGlobalTime() {
        globalTime = 0;
        compt = 0;
    }

    public static String getGlobalTime() {
        String result = null;

        if (globalTime > 0) {
            double elapsed = globalTime / 1e9;
            String unit = "s";
            if (elapsed < 1.0) {
                elapsed *= 1000.0;
                unit = "ms";
            }
            result = String.format("%.4g%s elapsed", elapsed, unit);
        }
        return result + " in " + compt + " calls";
    }
}