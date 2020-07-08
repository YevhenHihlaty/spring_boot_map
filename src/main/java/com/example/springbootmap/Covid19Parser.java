package com.example.springbootmap;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class Covid19Parser {
    private static final String COVID_CONFIRMED_URL =
"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";    @EventListener(ApplicationReadyEvent.class)
    public List<Point> getCovidData() throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newHttpClient();
//        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(COVID_CONFIRMED_URL)).build();
//        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.body());

        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(COVID_CONFIRMED_URL, String.class);
        StringReader stringReader = new StringReader(values);
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        List <Point> result = new ArrayList<>();

        SimpleDateFormat formatter= new SimpleDateFormat("M/d/yy");
        Date date = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));


        for (CSVRecord line: parse){
            double lat = Double.parseDouble(line.get("Lat"));
            double lon = Double.parseDouble(line.get("Long"));
            String text= line.get(String.valueOf(formatter.format(date)));

            result.add(new Point(lat, lon, text));
        }

        return result;

    }
}
