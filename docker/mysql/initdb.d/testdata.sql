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
    "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
    true,
    "2020-07-07T01:01:01",
    "2020-07-07T01:01:01",
    "USER"
    ),
    (
    "hanako777",
    "Hanako",
    "Yamada",
    "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
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
),
(
    "gjjgkajghaljkgdja",
    "Go to my University from morning",
    "Final Exam starts from 8:00 A.M.",
    NULL,
    true,
    "2020-08-24T10:00:00",
    "2020-08-24T10:00:00",
    NULL,
    "john777"
);