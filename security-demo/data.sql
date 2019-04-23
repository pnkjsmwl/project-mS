insert into user(user_id, email, password, first_name, last_name, active) VALUES
(1, 'naman@gmail.com', 'naman', 'Naman', 'Nigam', 1),
(2, 'anurag@gmail.com', 'anurag', 'Anurag', 'Panwar', 1),
(3, 'raghav@gmail.com', 'raghav', 'Raghav', 'Arora', 1),
(4, 'pankaj@gmail.com', 'pankaj', 'Pankaj', 'Semwal', 1)
;

insert into role(role_id, role) VALUES
(1, 'ADMIN'),
(2, 'DBA'),
(3, 'USER')
;

insert into user_role(user_id, role_id) VALUES
(1, 3),
(2, 3),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3)
;