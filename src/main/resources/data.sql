insert into user (created_date,last_modified_date, email, password, name, provider, user_image_url, entrance_year, graduation_year)
values (now(), now(), 'admin@email.com', '1234', 'admin', 'KAKAO', '/img', now(), now()),
       (now(), now(), 'user1@email.com', '1234', '홍길동', 'KAKAO', '/img', now(), now()),
       (now(), now(), 'user2@email.com', '1234', '전우치', 'KAKAO', '/img', now(), now());