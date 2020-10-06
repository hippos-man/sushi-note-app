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
    user_id
)
VALUES
(
    "fdjaofjaojfd",
    "Go to Broken heads with my mates.",
    "A day off!! Wake up in the morning, and borrow the car from my friend. Let's go beach to surf",
    "2020-10-01T06:00:00",
    false,
    "2020-08-24T10:00:00",
    "2020-08-24T10:00:00",
    "john777"
),
(
    "gjjgkajghaljkgdja",
    "Go to my University from morning",
    "Final Exam starts from 8:00 A.M.",
    "2020-10-06T08:00:00",
    false,
    "2020-08-25T10:00:00",
    "2020-08-25T10:00:00",
    "john777"
),
(
    "jghjgdjaighfshggs",
    "Final Exam for this semester !",
    "Prepare well and be focus on the problem solving",
    "2020-10-24T09:00:00",
    false,
    "2020-10-01T09:00:00",
    "2020-10-01T09:00:00",
    "john777"
),
(
    "bnvdajkdjahgogiha",
    "Final Exam for this semester !",
    "Prepare well and be focus on the problem solving",
    "2020-10-24T09:00:00",
    false,
    "2020-10-01T09:00:00",
    "2020-10-01T09:00:00",
    "hanako777"
);