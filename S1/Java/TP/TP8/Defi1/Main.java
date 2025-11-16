public class Main {
    public static void preCalc(double[][] coordinates, int n){
        for(double i = 0.0; i < n; i++){
            coordinates[(int)i][0] = Math.cos(i/n*(Math.PI*2))*0.8;
            coordinates[(int)i][1] = Math.sin(i/n*(Math.PI*2))*0.8;
        }
    }

    public static void drawTT(int n, double tt, double[][] coordinates){
        for(int i = 0; i < n; i++){
            int ratio = 16777215 / n;
            StdDraw.setPenColor(((i*ratio)%256), 128, 192);
            StdDraw.line(coordinates[i][0], coordinates[i][1], coordinates[(int)(tt*i%n)][0], coordinates[(int)(tt*i%n)][1]);
        }
    }

    public static void animTTn(double start, double end, double step, int n, double[][] coordinates){
        double x = start;
        while(x < end){
            StdDraw.clear(StdDraw.BLACK);
            drawTT(n, x, coordinates);
            StdDraw.show();
            x += step;
        }        
    }
    public static void show(double[][] tab){
        for(int i = 0; i < tab.length; i++){
            System.out.print(tab[i][0]); 
            System.out.println(tab[i][1]);
        }
    }
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(0.003);
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);

        int n = Integer.parseInt(args[0]);

        double[][] coordinates = new double[n][2];
        preCalc(coordinates, n);
        
        for(int i = 0; i < coordinates.length; i++){
            StdDraw.point(coordinates[i][0], coordinates[i][1]);
        }
        animTTn(10.0, 200.0, 0.01, n, coordinates);
    }
}