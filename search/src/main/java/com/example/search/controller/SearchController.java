package com.example.search.controller;

import com.example.search.dto.GeneralResponse;
import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController

public class SearchController {


    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @LoadBalanced
    @GetMapping("/weather/search")
    public ResponseEntity<?> getDetails() {
        //TODO
        return new ResponseEntity<>("this is search service", HttpStatus.OK);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search/port")
    public ResponseEntity<String> getDetailsPort() {
        // Using the service name registered in Eureka instead of hardcoding the URL
        String url = "http://tarun.ht.home:9400//details/port";  // Use service name registered in Eureka
        System.out.println(url);
        // Call the details service with load balancing
        String response = restTemplate.getForObject(url, String.class);

        return new ResponseEntity<>(response, HttpStatus.OK);  // Return the response from details service
    }


    @GetMapping("/fetchAll")
    public CompletableFuture<ResponseEntity<GeneralResponse>> fetchAll() {
        return searchService.fetchAllDetails()
                .thenApply(response -> ResponseEntity.status(response.getCode()).body(response));
    }

}



