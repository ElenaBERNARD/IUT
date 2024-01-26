public class Vec2 {
    public double x;
    public double y;

    public Vec2(){
        this.x = 0;
        this.y = 0;
    }
    
    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x +", " + this.y + ")";
    }

    public Vec2 add(Vec2 v){
        return new Vec2(this.x+v.x, this.y+v.y);
    }

    public Vec2 subtract(Vec2 v){
        return new Vec2(this.x-v.x, this.y-v.y);
    }

    public Vec2 multiply(double a){
        return new Vec2(this.x*a, this.y*a);
    }

    public Vec2 devided(double a){
        return new Vec2(this.x/a, this.y/a);
    }

    public double distance(Vec2 v){
        return (v.x - this.x)/2.0 + (v.y - this.y)/2.0;
    }
}
