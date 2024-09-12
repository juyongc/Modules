package com.kakaooauth2service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenResponseDto {

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("expires_in")
    public Integer expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    public Integer refreshTokenExpiresIn;

    @Override
    public String toString() {
        return "KakaoTokenResponseDto{" +
            "tokenType='" + tokenType + '\'' +
            ", accessToken='" + accessToken + '\'' +
            ", expiresIn=" + expiresIn +
            ", refreshToken='" + refreshToken + '\'' +
            ", refreshTokenExpiresIn=" + refreshTokenExpiresIn +
            '}';
    }

}
