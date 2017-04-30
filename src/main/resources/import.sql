INSERT INTO virtue (id, description, name) VALUES (1, 'Loving kindness and non injury', 'Loving kindness and non injury');
INSERT INTO virtue (id, description, name) VALUES (2, 'Generosity and non Stealing', 'Generosity and non Stealing');

INSERT INTO quote (id, description, image_relative_url, name, author_id, virtue_id) VALUES (1, 'test', '/images/quote1.png', 'test', 1, 1);
INSERT INTO author (id, name) VALUES (1, 'GMCKS');
INSERT INTO lq_user (id, name, password) VALUES (1, 'admin', '$2a$06$ZHNLFl3WUYwHtK0uLdlfKemOFl/1q5Qq.HV55KdlNpb9R3jjxdyqC');