-- Users
insert into user (user_id, avatar, description, display_name, email, password, registration_date, user_type, username, moderates) values (1, "https://i.redd.it/7z6purnyuvx51.png", "iOS and Android developer", "Ognjen", "lazicognjen2001@@gmail.com", "$2a$10$U5PO05keyVq2qyzFpQ/9QOgO3KoN3uIw2bFhrapkW4.pAUC0/nRtu", '2021-12-12 12:12:12.23', 2, "ognjen.lazic", null);
insert into user (user_id, avatar, description, display_name, email, password, registration_date, user_type, username, moderates) values (2, "https://i.redd.it/7z6purnyuvx51.png", "Football player", "Marko", "marko@gmail.com", "$2a$10$U5PO05keyVq2qyzFpQ/9QOgO3KoN3uIw2bFhrapkW4.pAUC0/nRtu", '2021-12-14 13:13:13.23', 1, "marko.brkic", null);
insert into user (user_id, avatar, description, display_name, email, password, registration_date, user_type, username, moderates) values (3, "https://i.redd.it/7z6purnyuvx51.png", "Software engineer", "Vladimir", "vladimir@gmail.com", "$2a$10$U5PO05keyVq2qyzFpQ/9QOgO3KoN3uIw2bFhrapkW4.pAUC0/nRtu", '2021-12-12 13:13:13.23', 1, "vladimir.djurdjevic", null);
insert into user (user_id, avatar, description, display_name, email, password, registration_date, user_type, username, moderates) values (4, "https://i.redd.it/7z6purnyuvx51.png", "Guitar entusiast", "Petar", "petar@gmail.com", "$2a$10$U5PO05keyVq2qyzFpQ/9QOgO3KoN3uIw2bFhrapkW4.pAUC0/nRtu", '2021-12-13 13:13:13.23', 0, "petar.petrovic", null);

-- Communities
insert into community (community_id, creation_date, description, name, suspended, suspended_reason, moderator) values (1, '2021-11-12 13:13:13.23', "Community for all iOS developers", "iOS development", false, null, 3);
insert into community (community_id, creation_date, description, name, suspended, suspended_reason, moderator) values (2, '2021-11-12 14:14:14.24', "Community for all football fans", "Football", false, null, 2);

-- Flairs
insert into flair (flair_id, name) values (1, "Programming");
insert into flair (flair_id, name) values (2, "Football");
insert into flair (flair_id, name) values (4, "iOS");
insert into flair (flair_id, name) values (5, "Adnroid");


-- Posts
insert into post (post_id, creation_date, image_path, text, title, community, flairs, user, reactions, posts, post) values (1, '2021-11-12 14:14:14.24', "https://uploads-ssl.webflow.com/5f841209f4e71b2d70034471/6078b650748b8558d46ffb7f_Flutter%20app%20development.png", "Thoughts on Flutter 3.0?", "New Flutter!", 1, 1, 1, null, null, null);
insert into post (post_id, creation_date, image_path, text, title, community, flairs, user, reactions, posts, post) values (2, '2021-11-12 15:14:14.24', null, "Can someone explain me Almofire to me?", "Almofire", 1, 1, 3, null, null, null);
insert into post (post_id, creation_date, image_path, text, title, community, flairs, user, reactions, posts, post) values (3, '2021-11-12 16:14:14.24', null, "What are you thinking, does new menager goes to make samo succes like in Ajax?", "Manchester United new menager", 2, 2, 2, null, null, null);
insert into post (post_id, creation_date, image_path, text, title, community, flairs, user, reactions, posts, post) values (4, '2021-11-12 11:14:14.24', null, "What are you think, where is Ronaldinhio rigt now","Ronaldinhio", 2, 2, 3, null, null, null);

-- Reactions
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (1, 0, '2022-01-12 17:14:14.24', null, 1, 1, null);
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (2, 0, '2022-01-12 18:14:14.24', null, 1, 2, null);
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (3, 1, '2022-01-12 10:14:14.24', null, 1, 3, null);
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (4, 0, '2022-01-12 17:14:14.24', null, 2, 1, null);
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (5, 0, '2022-01-12 18:14:14.24', null, 2, 2, null);
insert into reaction (reaction_id, reaction_type, timestamp, comment, post, user, reactions) values (6, 1, '2022-01-12 10:14:14.24', null, 3, 3, null);

