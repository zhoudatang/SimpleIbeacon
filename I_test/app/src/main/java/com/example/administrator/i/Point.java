package com.example.administrator.i;

/**
 * 绘制点的实体对象
 * Created by zhongzilu on 2018/11/27.
 */

public class Point {

    //点的X坐标
    private int x;

    //点的Y坐标
    private int y;

    //点的半径，默认为10像素
    private int radius = 20;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
