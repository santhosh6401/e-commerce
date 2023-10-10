/*
package com.ecommerce.clothing.appearance.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        Resource resource = new ClassPathResource("config/firebase-key.json");

        try {
            FileInputStream firebaseCredentials = new FileInputStream(resource.getFile());

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(firebaseCredentials))
                    .setStorageBucket("the-clothing-company.appspot.com")
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            log.error("FILE Upload exception : [{}]", ex.getMessage());
            throw ex;
        }
    }


    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }
}
*/
