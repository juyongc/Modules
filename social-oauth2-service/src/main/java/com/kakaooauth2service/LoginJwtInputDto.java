package com.kakaooauth2service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginJwtInputDto {

    private Long id;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;
}
