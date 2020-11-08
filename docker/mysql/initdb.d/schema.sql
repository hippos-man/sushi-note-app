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

CREATE TABLE todos (
    todo_id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    deadline_date DATETIME,
    is_completed BOOLEAN NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL,
    user_id VARCHAR(50) NOT NULL
);

CREATE TABLE labels (
    label_id VARCHAR(50) PRIMARY KEY,
    label_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL,
    user_id VARCHAR(50) NOT NULL
);

CREATE TABLE todo_label (
    todo_id VARCHAR(50) NOT NULL,
    label_id VARCHAR(50) NOT NULL,
    created_date_time DATETIME NOT NULL,
    updated_date_time DATETIME NOT NULL,
    PRIMARY KEY (todo_id, label_id)
);
