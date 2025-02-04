package com.postly.api.domain.topic


import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Table(name = "topic_media")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class TopicMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id

    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic
    
    String url
}
