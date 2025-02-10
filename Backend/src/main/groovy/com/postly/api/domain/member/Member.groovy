package com.postly.api.domain.member


import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table(name = "member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    UUID id
    String username

    @Column(name = "password", nullable = false, length = 60)
    String password

    String role

    @Column(name = "profile_picture_url")
    String profilePictureUrl

    @Column(name = "created_at")
    Date createdAt

    @Column(name = "updated_at")
    Date updatedAt

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == MemberRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority(MemberRole.ADMIN.get()),
                    new SimpleGrantedAuthority(MemberRole.USER.get())
            )
        }
        return List.of(
                new SimpleGrantedAuthority(MemberRole.USER.get())
        )
    }
}
