-- 疎通確認用の初期データ。
-- 管理者ログイン: mail=admin@example.com / pass=testtest
-- pass は BCryptPasswordEncoder でハッシュ化した値。

INSERT INTO admin_users (id, name, mail, pass) VALUES
  (1, '管理者', 'admin@example.com', '$2a$10$7fZUzURF2xmQbo1AzfU1ae84jPczKuVlEBzMP8cFnLgrLxCzLPAiq');

INSERT INTO authors (id, name) VALUES
  (1, '夏目漱石'),
  (2, '芥川龍之介'),
  (3, '太宰治');

INSERT INTO books (id, title, author_id) VALUES
  (1, 'こころ', 1),
  (2, '羅生門', 2),
  (3, '吾輩は猫である', 1),
  (4, '蜘蛛の糸', 2),
  (5, '人間失格', 3);

INSERT INTO users (id, name) VALUES
  (1, '佐藤花子'),
  (2, '鈴木一郎'),
  (3, '高橋次郎');

INSERT INTO loan_records (id, user_id, book_id, loan_date, due_date, return_date) VALUES
  (1, 1, 1, '2026-08-01', '2026-08-08', '2026-08-07'),
  (2, 2, 1, '2026-08-10', '2026-08-17', '2026-08-16'),
  (3, 1, 2, '2026-08-20', '2026-08-27', NULL),
  (4, 2, 4, '2026-09-10', '2026-09-17', NULL);

SELECT setval(pg_get_serial_sequence('admin_users', 'id'), (SELECT MAX(id) FROM admin_users));
SELECT setval(pg_get_serial_sequence('authors', 'id'), (SELECT MAX(id) FROM authors));
SELECT setval(pg_get_serial_sequence('books', 'id'), (SELECT MAX(id) FROM books));
SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));
SELECT setval(pg_get_serial_sequence('loan_records', 'id'), (SELECT MAX(id) FROM loan_records));
