package com.example.search.service;

import com.example.search.dto.GeneralResponse;
import com.example.search.model.Student;
import com.example.search.model.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final RestTemplate restTemplate;

    //@Value("${details.service.url}") // Inject the URL from properties
    //private String detailsServiceUrl;

    private final ExecutorService executor = Executors.newFixedThreadPool(3); // Limit concurrent requests

    public SearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Call to teacher service
    public CompletableFuture<List<Teacher>> callTeacherService() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<List<Teacher>> response = restTemplate.exchange(
                        "http://student-teacher/teachers", // Service URL (Ensure service discovery is working)
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Teacher>>() {}
                );
                return response.getBody();
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("Error fetching teachers: " + e.getMessage());
            }
        }, executor);
    }

    // Call to student service
    public CompletableFuture<List<Student>> callStudentService() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Get the response as a String
                String response = restTemplate.getForObject("http://student-teacher/students", String.class);

                // Use ObjectMapper to convert the String response into a List of Student objects
                ObjectMapper objectMapper = new ObjectMapper();
                List<Student> students = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));

                return students;
            } catch (Exception e) {
                throw new RuntimeException("Error fetching students: " + e.getMessage(), e);
            }
        }, executor);
    }

    // Call to details service (to fetch port)
//    public CompletableFuture<String> callDetailsService() {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                // Perform the REST call
//                ResponseEntity<String> response = restTemplate.exchange(
//                        detailsServiceUrl + "/port", HttpMethod.GET, null, String.class
//                );
//
//                // Check if response is valid
//                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                    String responseBody = response.getBody();
//
//                    // If response is a plain string (e.g., "8080"), return it directly
//                    if (responseBody.matches("\\d+")) {
//                        return responseBody;
//                    }
//
//                    // If response is a JSON object, parse it
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
//
//                    return responseMap.getOrDefault("port", "Unknown Port").toString();
//                }
//                return "Unknown Port";
//            } catch (HttpClientErrorException e) {
//                return "Error fetching port: HTTP error " + e.getStatusCode();
//            } catch (Exception e) {
//                return "Error fetching port: " + e.getMessage();
//            }
//        }, executor);
//    }
    public CompletableFuture<String> callDetailsService() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String fullUrl = "http://localhost:9400/details/port"; // Updated URL
                System.out.println("Calling: " + fullUrl); // Debugging log

                ResponseEntity<String> response = restTemplate.exchange(
                        fullUrl, HttpMethod.GET, null, String.class
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return response.getBody(); // Directly return the response as a string
                }
                return "Unknown Port";
            } catch (HttpClientErrorException e) {
                return "Error fetching port: HTTP error " + e.getStatusCode();
            } catch (Exception e) {
                return "Error fetching port: " + e.getMessage();
            }
        }, executor);
    }


    // Fetch all details (Teachers, Students, Port)
    public CompletableFuture<GeneralResponse> fetchAllDetails() {
        CompletableFuture<List<Teacher>> teacherFuture = callTeacherService();
        CompletableFuture<List<Student>> studentFuture = callStudentService();
        CompletableFuture<String> detailsPortFuture = callDetailsService(); // Fetch details/port

        return CompletableFuture.allOf(teacherFuture, studentFuture, detailsPortFuture)
                .thenApply(v -> {
                    try {
                        // Fetch all data asynchronously
                        List<Teacher> teacherData = teacherFuture.get();
                        List<Student> studentData = studentFuture.get();
                        String detailsPort = detailsPortFuture.get();

                        // Process teachers' names into a list
                        List<String> teachers = teacherData.stream()
                                .map(Teacher::getName)
                                .collect(Collectors.toList());

                        // Prepare the response data
                        Map<String, Object> responseData = new HashMap<>();
                        responseData.put("Teachers", teachers);
                        responseData.put("Students", studentData);
                        responseData.put("port", detailsPort);

                        // Create the final response structure
                        Map<String, Object> detailsResponse = new HashMap<>();
                        detailsResponse.put("details", responseData);

                        return new GeneralResponse(200, System.currentTimeMillis(), detailsResponse);
                    } catch (Exception e) {
                        // Handle errors and return a structured error response
                        Map<String, Object> errorMap = new HashMap<>();
                        errorMap.put("error", "Error fetching details: " + e.getMessage());

                        return new GeneralResponse(500, System.currentTimeMillis(), errorMap);
                    }
                });
    }
}
