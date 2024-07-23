package com.modules.oauth2.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        String serviceAccountPath = "oauth2-service/src/main/resources/firebase-service-account.json";

        // FileInputStream을 try-with-resources로 사용하여 자동으로 닫히도록 함
        try (FileInputStream serviceAccount = new FileInputStream(serviceAccountPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            // 이미 초기화된 앱이 있는지 확인하고, 없으면 초기화
            List<FirebaseApp> apps = FirebaseApp.getApps();
            if (apps != null && !apps.isEmpty()) {
                for (FirebaseApp app : apps) {
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                        return app;
                    }
                }
            }

            return FirebaseApp.initializeApp(options);
        }
    }

}
