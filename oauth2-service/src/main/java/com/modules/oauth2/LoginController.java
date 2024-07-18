package com.modules.oauth2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @PostMapping("/token/check")
    public ResponseEntity<String> checkToken(@RequestHeader("Authorization") String authorizationHeader) {

        log.info("authorizationHeader = " + authorizationHeader);
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            log.info("Received token for UID: " + uid);
            return new ResponseEntity<>("Token is valid for UID: " + uid, HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

}
