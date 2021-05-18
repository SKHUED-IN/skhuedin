insert into user (created_date, last_modified_date, email, password, name, provider, user_image_url, entrance_year,
                  graduation_year, role)
values (now(), now(), 'admin@email.com', '1234', 'admin', 'KAKAO', '/img', now(), now(), 'ADMIN'),
       (now(), now(), 'user1@email.com', '1234', '홍길동', 'KAKAO', '/img', now(), now(), null),
       (now(), now(), 'user2@email.com', '1234', '전우치', 'KAKAO', '/img', now(), now(), null),
       (now(), now(), 'dev.hyeonic@gmail.com', '1234', 'hyeonic', 'KAKAO', '/img', now(), now(), 'ADMIN');

insert into question (target_user_id, writer_user_id, title, content, status, fix, view, created_date,
                      last_modified_date)
values (1, 2, 'spring 1', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 2', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 3', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 4', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 5', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 6', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 7', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 8', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 9', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 10', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 11', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 12', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 13', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 14', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 15', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 16', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 17', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 18', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 19', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 20', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 21', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 22', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 23', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 24', 'spring 공부 루트', false, false, 0, now(), now()),
       (1, 2, 'spring 25', 'spring 공부 루트', false, false, 0, now(), now());

insert into comment (question_id, writer_user_id, content, parent_comment_id, created_date, last_modified_date)
values (1, 2, 'parent 댓글 1', null, now(), now()),
       (1, 2, 'parent 댓글 2', null, now(), now()),
       (1, 2, 'parent 댓글 1 대댓글 1', 1, now(), now()),
       (1, 2, 'parent 댓글 1 대댓글 2', 1, now(), now()),
       (1, 2, 'parent 댓글 2 대댓글 1', 2, now(), now()),
       (1, 2, 'parent 댓글 2 대댓글 2', 2, now(), now());

insert into blog (user_id, profile_image_url, content, created_date, last_modified_date)
values (1, '/img', 'admin blog', now(), now()),
       (2, '/img', '홍길동 blog', now(), now()),
       (3, '/img', '전우치 blog', now(), now());

insert into posts (created_date, last_modified_date, content, title, view, blog_id, category_id)
values (now(), now(), 'posts content 1', 'posts title 1', 10, 1, null),
       (now(), now(), 'posts content 2', 'posts title 2', 20, 1, null),
       (now(), now(), 'posts content 3', 'posts title 3', 20, 2, null),
       (now(), now(), 'posts content 4', 'posts title 4', 2, 2, null),
       (now(), now(), 'posts content 5', 'posts title 5', 6, 3, null),
       (now(), now(), 'posts content 6', 'posts title 6', 10, 3, null);