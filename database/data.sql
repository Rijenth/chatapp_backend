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
DELETE FROM channel_roles_users;

-- Insertion du rôle USER s'il n'existe pas
INSERT INTO roles (name) VALUES ('USER');

-- Insertion des utilisateurs avec leur mot de passe en clair
INSERT INTO users (username, password, is_online) VALUES
                                           ('demoUser', '$2a$10$DG./Bxi8VHPrlrJZkZNONu/t5f5KLj3EJucPkJswjEn63myP5k3KS', false),
                                           ('use001', '$2a$10$DG./Bxi8VHPrlrJZkZNONu/t5f5KLj3EJucPkJswjEn63myP5k3KS', false),
                                           ('use002', '$2a$10$DG./Bxi8VHPrlrJZkZNONu/t5f5KLj3EJucPkJswjEn63myP5k3KS', false),
                                           ('use003', '$2a$10$DG./Bxi8VHPrlrJZkZNONu/t5f5KLj3EJucPkJswjEn63myP5k3KS', false),
                                           ('use004', '$2a$10$DG./Bxi8VHPrlrJZkZNONu/t5f5KLj3EJucPkJswjEn63myP5k3KS', false);

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
INSERT INTO messages (content, channel_id, created_at, user_id, username) VALUES
-- Channel 1 : demoUser & use001
('Bonjour tout le monde sur Channel001', 1, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Salut a tous, content d etre ici', 1, NOW(), (SELECT id FROM users WHERE username = 'use001'), 'use001'),
('Quelqu un veut coder ce soir', 1, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Oui je suis partant', 1, NOW(), (SELECT id FROM users WHERE username = 'use001'), 'use001'),

-- Channel 2 : demoUser & use002
('Bienvenue sur Channel002', 2, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Merci pour l ajout', 2, NOW(), (SELECT id FROM users WHERE username = 'use002'), 'use002'),
('Tu utilises Avalonia aussi', 2, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Oui je travaille dessus en ce moment', 2, NOW(), (SELECT id FROM users WHERE username = 'use002'), 'use002'),

-- Channel 3 : demoUser & use003
('Hello tout le monde sur Channel003', 3, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Bonjour a tous', 3, NOW(), (SELECT id FROM users WHERE username = 'use003'), 'use003'),
('Vous travaillez sur quoi', 3, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Je suis sur une interface de chat', 3, NOW(), (SELECT id FROM users WHERE username = 'use003'), 'use003'),

-- Channel 4 : demoUser & use004
('Channel004 est lance', 4, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Parfait je suis la', 4, NOW(), (SELECT id FROM users WHERE username = 'use004'), 'use004'),
('On commence par quoi', 4, NOW(), (SELECT id FROM users WHERE username = 'demoUser'), 'demoUser'),
('Par les composants principaux', 4, NOW(), (SELECT id FROM users WHERE username = 'use004'), 'use004');

-- Insertion des autres roles
INSERT INTO roles (name) VALUES ('MODERATOR'), ('ADMIN');

-- ASSIGNATION DES ADMINS AUX CHANNELS
-- Récupérez l'ID du rôle ADMIN
SET @admin_role_id = (SELECT id FROM roles WHERE name = 'ADMIN');

-- Assignez chaque utilisateur comme ADMIN d'un channel
INSERT INTO channel_roles_users (channel_id, role_id, user_id)
VALUES
    (1, @admin_role_id, (SELECT id FROM users WHERE username = 'demoUser')),
    (2, @admin_role_id, (SELECT id FROM users WHERE username = 'use001')),
    (3, @admin_role_id, (SELECT id FROM users WHERE username = 'use002')),
    (4, @admin_role_id, (SELECT id FROM users WHERE username = 'use003')),
    (5, @admin_role_id, (SELECT id FROM users WHERE username = 'use004'));

-- Optionnel : Assignez le rôle USER aux autres utilisateurs pour chaque channel
-- (si vous voulez qu'ils aient accès à tous les channels)
SET @user_role_id = (SELECT id FROM roles WHERE name = 'USER');

-- Pour chaque channel, assignez le rôle USER à tous les utilisateurs sauf l'admin
INSERT INTO channel_roles_users (channel_id, role_id, user_id)
SELECT
    c.id,
    @user_role_id,
    u.id
FROM
    channels c
        CROSS JOIN
    users u
WHERE
    u.id NOT IN (
        SELECT cru.user_id
        FROM channel_roles_users cru
        WHERE cru.channel_id = c.id AND cru.role_id = @admin_role_id
    );
