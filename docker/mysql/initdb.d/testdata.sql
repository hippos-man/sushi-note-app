INSERT INTO users
    (
    user_id,
    first_name,
    last_name,
    email_address,
    password,
    is_active,
    created_date_time,
    updated_date_time
    )
VALUES
    (
    "john777",
    "John",
    "Smith",
    "hoge@gmail.com",
    "password",
    true,
    "2020-10-10-01:01:01",
    NULL
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
    "2020-08-24 10:00:00",
    NULL,
    NULL,
    "john777"
);