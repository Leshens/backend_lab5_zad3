package com.example.demo;

import com.example.demo.business.PointService;
import com.example.demo.data.Point;
import com.example.demo.web.PointController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointController.class)
public class PointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PointService pointService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getListOfPointsShouldReturnListOfPoints() throws Exception {
        when(pointService.getPoints()).thenReturn(List.of(
                new Point(1,2),
                new Point(4,7))
        );

        mvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"x\":1,\"y\":2},{\"x\":4,\"y\":7}]"));
    }

    @Test
    public void getListOfPointsToShouldReturnOnePoint() throws Exception {
        final int number_to_test = 1;
        when(pointService.getPoints(number_to_test)).thenReturn(List.of(
                new Point(1,2))
        );

        mvc.perform(get("/{num}", number_to_test)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"x\":1,\"y\":2}]"));
    }

    @Test
    public void getDistanceShouldReturnOneResult() throws Exception {
        final int x1 = 2;
        final int y1 = 3;
        final int x2 = 4;
        final int y2 = 5;
        final double expected_distance = 2.8284271247461903;
        when(pointService.getDistance(new Point(x1,y1), new Point(x2,y2))).thenReturn(expected_distance);

        mvc.perform(get("/distance/{x1}/{y1}/{x2}/{y2}", x1, y1, x2, y2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expected_distance)));
    }

    @Test
    public void addPointShouldReturnOneResult() throws Exception {
        final Point test_point = new Point(1,1);
        when(pointService.addPoint(test_point)).thenReturn(true);

        mvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(test_point)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deletePointShouldReturnPoint() throws Exception {
        final int number_to_test = 0;
        when(pointService.deletePoint(number_to_test)).thenReturn(new Point(1,1));

        mvc.perform(delete("/{id}",0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"x\":1,\"y\":1}"));
    }
}