-- Comments
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (1, false, "I think this will be main technology to use in developement.", '2022-02-12 22:14:14.24', null, 1, null, 1, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (2, false, "Native development is still better!", '2022-01-12 23:14:14.24', null, 1, null, 3, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (3, false, "With Cristiano in MAN UTD, I think this will not work...", '2022-01-12 09:14:14.24', null, 3, null, 2, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (4, false, "Probbaly smoking weed somewhere in Cambodia, LOL!!!", '2022-01-12 09:14:14.24', null, 4, null, 3, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (5, false, "Hell no!", '2022-01-12 09:14:14.24', 1, null, null, 3, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (6, false, "I dont think so...", '2022-01-12 09:14:14.24', 1, null, null, 3, null);
insert into comment (comment_id, is_deleted, text, timestamp, comment, post, replies_to, user, comments) values (7, false, "Nah you even dont know anything about flutter...", '2022-01-12 09:14:14.24', 6, null, null, 3, null);

-- Banned
-- For now I will not add any banned user, maybe later.
insert into banned (ban_id, timestamp, user, community, banneds, banned) values (1, '2022-03-12 19:14:14.24', 1, 1, null, null);
insert into banned (ban_id, timestamp, user, community, banneds, banned) values (2, '2022-05-12 18:14:14.24', 1, 2, null, null);

-- Reports
insert into report (report_id, accepted, report_reason, timestamp, user, comment, post, reports) values (1, false, 1, '2022-02-12 13:14:14.24', 1, null, 1, null);
insert into report (report_id, accepted, report_reason, timestamp, user, comment, post, reports) values (2, false, 3, '2022-01-12 19:14:14.24', 2, null, 2, null);
insert into report (report_id, accepted, report_reason, timestamp, user, comment, post, reports) values (3, false, 3, '2022-03-12 19:14:14.24', 2, 1, null , null);

-- Rules
insert into rule (rule_id, description, community, rules) values (1, "There is no place for hate speech!", 1, null);
insert into rule (rule_id, description, community, rules) values (2, "Only programming, no tech stuff!", 1, null);
insert into rule (rule_id, description, community, rules) values (3, "Dont ask us to fix your printer, and you don't have any app idea!", 1, null);
insert into rule (rule_id, description, community, rules) values (4, "There is no place for hate speech!", 2, null);
insert into rule (rule_id, description, community, rules) values (5, "Football is not a soccer! There is foot and ball, not hand and egg!", 2, null);
insert into rule (rule_id, description, community, rules) values (6, "Against modern football!", 2, null);

-- User Communities
insert into user_communities (user_user_id, communities_community_id) values (1, 1);
insert into user_communities (user_user_id, communities_community_id) values (1, 2);
insert into user_communities (user_user_id, communities_community_id) values (2, 1);
insert into user_communities (user_user_id, communities_community_id) values (2, 2);
insert into user_communities (user_user_id, communities_community_id) values (3, 1);
insert into user_communities (user_user_id, communities_community_id) values (3, 2);
insert into user_communities (user_user_id, communities_community_id) values (4, 1);
insert into user_communities (user_user_id, communities_community_id) values (4, 2);

-- Flair Communities
insert into flair_communities (flair_flair_id, communities_community_id) values (1, 1);
insert into flair_communities (flair_flair_id, communities_community_id) values (3, 1);
insert into flair_communities (flair_flair_id, communities_community_id) values (4, 1);
insert into flair_communities (flair_flair_id, communities_community_id) values (2, 2);

-- Community users
insert into community_users (community_community_id, users_user_id) values (1, 1);
insert into community_users (community_community_id, users_user_id) values (1, 2);
insert into community_users (community_community_id, users_user_id) values (1, 3);
insert into community_users (community_community_id, users_user_id) values (2, 1);
insert into community_users (community_community_id, users_user_id) values (2, 2);
insert into community_users (community_community_id, users_user_id) values (2, 3);

-- Community flairs
insert into community_flairs (community_community_id, flairs_flair_id) values (1, 1);
insert into community_flairs (community_community_id, flairs_flair_id) values (1, 3);
insert into community_flairs (community_community_id, flairs_flair_id) values (1, 4);
insert into community_flairs (community_community_id, flairs_flair_id) values (2, 2);
