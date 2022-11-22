package com.example.demo.business;

import com.example.demo.data.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointService {

    private List<Point> points = new ArrayList<>();

    public boolean addPoint(Point point) {
        return points.add(point);
    }

    public Point deletePoint(int i) {
        return  points.remove(i);
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> getPoints(int n) {
        return points.subList(0, n);
    }

    public double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }



}