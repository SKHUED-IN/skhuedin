insert into user (created_date, last_modified_date, email, password, name, provider, user_image_url, entrance_year,
                  graduation_year, role)
values (now(), now(), 'admin@email.com', '1234', 'admin', 'SELF', '/img', now(), now(), 'ADMIN'),
       (now(), now(), 'dev.hyeonic@gmail.com', null, 'hyeonic', 'KAKAO', null, '2016', null, 'ADMIN'),
       (now(), now(), 'her0807@naver.com', null, 'her0807', 'KAKAO', null, '2017', null, 'ADMIN'),
       (now(), now(), 'hs98414@naver.com', null, '오혜성', 'KAKAO', null, '2017', null, 'ADMIN'),
       (now(), now(), 'user1@email.com', null, '일반 유저', 'KAKAO', null, '2017', null, 'USER');

insert into file (created_date, last_modified_date, original_name, name, path)
values (now(), now(), 'user.png', 'user.png', '/home/ec2-user/app/profile/');

insert into blog (user_id, profile_id, content, created_date, last_modified_date)
values (1, 1, 'admin blog', now(), now()),
       (2, 1, 'hyeonic 책장', now(), now()),
       (3, 1, 'her0807 책장', now(), now()),
       (4, 1, '오혜성의 책장', now(), now());

insert into question (target_user_id, writer_user_id, title, content, status, fix, view, created_date,
                      last_modified_date)
