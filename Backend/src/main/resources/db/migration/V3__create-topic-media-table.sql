CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE topic_media (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    topic_id UUID NOT NULL,
    url VARCHAR(255),
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE
);