package com.example.demo.web;

import com.example.demo.business.PointService;
import com.example.demo.data.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PointController {
    private final PointService services;

    @Autowired
    public PointController(PointService services) {
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<?> getListOfPoints(){
        return ResponseEntity.ok(services.getPoints());
    }

    @GetMapping("/{num}")
    public ResponseEntity<?> getListOfPointsTo(@PathVariable("num")  int num){
        return ResponseEntity.ok(services.getPoints(num));
    }

    @GetMapping("/distance/{x1}/{y1}/{x2}/{y2}")
    public ResponseEntity<?> getDistance(@PathVariable("x1") int x1, @PathVariable("y1") int y1 , @PathVariable("x2") int x2, @PathVariable("y2") int y2){
        return ResponseEntity.ok(services.getDistance(new Point(x1,y1), new Point(x2,y2)));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPoint(@RequestBody Point point){
        return ResponseEntity.ok(services.addPoint(point));
    }

    @PostMapping("/add/{x}/{y}")
    public ResponseEntity<?> addPoint(@PathVariable("x") int x, @PathVariable("y") int y){
        return ResponseEntity.ok(services.addPoint(new Point(x,y)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable("id") int id){
        return ResponseEntity.ok(services.deletePoint(id));
    }
}