values (1, 2, 'test question 1', 'test question content 1', false, false, 0, now(), now()),
       (1, 2, 'test question 2', 'test question content 2', false, false, 0, now(), now()),
       (1, 2, 'test question 3', 'test question content 3', false, false, 0, now(), now()),
       (1, 2, 'test question 4', 'test question content 4', false, false, 0, now(), now()),
       (1, 2, 'test question 5', 'test question content 5', false, false, 0, now(), now()),
       (1, 2, 'test question 6', 'test question content 6', false, false, 0, now(), now()),
       (1, 2, 'test question 7', 'test question content 7', false, false, 0, now(), now()),
       (1, 2, 'test question 8', 'test question content 8', false, false, 0, now(), now()),
       (1, 2, 'test question 9', 'test question content 9', false, false, 0, now(), now()),
       (1, 2, 'test question 10', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 11', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 12', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 13', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 14', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 15', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 16', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 17', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 18', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 19', 'test question content 10', false, false, 0, now(), now()),
       (1, 3, 'test question 20', 'test question content 10', false, false, 0, now(), now()),

       (4, 1, 'test question 1', 'test question content 1', false, false, 0, now(), now()),
       (4, 1, 'test question 2', 'test question content 2', false, false, 0, now(), now()),
       (4, 1, 'test question 3', 'test question content 3', false, false, 0, now(), now()),
       (4, 1, 'test question 4', 'test question content 4', false, false, 0, now(), now()),
       (4, 1, 'test question 5', 'test question content 5', false, false, 0, now(), now()),
       (4, 1, 'test question 6', 'test question content 6', false, false, 0, now(), now()),
       (4, 1, 'test question 7', 'test question content 7', false, false, 0, now(), now()),
       (4, 1, 'test question 8', 'test question content 8', false, false, 0, now(), now()),
       (4, 1, 'test question 9', 'test question content 9', false, false, 0, now(), now()),
       (4, 1, 'test question 10', 'test question content 10', false, false, 0, now(), now()),
       (4, 1, 'test question 11', 'test question content 11', false, false, 0, now(), now()),
       (4, 1, 'test question 12', 'test question content 12', false, false, 0, now(), now()),
       (4, 1, 'test question 13', 'test question content 13', false, false, 0, now(), now()),
       (4, 1, 'test question 14', 'test question content 14', false, false, 0, now(), now()),
       (4, 1, 'test question 15', 'test question content 15', false, false, 0, now(), now()),
       (4, 1, 'test question 16', 'test question content 16', false, false, 0, now(), now()),
       (4, 1, 'test question 17', 'test question content 17', false, false, 0, now(), now()),
       (4, 1, 'test question 18', 'test question content 18', false, false, 0, now(), now()),
       (4, 1, 'test question 19', 'test question content 19', false, false, 0, now(), now()),
       (4, 1, 'test question 20', 'test question content 20', false, false, 0, now(), now());

insert into category (created_date, last_modified_date, name, weight)
values (now(), now(), '학창 시절', 1),
       (now(), now(), '취업 준비', 2),
       (now(), now(), '하고싶은말', 3),
       (now(), now(), '연봉', 4),
       (now(), now(), '건의사항', 0),
       (now(), now(), '자기소개', 1),
       (now(), now(), '학교생활', 1),
       (now(), now(), '졸업 후 현재', 1);

insert into posts (created_date, last_modified_date, content, title, delete_status, view, blog_id, category_id)
values (now(), now(), 'posts content 1', 'posts title 1', false, 1, 1, 1),
       (now(), now(), 'posts content 2', 'posts title 2', false, 2, 1, 2),
       (now(), now(), 'posts content 3', 'posts title 3', false, 3, 1, 3),
       (now(), now(), 'posts content 4', 'posts title 4', false, 4, 1, 1),
       (now(), now(), 'posts content 5', 'posts title 5', false, 5, 1, 2),
       (now(), now(), 'posts content 6', 'posts title 6', false, 6, 2, 3),
       (now(), now(), 'posts content 7', 'posts title 7', false, 7, 2, 1),
       (now(), now(), 'posts content 8', 'posts title 8', false, 8, 2, 2),
       (now(), now(), 'posts content 9', 'posts title 8', false, 9, 2, 3),
       (now(), now(), 'posts content 10', 'posts title 8', false, 10, 2, 1),
       (now(), now(), 'posts content 11', 'posts title 8', false, 11, 3, 2),
       (now(), now(), 'posts content 12', 'posts title 8', false, 12, 3, 3),
       (now(), now(), 'posts content 13', 'posts title 8', false, 13, 3, 1),
       (now(), now(), 'posts content 14', 'posts title 8', false, 14, 3, 2),
       (now(), now(), 'posts content 15', 'posts title 8', false, 15, 3, 3),
       (now(), now(), 'posts content 16', 'posts title 8', false, 16, 4, 1),
       (now(), now(), 'posts content 17', 'posts title 8', false, 17, 4, 2),
       (now(), now(), 'posts content 18', 'posts title 8', false, 18, 4, 3),
       (now(), now(), 'posts content 19', 'posts title 8', false, 19, 4, 1),
       (now(), now(), 'posts content 20', 'posts title 8', false, 20, 4, 2),

       (now(), now(), '건의 사항 1 입니다.', '건의 사항 1', null, null, 1, 5),
       (now(), now(), '건의 사항 2 입니다.', '건의 사항 2', null, null, 1, 5),
       (now(), now(), '건의 사항 3 입니다.', '건의 사항 3', null, null, 1, 5),
       (now(), now(), '건의 사항 4 입니다.', '건의 사항 4', null, null, 1, 5),
       (now(), now(), '건의 사항 5 입니다.', '건의 사항 5', null, null, 1, 5),
       (now(), now(), '건의 사항 6 입니다.', '건의 사항 6', null, null, 1, 5),
       (now(), now(), '건의 사항 7 입니다.', '건의 사항 7', null, null, 1, 5),
       (now(), now(), '건의 사항 8 입니다.', '건의 사항 8', null, null, 1, 5),
       (now(), now(), '건의 사항 9 입니다.', '건의 사항 9', null, null, 1, 5),
       (now(), now(), '건의 사항 10 입니다.', '건의 사항 10', null, null, 1, 5),
       (now(), now(), '건의 사항 11 입니다.', '건의 사항 11', null, null, 1, 5),
       (now(), now(), '건의 사항 12 입니다.', '건의 사항 12', null, null, 1, 5),
       (now(), now(), '건의 사항 13 입니다.', '건의 사항 13', null, null, 1, 5),
       (now(), now(), '건의 사항 14 입니다.', '건의 사항 14', null, null, 1, 5),
       (now(), now(), '건의 사항 15 입니다.', '건의 사항 15', null, null, 1, 5);

insert into comment (question_id, writer_user_id, content, created_date, last_modified_date)
values (21, 1, '댓글 1', now(), now()),
       (21, 2, '댓글 2', now(), now()),
       (21, 2, '댓글 3', now(), now()),
       (21, 2, '댓글 4', now(), now()),
       (21, 2, '댓글 5', now(), now()),
       (21, 2, '댓글 6', now(), now()),
       (21, 2, '댓글 7', now(), now()),
       (21, 2, '댓글 8', now(), now()),
       (21, 2, '댓글 9', now(), now()),
       (21, 2, '댓글 10', now(), now());

insert into banner (banner_id, title, content, weight, store_file_name, upload_file_name, created_date, last_modified_date)
values (1, '스크린샷1', '123', 100, '0bf27d50-6fdb-4f49-8ece-7da3203e411c.png', '스크린샷1.png', now(), now()),
       (2, '스크린샷2', '123', 10, '49dc1cc3-db59-4c71-84ee-3b5366a43569.png', '스크린샷2.png', now(), now());