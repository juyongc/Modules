package com.kakaooauth2service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    @JsonProperty("id")
    private Long id;  // 회원번호

    @JsonProperty("kakao_account")
    private KakaoAccountDto kakaoAccount;  // 카카오 계정 정보

    public String getKakaoNickname() {
        return kakaoAccount.profile.getNickname();
    }
    public String getThumbnailImageUrl() {
        return kakaoAccount.profile.getThumbnailImageUrl();
    }
    public String getProfileImageUrl() {
        return kakaoAccount.profile.getProfileImageUrl();
    }


    @Override
    public String toString() {
        return "KakaoUserInfoResponseDto{" +
            "id=" + id +
            ", kakaoAccount=" + kakaoAccount +
            '}';
    }

    @Getter
    @NoArgsConstructor
    private static class KakaoAccountDto {

        private KakaoProfile profile;

        @Getter
        @NoArgsConstructor
        private static class KakaoProfile {

            private String nickname;
            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}
