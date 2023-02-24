CREATE TABLE IF NOT EXISTS request_reply_audit (
    id SERIAL PRIMARY KEY,
    message varchar(36) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);