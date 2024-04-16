package lab2;

public class Walec {
    private double radius;
    private double height;

    public Walec() {
    }

    public Walec(double r, double h) {
        this.radius = r;
        this.height = h;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double r) {
        this.radius = r;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double h) {
        this.height = h;
    }

    public double baseArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }

    public double sideArea() {
        return 2 * Math.PI * this.radius * this.height;
    }

    public double totalArea() {
        return this.baseArea() + this.sideArea();
    }

    public double volume() {
        return this.baseArea() * this.height;
    }
}
