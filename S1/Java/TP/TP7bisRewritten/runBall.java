public class runBall {
    final static int XSCALE = 1;
    final static int YSCALE = 1;

    final static double GRAVITY = -0.0001;

    public static void update(ball[] balls, double dt){
        applyGravity(balls);
        updatePositions(balls, dt);
    }
    public static void updatePositions(ball[] balls, double dt){
        for(int i = 0; i < balls.length; i++){
            balls[i].updatePosition(dt);
        }
    }

    public static void applyGravity(ball[] balls){
        for(int i = 0; i < balls.length; i++){
            balls[i].accelarate(0, GRAVITY);
        }        
    }
    public static void applyConstraint(ball[] balls){

    }
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-XSCALE, XSCALE);
        StdDraw.setYscale(-YSCALE, YSCALE);

        StdDraw.setPenColor(0, 0, 0);
        StdDraw.filledRectangle(0, 0, 1, 1);

        ball[] balls = new ball[5];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new ball();
        }

        StdDraw.setPenRadius(balls[0].ballSize);

        while (true) {
            StdDraw.setPenColor(0, 0, 0);
            StdDraw.filledRectangle(0, 0, YSCALE, XSCALE);

            update(balls, 1);

            for (int i = 0; i < balls.length; i++) {
                StdDraw.setPenColor(
                    (int) (55.0 + 200.0 / balls.length * i),
                    (int) (128.0 + 128.0 / balls.length * i),
                    (int) (255 - 191.0 / balls.length * i));

                StdDraw.point(balls[i].x, balls[i].y);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.text(balls[i].x, balls[i].y, "" + i + "");
            }
            StdDraw.show();
        }
    }
}