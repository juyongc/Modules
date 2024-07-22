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

        List<FirebaseApp> apps = FirebaseApp.getApps();
        if (apps != null && !apps.isEmpty()) {
            for (FirebaseApp app : apps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    return app;
                }
            }
        }

        FileInputStream serviceAccount = new FileInputStream("oauth2-service/src/main/resources/firebase-service-account.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        return FirebaseApp.initializeApp(options);
    }

}
