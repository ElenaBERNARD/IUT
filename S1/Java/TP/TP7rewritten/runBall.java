public class runBall{
    final static double XSCALE = 1;
    final static double YSCALE = 1;
    final static Vec2 gravity = new Vec2(0, -0.1);

    public static void update(Ball[] balls, double dt){
        applyGravity(balls);
        applyConstraint(balls);
        updatePositions(balls, dt);
    }

    public static void updatePositions(Ball[] balls, double dt){
        for(int i = 0; i < balls.length; i++) 
            balls[i].updatePosition(dt);
    }

    public static void applyGravity(Ball[] balls){
        for(int i = 0; i < balls.length; i++) 
            balls[i].accelerate(gravity);
    }

    public static void applyConstraint(Ball[] balls){
        double radius = 0.4;
        for(int i = 0; i < balls.length; i++){
            double dist = balls[i].position_current.distance(new Vec2());
            if(dist > radius - 0.1){
                Vec2 n = balls[i].position_current.multiply(-dist);
                balls[i].position_current = balls[i].position_current.add(n.multiply(dist-0.1));
                balls[i].position_old = balls[i].position_current;
            }
        }
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-XSCALE, XSCALE);
        StdDraw.setYscale(-YSCALE, YSCALE);

        StdDraw.setPenRadius(0.1);

        Ball[] balls = new Ball[1];
        for(int i = 0; i < balls.length; i++){
            balls[i] = new Ball();
        }

        StdDraw.setPenColor(0, 0, 0);
        
        while(true){
            StdDraw.clear();
            
            for(int i = 0; i < balls.length; i++){
                StdDraw.point(balls[i].getX(), balls[i].getY());
            }

            update(balls, 0.01);
            StdDraw.show();
        }
    }
}