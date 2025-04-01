-- USER SEEDER --

-- Suppression des données existantes pour éviter les doublons
DELETE FROM messages;
DELETE FROM user_contacts;
DELETE FROM users_conversations;
DELETE FROM conversations;
DELETE FROM channels;
DELETE FROM users_roles;
DELETE FROM users;
DELETE FROM roles WHERE name = 'USER';

-- Insertion du rôle USER s'il n'existe pas
INSERT INTO roles (name) VALUES ('USER');

-- Insertion des utilisateurs avec leur mot de passe en clair
INSERT INTO users (username, password) VALUES
                                           ('demoUser', 'password123'),
                                           ('use001', 'password123'),
                                           ('use002', 'password123'),
                                           ('use003', 'password123'),
                                           ('use004', 'password123');

-- Attribution du rôle USER aux utilisateurs
INSERT INTO users_roles (user_id, role_id)
SELECT users.id, roles.id FROM users, roles WHERE roles.name = 'USER';

-- CHANNEL SEEDER --
-- Doit être fait avant les messages de canal
INSERT INTO channels (name) VALUES
                                ('Channel001'),
                                ('Channel002'),
                                ('Channel003'),
                                ('Channel004'),
                                ('Channel005');

-- CONVERSATION SEEDER --
-- Doit être fait avant les messages de conversation
INSERT INTO conversations (type) VALUES
                                     ('PRIVATE'), ('PRIVATE'), ('PRIVATE'), ('PRIVATE'),
                                     ('PRIVATE'), ('PRIVATE'), ('PRIVATE'), ('PRIVATE');

-- USER CONVERSATIONS --
-- Lier les utilisateurs aux conversations
INSERT INTO users_conversations (user_id, conversation_id) VALUES
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 1),
                                                               ((SELECT id FROM users WHERE username = 'use001'), 1),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 2),
                                                               ((SELECT id FROM users WHERE username = 'use002'), 2),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 3),
                                                               ((SELECT id FROM users WHERE username = 'use003'), 3),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 4),
                                                               ((SELECT id FROM users WHERE username = 'use004'), 4),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 5),
                                                               ((SELECT id FROM users WHERE username = 'use001'), 5),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 6),
                                                               ((SELECT id FROM users WHERE username = 'use002'), 6),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 7),
                                                               ((SELECT id FROM users WHERE username = 'use003'), 7),
                                                               ((SELECT id FROM users WHERE username = 'demoUser'), 8),
                                                               ((SELECT id FROM users WHERE username = 'use004'), 8);

-- USER CONTACTS --
INSERT INTO user_contacts (user_id, contact_id) VALUES
                                                    ((SELECT id FROM users WHERE username = 'demoUser'), (SELECT id FROM users WHERE username = 'use001')),
                                                    ((SELECT id FROM users WHERE username = 'use001'), (SELECT id FROM users WHERE username = 'demoUser')),
                                                    ((SELECT id FROM users WHERE username = 'demoUser'), (SELECT id FROM users WHERE username = 'use002')),
                                                    ((SELECT id FROM users WHERE username = 'use002'), (SELECT id FROM users WHERE username = 'demoUser')),
                                                    ((SELECT id FROM users WHERE username = 'demoUser'), (SELECT id FROM users WHERE username = 'use003')),
                                                    ((SELECT id FROM users WHERE username = 'use003'), (SELECT id FROM users WHERE username = 'demoUser')),
                                                    ((SELECT id FROM users WHERE username = 'demoUser'), (SELECT id FROM users WHERE username = 'use004')),
                                                    ((SELECT id FROM users WHERE username = 'use004'), (SELECT id FROM users WHERE username = 'demoUser'));

-- PUBLIC MESSAGES --
INSERT INTO messages (user_id, content, username, created_at) VALUES
                                                                  ((SELECT id FROM users WHERE username = 'demoUser'), 'Salut, comment ça va ?', 'demoUser', NOW()),
                                                                  ((SELECT id FROM users WHERE username = 'use001'), 'Qu est-ce que tu fais en ce moment ?', 'use001', NOW()),
                                                                  ((SELECT id FROM users WHERE username = 'use002'), 'J ai passé une excellente journée!', 'use002', NOW()),
                                                                  ((SELECT id FROM users WHERE username = 'use003'), 'Tu as des projets pour le week-end ?', 'use003', NOW()),
                                                                  ((SELECT id FROM users WHERE username = 'use004'), 'Comment s est passée ta journée ?', 'use004', NOW());

-- CHANNEL MESSAGES --
INSERT INTO messages (channel_id, content, created_at, user_id, username) VALUES
                                                                              (1, 'Bienvenue sur Channel001', NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
                                                                              (2, 'Bienvenue sur Channel002', NOW(), (SELECT id FROM users WHERE username = 'use001'), 'use001'),
                                                                              (3, 'Bienvenue sur Channel003', NOW(), (SELECT id FROM users WHERE username = 'use002'), 'use002'),
                                                                              (4, 'Bienvenue sur Channel004', NOW(), (SELECT id FROM users WHERE username = 'use003'), 'use003'),
                                                                              (5, 'Bienvenue sur Channel005', NOW(), (SELECT id FROM users WHERE username = 'use004'), 'use004');

-- CONVERSATION MESSAGES --
INSERT INTO messages (content, conversation_id, created_at, user_id, username) VALUES
                                                                                   ('Salut, comment ça va ?', 1, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
                                                                                   ('Très bien et toi ?', 1, NOW(), (SELECT id FROM users WHERE username = 'use001'), 'use001'),
                                                                                   ('On se voit ce soir ?', 2, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
                                                                                   ('Oui, avec plaisir !', 2, NOW(), (SELECT id FROM users WHERE username = 'use002'), 'use002'),
                                                                                   ('Tu veux sortir ce week-end ?', 3, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
                                                                                   ('Bonne idée !', 3, NOW(), (SELECT id FROM users WHERE username = 'use003'), 'use003'),
                                                                                   ('Quel est ton plat préféré ?', 4, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
                                                                                   ('Les pâtes carbonara !', 4, NOW(), (SELECT id FROM users WHERE username = 'use004'), 'use004');