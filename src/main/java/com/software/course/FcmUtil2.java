package com.software.course;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FcmUtil2 {
	public void send_FCM(String tokenId, String title, String content) { 
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("C:\\Users\\jsbae\\eclipse-workspace\\SoftwareEngineering\\src\\main\\resources\\calendarmap-8046b-firebase-adminsdk-v65hx-9f0f03436f.json").getInputStream())
				.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
				googleCredentials.refreshIfExpired();
				googleCredentials.getAccessToken().getTokenValue();
				
				

	String registrationToken = tokenId;

	Message msg = Message.builder()
		.setAndroidConfig(AndroidConfig.builder()
			.setTtl(3600*1000)
			.setPriority(AndroidConfig.Priority.NORMAL)
			.setNotification(AndroidNotification.builder()
			.setTitle(title)
			.setBody(content)
			.setIcon("stock_ticker_update")
			.setColor("#f45342")
			.build())
			.build())
		.setToken(registrationToken)
		.build();

	String response = FirebaseMessaging.getInstance().send(msg);
	System.out.println("Success "+ response);
	}
}
		