INSERT INTO users (user_id, display_name, email_address, password, document_id, is_active, created_date_time,
    updated_date_time, role_name)
VALUES
    ( "john777", "John", "happy@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        NULL, true, "2020-07-07T01:01:01", "2020-07-07T01:01:01", "USER"),
    ( "hanako777", "Hana", "hana@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        NULL, true, "2020-07-07T01:01:01", "2020-07-07T01:01:01", "USER"),
    ( "admin", "Admin", "admin@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        NULL, true, "2020-07-07T01:01:01", "2020-07-07T01:01:01","ADMIN");

INSERT INTO topics ( topic_id, user_id, topic_name, is_active, created_date_time, updated_date_time)
VALUES ( "gourmet", "admin", "Gourmet", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00"),
       ( "sports", "admin", "Sports", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00"),
       ( "music", "admin", "Music", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00"),
       ( "technology", "admin", "Technology", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00"),
       ( "career", "admin", "Career", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00"),
       ( "travel", "admin", "Travel", true, "2020-11-01T12:00:00", "2020-11-01T12:00:00");


INSERT INTO articles ( article_id, user_id, topic_id, title, text_body, summary, document_id,
        created_date_time, updated_date_time)
VALUES ("jfalhgakjdalh",
        "john777",
        "gourmet",
        "Is Yoshinoya's Beef bowl better in Japan ?",
        "Hello, folks. My name is John who is office worker in Singapore. I heard that Yoshinoya in Singapore is not as good as the one in Japan. If it's true, I want to try it !!. Does anyone know about it??",
        "Hello, folks. My name is John who is office worker in Singapore. I heard that Yoshinoya in Singapore is not as good as the one in Japan. If it's true",
        NULL,
        "2020-11-07T12:01:00",
        "2020-11-07T12:01:00"
        ),
        ("fjalghaldj37fajl",
        "hanako777",
        "sports",
        "Football player Wanted!! location: Harbour Front",
        "Hello there! My name is Hana who is freelance worker in Singapore. I'm football lover!! So, I'm planning to hold football match in the footsal coart nearHarbour Front MRT. If you are keen to join me, let me know yeah!! Thank you!",
        "Hello there! My name is Hana who is freelance worker in Singapore. I'm football lover!! So, I'm planning to hold football match in the footsal coart near",
        NULL,
        "2020-11-10T08:00:30",
        "2020-11-10T09:00:30"
        ),
        ("3938dfjagjai",
        "hanako777",
        "career",
        "Is it difficult for foreigners to get  Japanese speaking job in Japan ?",
        "Hello, my friends. My name is Hana. I'm currently planning to go to Japan to start career as a Japanese speaker. Does anyone know about easier way to do that? Please comment here thanks.",
        "Hello, my friends. My name is Hana. I'm currently planning to go to Japan to start career as a Japanese speaker. Does anyone know about",
        NULL,
        "2020-11-09T12:01:00",
        "2020-11-09T15:00:00"
        ),
        ("jghalhdl8dhak",
        "john777",
        "music",
        "I can't remember the name of the song",
        "Hello, my friends. My name is John. 38 years old. I'm British. Does any one know the name of song that Life is beautiful Wow Wow  Only Lonely ",
        "Hello, my friends. My name is John. 38 years old. I'm British. Does any one know the name of song that Life is ",
        NULL,
        "2020-11-11T10:01:00",
        "2020-11-11T10:01:00"
        ),
        ("7874hdjjfhg",
        "hanako777",
        "music",
        "I have no experience in Coding. Is it difficult to get a job as a web developer?",
        "Hi there! I'm Hana from Malaysia. I was wondering if people with no experience can get a developer job. I'm very interested in that job!",
        "Hi there! I'm Hana from Malaysia. I was wondering if people with no experience can get",
        NULL,
        "2020-11-11T10:01:00",
        "2020-11-11T10:01:00"
        );

INSERT INTO comments (comment_id, article_id, user_id, text_body, created_date_time, updated_date_time)
VALUES
    ("jghaewai8e7whfaf", "jfalhgakjdalh", "hanako777", "Hi John, I'm Hana. I think it's slightly different.
    Yoshinoya in Japan serves better white rice which is delicious! You better to try!", "2020-11-15T12:01:00", "2020-11-15T12:01:00"),
    ("vasejoghaohgaogd", "jfalhgakjdalh", "hanako777", "On second thought,it may be same. Sorry.", "2020-11-15T13:01:00", "2020-11-15T13:01:00"),
    ("24jlgaklh4hlgafg", "jfalhgakjdalh", "hanako777", "Additionally, Yoshinoya in Japan serve more kind of meals such as selfish hotpot, pork rice bowl and so on. I love pork jinger rice bowl.", "2020-11-15T14:01:00", "2020-11-15T14:01:00");

INSERT INTO likes (user_id, article_id, created_date_time)
VALUES
    ("hanako777", "jfalhgakjdalh", "2020-12-15T12:01:00");