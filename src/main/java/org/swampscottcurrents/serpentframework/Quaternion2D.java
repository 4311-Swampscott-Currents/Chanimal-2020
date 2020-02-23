package org.swampscottcurrents.serpentframework;

public class Quaternion2D
{
    public double x;
    public double y;

    public Quaternion2D() {}
    public Quaternion2D(double xPos, double yPos) {
        x = xPos;
        y = yPos;
    }

    public static Quaternion2D fromAxis(double x, double y) {
        double length = Math.sqrt(x*x + y*y);
        x /= length;
        y /= length;
        return new Quaternion2D(x, y);
    }

    public static Quaternion2D fromEuler(double angle) {
        angle = angle * (Math.PI / 180);
        return new Quaternion2D(Math.cos(angle), Math.sin(angle));
    }

    public static Quaternion2D slerp(Quaternion2D a, Quaternion2D b, double t) {
        double cosDot = Math.acos(dot(a, b));
        double aCoeff = Math.sin((1 - t) * cosDot) / Math.sin(cosDot);
        double bCoeff = Math.sin(t * cosDot) / Math.sin(cosDot);
        return new Quaternion2D((aCoeff * a.x) + (bCoeff * b.x), (aCoeff * a.y) + (bCoeff * b.y));
    }

    public static double dot(Quaternion2D a, Quaternion2D b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    public double toEuler() {
        return Math.atan2(y, x) * (180 / Math.PI);
    }

    public double toRadian() {
        return Math.atan2(y, x);
    }

    public void normalize() {
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        x /= distance;
        y /= distance;
    }

    public static Quaternion2D add(Quaternion2D a, Quaternion2D b) {
        return multiply(a, b);
    }

    public static Quaternion2D subtract(Quaternion2D a, Quaternion2D b) {
        return new Quaternion2D((a.x * b.x) + (a.y * b.y), (a.y * b.x) - (a.x * b.y));
    }

    public static Quaternion2D multiply(Quaternion2D a, Quaternion2D b) {
        return new Quaternion2D((a.x * b.x) - (a.y * b.y), (a.y * b.x) + (a.x * b.y));
    }

    public void invert() {
        y = -y;
    }
}