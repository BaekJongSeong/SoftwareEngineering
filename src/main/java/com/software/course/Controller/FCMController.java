package com.software.course.Controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Service.FirebaseCloudMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FCMController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage() throws IOException {
        
        firebaseCloudMessageService.sendMessageTo(
    			"dPAvh5R6Tjyo_bq0U7DRFz:APA91bH2ZEHxSje8irSYFP0z8D3WVQuDcdEGGbcOmH1UPZUIE-M7CmbbPlxVPs1CvDaMjuslgGgkzfkh8IHwezOxfKTaeheedDNhBVSWx6rMdHx_USv-rE8UD_2Ovdc7rzww5-glcTE3"
        		,
                "sdfsdf",
                "sdfsdfsdf");
        return ResponseEntity.ok().build();
    }
}
