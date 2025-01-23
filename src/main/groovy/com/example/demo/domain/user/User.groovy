package com.example.demo.domain.user

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    @Id
    @GeneratedValue
    private UUID id
    private String username

    @Column(name = "profile_picture_url")
    private String profilePictureUrl

    @Column(name = "created_at")
    private Date createAt

    @Column(name = "updated_at")
    private Date updatedAt
}
