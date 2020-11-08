CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    display_name VARCHAR (50),
    email_address VARCHAR (255),
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE articles (
    article_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    topic_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    text_body VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL,
);

CREATE TABLE topics (
    topic_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    topic_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL
);
