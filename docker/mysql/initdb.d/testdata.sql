INSERT INTO users
    (
    user_id,
    first_name,
    last_name,
    password,
    is_active,
    created_date_time,
    updated_date_time,
    role_name
    )
VALUES
    (
    "john777",
    "John",
    "Smith",
    "password",
    true,
    "2020-07-07T01:01:01",
    "2020-07-07T01:01:01",
    "USER"
    );


INSERT INTO todos
(
    todo_id,
    title,
    description,
    deadline_date,
    is_completed,
    created_date_time,
    updated_date_time,
    label_id,
    user_id
)
VALUES
(
    "fdjaofjaojfd",
    "Go to the beach",
    "A day off!! Wake up in the morning, and borrow the car from my friend. Let's go beach",
    NULL,
    false,
    "2020-08-24T10:00:00",
    "2020-08-24T10:00:00",
    NULL,
    "john777"
);