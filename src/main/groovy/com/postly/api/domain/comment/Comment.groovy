package com.postly.api.domain.comment

import com.postly.api.domain.member.Member
import com.postly.api.domain.topic.Topic
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Table(name = "comment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Comment {
    @Id
    @GeneratedValue
    private UUID id
    private String content

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user

    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic

    @Column(name = "created_at")
    private Date createdAt

    @Column(name = "updated_at")
    private Date updatedAt
}
