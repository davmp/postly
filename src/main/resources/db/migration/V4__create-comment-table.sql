CREATE TABLE comment (
    id SERIAL,
    content VARCHAR(255) NOT NULL,
    member_id UUID,
    topic_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE,
    PRIMARY KEY(id, member_id, topic_id)
);