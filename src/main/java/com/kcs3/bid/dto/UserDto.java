package com.kcs3.bid.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long userId;
    private String nickname;
    private String email;
}
