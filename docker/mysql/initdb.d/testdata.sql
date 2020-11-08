INSERT INTO users (user_id, display_name, email_address, password, is_active, created_date_time,
    updated_date_time,role_name)
VALUES
    ( "john777", "John", "happy@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        true, "2020-07-07T01:01:01", "2020-07-07T01:01:01", "USER"),
    ( "hanako777", "Hana", "hana@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        true, "2020-07-07T01:01:01", "2020-07-07T01:01:01", "USER"),
    ( "admin", "Admin", "admin@gmail.com", "$2a$10$h5PwB29NCTL5BI03z8rireo5ghNvx5l.6jeckPEQM2ibuplr0b0OO",
        true, "2020-07-07T01:01:01", "2020-07-07T01:01:01","ADMIN");


INSERT INTO articles ( article_id, user_id, topic_id, title, text_body, is_deleted,
        created_date_time, updated_date_time)
VALUES ("jfalhgakjdalh", "john777", "1", "Is Yoshinoya's Beef bowl better in Japan ?", "Hello, folks. My name is John who is office worker
            in Singapore. I heard that Yoshinoya in Singapore is not as good as the one in Japan. If it's true, I want to try it !!. Does anyone know
            about it??", false, "2020-11-07T12:01:00"),
        ("fjalghaldj37fajl", "hanako777", "1", "Football player Wanted!! location: Harbour Front", "Hello there! My name is Hana who is freelance worker
            in Singapore. I'm football lover!! So, I'm planning to hold football match in the footsal coart near
            Harbour Front MRT. If you are keen to join me, let me know yeah!! Thank you!", false, "2020-11-10T08:00:30"),
        ("3938dfjagjai", "hanako777", "2", "Is it difficult for foreigners to get  Japanese speaking job in Japan ?",
        "Hello, my friends. My name is Hana. I'm currently planning to go to Japan to start career as a Japanese speaker.
        Does anyone know about easier way to do that? Please comment here thanks.", false, "2020-11-09T12:01:00");

INSERT INTO topics ( topic_id, user_id, topic_name, is_active, created_date_time, updated_date_time)
VALUES ( "1", "admin", "Gourmet", true, "2020-11-00T12:00:00", "2020-11-00T12:00:00"),
       ( "2", "admin", "Sports", true, "2020-11-00T12:00:00", "2020-11-00T12:00:00"),
       ( "3", "admin", "Music", true, "2020-11-00T12:00:00", "2020-11-00T12:00:00"),
       ( "4", "admin", "Technology", true, "2020-11-00T12:00:00", "2020-11-00T12:00:00"),
       ( "5", "admin", "Career", true, "2020-11-00T12:00:00", "2020-11-00T12:00:00");