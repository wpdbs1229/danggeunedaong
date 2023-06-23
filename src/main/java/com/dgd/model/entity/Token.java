package com.dgd.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    private String token;
    private Long userId;
}
