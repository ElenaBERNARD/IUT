public class runBall{
    final static int XSCALE = 1;
    final static int YSCALE = 1;

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-XSCALE, XSCALE);
        StdDraw.setYscale(-YSCALE, YSCALE);

        StdDraw.setPenColor(0, 0, 0);
        StdDraw.filledRectangle(0, 0, 1, 1);

        ball[] balls = new ball[50];
        for(int i = 0; i < balls.length; i++){
            balls[i] = new ball();
        }


        while(true){
            StdDraw.setPenRadius(balls[0].ballSize + 0.01);
            for(int i = 0; i < balls.length; i++){
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.point(balls[i].x, balls[i].y);
                balls[i].step();
            }

            StdDraw.setPenRadius(balls[0].ballSize);
            for(int i = 0; i < balls.length; i++){
                StdDraw.setPenColor(
                    (int)(55.0+200.0/balls.length*i), 
                    (int)(128.0+128.0/balls.length*i), 
                    (int)(255-191.0/balls.length*i));
                StdDraw.point(balls[i].x, balls[i].y);
            }
            StdDraw.show();
        }
    }
}