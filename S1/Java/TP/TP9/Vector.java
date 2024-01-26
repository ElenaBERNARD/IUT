public class Vector {
    public double l;
    public double d;

    public Vector(){
        this.l = 0;
        this.d = 0;
    }

    public Vector(double l, double d){
        this.l = l;
        this.d = d;
    }

    public Vector(Vector v){
        this.l = v.l;
        this.d = v.d;
    }

    public String toString(){
        return "(" + this.l + ", " + this.d + ")";
    }
}