insert into user (created_date, last_modified_date, email, password, name, provider, user_image_url, entrance_year, graduation_year)
values (now(), now(), 'admin@email.com', '1234', 'admin', 'KAKAO', '/img', now(), now()),
       (now(), now(), 'user1@email.com', '1234', '홍길동', 'KAKAO', '/img', now(), now()),
       (now(), now(), 'user2@email.com', '1234', '전우치', 'KAKAO', '/img', now(), now());

insert into question (target_user_id, writer_user_id, title, content, status, fix, view, created_date, last_modified_date)
values (1, 2, 'spring', 'spring 공부 루트', false, false, 0, now(), now());

insert into comment (question_id, writer_user_id, content, parent_comment_id, created_date, last_modified_date)
values (1, 2, 'parent 댓글 1', null, now(), now()),
       (1, 2, 'parent 댓글 2', null, now(), now()),
       (1, 2, 'parent 댓글 1 대댓글 1', 1, now(), now()),
       (1, 2, 'parent 댓글 1 대댓글 2', 1, now(), now()),
       (1, 2, 'parent 댓글 2 대댓글 1', 2, now(), now()),
       (1, 2, 'parent 댓글 2 대댓글 2', 2, now(), now());

insert into blog (user_id, profile_image_url, content, created_date, last_modified_date)
values (1, '/img', 'admin blog', now(), now());

insert into posts (created_date, last_modified_date, content, title, view, blog_id, category_id)
values (now(), now(), 'posts content 1', 'posts title 1', 0, 1, null),
       (now(), now(), 'posts content 2', 'posts title 2', 0, 1, null);