CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    password BINARY(60) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_date_time LOCALDATE NOT NULL,
    updated_date_time LOCALDATE,
    UNIQUE KEY email_address (email_address)
);



CREATE TABLE todos (
    todo_id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    deadline_date DATETIME,
    is_completed BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME,
    label_id VARCHAR(50),
    user_id VARCHAR(50) NOT NULL,

);

CREATE TABLE labels (
    label_id VARCHAR(50) PRIMARY KEY,
    label_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    label_name VARCHAR(50) NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME,
    user_id VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN NOT NULL
);