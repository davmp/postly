CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE topic (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(10) NOT NULL,
    content VARCHAR(500) NOT NULL,
    member_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);