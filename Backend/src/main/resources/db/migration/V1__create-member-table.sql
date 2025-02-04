CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE member (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    profile_picture_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);