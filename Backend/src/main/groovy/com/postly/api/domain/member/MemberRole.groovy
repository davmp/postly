package com.postly.api.domain.member

enum MemberRole {
    ADMIN("adm"),
    USER("usr")

    private final String role

    private MemberRole(String role) {
        this.role = role
    }

    String get() {
        return this.role
    }
}