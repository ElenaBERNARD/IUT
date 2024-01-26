public class Ball {
    public Vec2 position_current; 
    public Vec2 position_old;
    public Vec2 acceleration;

    public Ball(){
        this.position_current = new Vec2();
        this.position_old = new Vec2();
        this.acceleration = new Vec2();
    }

    public double getX(){
        return this.position_current.x;
    }

    public double getY(){
        return this.position_current.y;
    }

    public void updatePosition(double dt){
        final Vec2 velocity = position_current.subtract(position_old);
        position_old = position_current;

        //Verlet integration
        position_current = position_current.add(velocity.add(acceleration.multiply(dt*dt)));

        //Reset acceleration
        acceleration = new Vec2();
        
    }

    public void accelerate(Vec2 acc){
        acceleration = acceleration.add(acc);
    }
}
