package com.postly.api.domain.member


import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Table(name = "member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    UUID id
    String username

    @Column(name = "profile_picture_url")
    String profilePictureUrl

    @Column(name = "created_at")
    Date createdAt

    @Column(name = "updated_at")
    Date updatedAt
}
