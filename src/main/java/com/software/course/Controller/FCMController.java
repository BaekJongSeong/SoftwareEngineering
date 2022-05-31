package com.software.course.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Service.FirebaseCloudMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FCMController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Value("${API.token}")
	private String token;
    
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage() throws IOException {
        
        firebaseCloudMessageService.sendMessageTo(
        		token,
                "sdfsdf",
                "sdfsdfsdf");
        return ResponseEntity.ok().build();
    }
}
