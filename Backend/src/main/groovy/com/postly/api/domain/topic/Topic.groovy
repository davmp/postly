package com.postly.api.domain.topic

import com.postly.api.domain.member.Member
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Table(name = "topic")
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Topic {
    @Id
    @GeneratedValue
    UUID id
    String title
    String content

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member

    @Column(name = "created_at")
    Date createdAt

    @Column(name = "updated_at")
    Date updatedAt
}
