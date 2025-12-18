package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller to verify server is running and accessible.
 * This endpoint is publicly available without authentication.
 */
@RestController
@RequestMapping("/public")
public class TestController {
    
    /**
     * Test endpoint to verify server is running
     * @return A success message if the server is accessible
     */
    @GetMapping("/test")
    public String test() {
        return "✅ Server is running and accessible! Current time: " + java.time.LocalDateTime.now();
    }
}