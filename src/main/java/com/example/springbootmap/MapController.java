package com.example.springbootmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String getMap (Model model, @RequestParam String x, @RequestParam String y){
//        model.addAttribute("x", x);
//        model.addAttribute("y", y);
//        return "map";
//    }

    private Covid19Parser covid19Parser;


    public MapController(Covid19Parser covid19Parser) {
        this.covid19Parser = covid19Parser;
    }

    @GetMapping
    public String getMap (Model model) throws IOException, InterruptedException {
//        List<Point> pointList = new ArrayList<>();
//        pointList.add(new Point(51.7687323, 19.4569911, "Wykryte przypadki 1"));
//        pointList.add(new Point(50.2598987, 19.0215852, "Wykryte przypadki 2"));
        model.addAttribute("points", covid19Parser.getCovidData());
        return "map";
    }
}
