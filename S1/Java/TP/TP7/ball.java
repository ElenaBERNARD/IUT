public class ball {
    final static int XSCALE = 1;
    final static int YSCALE = 1;
    final static double GRAVITY = 0.000089;
    final static double FRICTION = 0.00001;
    final static double IMPACT_FRICTION = 0.002;

    public double x = 0.0;
    public double y = 0.0;

    public double maxSpeed = 0.01;

    public double randomXvelocity = maxSpeed - Math.random() * (2*maxSpeed);
    public double randomYvelocity = Math.random()*maxSpeed;

    public double dx = randomXvelocity;
    public double dy = randomYvelocity;

    public double ballSize = 0.02;

    public ball(){

    }

    public void step() {
        if (x < -XSCALE) {
            x = -XSCALE + ballSize;
            dx = -dx;
            if(Math.abs(dx)-IMPACT_FRICTION > 0) dx -= IMPACT_FRICTION;
            else dx = 0;
        }
        else if (x > XSCALE) {
            x = XSCALE - ballSize;
            dx = -dx;
            if(Math.abs(dx)-IMPACT_FRICTION > 0) dx -= IMPACT_FRICTION;
            else dx = 0;
        }

        if (y < -YSCALE) {
            y = -YSCALE + ballSize;
            dy = -dy;
            if(Math.abs(dy) - IMPACT_FRICTION > 0) dy -= IMPACT_FRICTION;
            else dy = 0;
        }
        else if (y > YSCALE) {
            y = YSCALE - ballSize;
            dy = -dy;
            if(Math.abs(dy) - IMPACT_FRICTION > 0) dy -= IMPACT_FRICTION;
            else dy = 0;
        }

        if(y > -YSCALE && dy != 0) dy -= GRAVITY;

        if(dy == 0 && Math.abs(dx) - FRICTION > 0)
            if(dx>0) dx -= FRICTION;
            else dx += FRICTION;
        else if(dy == 0 && Math.abs(dx) - FRICTION < 0)
            dx = 0;

        if(x > -XSCALE || x < XSCALE) x += dx;
        if(y > -YSCALE || y < YSCALE) y += dy;

    }
}