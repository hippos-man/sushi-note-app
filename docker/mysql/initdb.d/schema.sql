CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    display_name VARCHAR (50) NOT NULL,
    email_address VARCHAR (255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    document_id BIGINT(20),
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
    text_body TEXT NOT NULL,
    summary TEXT NOT NULL,
    document_id BIGINT(20),
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL
);

CREATE TABLE topics (
    topic_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    topic_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL
);

CREATE TABLE comments (
    comment_id  VARCHAR(50) PRIMARY KEY,
    article_id VARCHAR (50) NOT NULL,
    user_id VARCHAR (50) NOT NULL,
    text_body TEXT NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL
);

CREATE TABLE documents (
    document_id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    content LONGBLOB NOT NULL,
    original_name VARCHAR (255) NOT NULL,
    file_path VARCHAR (255) UNIQUE NOT NULL,
    file_size BIGINT (20) NOT NULL,
    user_id VARCHAR (50) NOT NULL,
    created_date_time DATETIME NOT NULL
);