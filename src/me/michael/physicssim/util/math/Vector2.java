package me.michael.physicssim.util.math;

public class Vector2 {

    public double x;
    public double y;

    public Vector2() {
        this.x = 0D;
        this.y = 0D;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vector2 subtract(Vector2 vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public Vector2 multiply(Vector2 vec) {
        x *= vec.x;
        y *= vec.y;
        return this;
    }

    public Vector2 multiply(double m) {
        x *= m;
        y *= m;
        return this;
    }

    public Vector2 divide(Vector2 vec) {
        x /= vec.x;
        y /= vec.y;
        return this;
    }

    public Vector2 copy(Vector2 vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public Vector2 normalize() {
        double length = length();
        x /= length;
        y /= length;
        return this;
    }

    public Vector2 clone() {
        return new Vector2(x, y);
    }
}
