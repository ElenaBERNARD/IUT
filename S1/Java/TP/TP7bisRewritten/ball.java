public class ball {
    final static int XSCALE = 1;
    final static int YSCALE = 1;
    final static double GRAVITY = 0.001;
    final static double FRICTION = 0.00005;
    final static double IMPACT_FRICTION = 0.003;

    public double x = 1.0 - Math.random()*2;
    public double y = 1.0 - Math.random()*2;
    public double xOld = x;
    public double yOld = y;

    public double maxSpeed = 0.01;

    public double randomXvelocity = maxSpeed - Math.random() * (2*maxSpeed);
    public double randomYvelocity = Math.random()*maxSpeed;

    public double dx = randomXvelocity;
    public double dy = randomYvelocity;
    public double accX = 0;
    public double accY = 0;

    public double ballSize = 0.05;

    public ball(){

    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void accelarate(double x, double y){
        accX += x;
        accY += y;
    }

    public void updatePosition(double dt){
        dx = x-xOld;
        dy = y-yOld;

        xOld = x;
        yOld = y;

        x = x + dx + accX * dt * dt;
        y = y + dy + accY * dt * dt;

        accX = 0;
        accY = 0;
    }
    // public boolean ballColision(ball balle){
    //     if(this.x+ballSize > balle.x-ballSize && this.x-ballSize < balle.x+ballSize
    //     && this.y+ballSize > balle.y-ballSize && this.y-ballSize < balle.y+ballSize){
    //         double tempDX = this.dx - (this.dx/Math.abs(this.dx))*IMPACT_FRICTION;
    //         double tempDY = this.dy - (this.dy/Math.abs(this.dy))*IMPACT_FRICTION;
    //         this.dx = balle.dx - (balle.dx/Math.abs(balle.dx))*IMPACT_FRICTION;
    //         this.dy = balle.dy - (balle.dy/Math.abs(balle.dy))*IMPACT_FRICTION;
    //         balle.dx = tempDX;
    //         balle.dy = tempDY;

    //         x += (this.x-balle.x)/2;
    //         y += (this.y-balle.y)/2;
    //         balle.setX(balle.x + (balle.x-this.x));
    //         balle.setY(balle.y + (balle.y-this.y));
    //         return true;
    //     }
    //     return false;
    // }

    // public void step(ball[] balls, int n) {
    //     for (int j = n+1; j < balls.length; j++){
    //         if(this.ballColision(balls[j])) break; 
    //     }
    //     if (x < -XSCALE + ballSize) {
    //         x = -XSCALE + ballSize;
    //         dx = -dx;
    //         if(Math.abs(dx)-IMPACT_FRICTION > 0) dx -= IMPACT_FRICTION;
    //         else dx = dx/2;
    //     }
    //     else if (x > XSCALE - ballSize) {
    //         x = XSCALE - ballSize;
    //         dx = -dx;
    //         if(Math.abs(dx)-IMPACT_FRICTION > 0) dx += IMPACT_FRICTION;
    //         else dx = dx/2;
    //     }

    //     if (y < -YSCALE + ballSize) {
    //         y = -YSCALE + ballSize;
    //         dy = -dy;
    //         if(Math.abs(dy) - IMPACT_FRICTION > 0) dy -= IMPACT_FRICTION;
    //         else dy = 0;
    //     }
    //     else if (y > YSCALE - ballSize) {
    //         y = YSCALE - ballSize;
    //         dy = -dy;
    //     }

    //     if(y > -YSCALE && dy != 0) dy -= GRAVITY;

    //     if(dy == 0 && Math.abs(dx) - FRICTION > 0)
    //         if(dx>0) dx -= FRICTION;
    //         else dx += FRICTION;
    //     else if(dy == 0 && Math.abs(dx) - FRICTION < 0)
    //         dx = 0;

    //     if(x > -XSCALE || x < XSCALE) x += dx;
    //     if(y > -YSCALE || y < YSCALE) y += dy;

    //     if(Double.isNaN(x)){
    //         x = 0.0;
    //     } 
    //     if(Double.isNaN(y)){
    //         y = 0.0;
    //     } 
    // }
}