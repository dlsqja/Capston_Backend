package com.HELPT.Backend.domain.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {

    public void sendMessageTo(FcmSendDto fcmSendDto) throws IOException {

        String message = makeMessage(fcmSendDto);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity entity = new HttpEntity<>(message, headers);

        String API_URL = "https://fcm.googleapis.com/v1/projects/helpt-431a3/messages:send";
        try {
            restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            log.error("[-] FCM 전송 오류 :: " + e.getMessage());
            log.error("[-] 오류 발생 토큰 :: [" + fcmSendDto.getToken() + "]");
            log.error("[-] 오류 발생 메시지 :: [" + fcmSendDto.getBody() + "]");
        }
    }

//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }

//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        // Gson 세팅
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
//                .setLenient().create();
//
//        JsonReader reader = new JsonReader(new InputStreamReader(new ClassPathResource(firebaseConfigPath).getInputStream()));
//        reader.setLenient(true);
//
//        // Json파싱
//        GoogleCredentials googleCredentials = gson.fromJson(reader, GoogleCredentials.class);
//        googleCredentials = googleCredentials.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
//
//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        // GSON 설정
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        JsonReader reader = new JsonReader(new InputStreamReader(new ClassPathResource(firebaseConfigPath).getInputStream()));
//        reader.setLenient(true);
//
//        // Json 파싱
//        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        // GoogleCredentials 객체를 생성하여 액세스 토큰을 가져옵니다.
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }


    private String makeMessage(FcmSendDto fcmSendDto){

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(fcmSendDto.getToken())
                        .notification(FcmMessageDto.Notification.builder()
                                .title(fcmSendDto.getTitle())
                                .body(fcmSendDto.getBody())
                                .image(null)
                                .build()
                        ).build())
                .validateOnly(false)
                .build();

        try {
            return om.writeValueAsString(fcmMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    private FcmMessageDto makeFmd(FcmSendDto fcmSendDto) {
//
//        return FcmMessageDto.builder()
//                .message(FcmMessageDto.Message.builder()
//                        .token(fcmSendDto.getToken())
//                        .notification(FcmMessageDto.Notification.builder()
//                                .body(fcmSendDto.getBody())
//                                .title(fcmSendDto.getTitle())
//                                //.image(null)
//                                .build()
//                        ).build())
//                //.validateOnly(false)
//                .build();
//    }
}