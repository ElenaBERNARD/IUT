public class Main {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);

        int len = Integer.parseInt(args[1]);
        double end = 2;


        Vector[] vectors = new Vector[len];
        for(int i = 0; i < len; i++){
            vectors[i] = new Vector(Math.random()*0.5, Math.PI/(Math.random()*25));
            end *= vectors[i].d;
        }

        StdDraw.setXscale(-len/2, len/2);
        StdDraw.setYscale(-len/2, len/2);
    
        double n = 0;
        double step = Double.parseDouble(args[0]);

        double x;
        double y;
        while (n < 1000) {
            x = 0;
            y = 0;
            for(int i = 0; i < vectors.length; i++){
                x += vectors[i].l*Math.cos(vectors[i].d*n);
                y += vectors[i].l*Math.sin(vectors[i].d*n);
            }
            StdDraw.setPenColor((int)(n/step/10)%256, 128, 192);
            
            StdDraw.point(x, y);
            
            n += step;

            StdDraw.show();
        }
    }
}
