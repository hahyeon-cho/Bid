package com.kcs3.bid.entity;

import com.kcs3.bid.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String userNickname;

    @Column(nullable = false, length = 255, unique = true)  // 이메일 필드 추가
    private String userEmail;  // 이메일

    @Column(nullable = false, columnDefinition = "int default 0")
    private int userPoint;
}
