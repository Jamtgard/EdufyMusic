-- ED-116-SJ

-- Insert ALBUM --------------------------------------------------------------------------------------------------------
INSERT INTO album (album_id, album_title, album_url, album_length, album_release_date, album_number_of_streams, album_active)
VALUES
    (1, 'City Lights', 'https://music.example.com/album/1', '00:36:40', '2021-06-01', 212345, true),
    (2, 'Stellar Maps', 'https://music.example.com/album/2', '00:36:55', '2021-06-21', 224690, true),
    (3, 'Night Parade', 'https://music.example.com/album/3', '00:37:10', '2021-07-11', 237035, true),
    (4, 'Echoes & Neon', 'https://music.example.com/album/4', '00:37:25', '2021-07-31', 249380, true),
    (5, 'Static Dreams', 'https://music.example.com/album/5', '00:37:40', '2021-08-20', 261725, true),
    (6, 'Oceanic', 'https://music.example.com/album/6', '00:37:55', '2021-09-09', 274070, true),
    (7, 'Glass Forest', 'https://music.example.com/album/7', '00:38:10', '2021-09-29', 286415, true),
    (8, 'Arcade Hearts', 'https://music.example.com/album/8', '00:38:25', '2021-10-19', 298760, true),
    (9, 'Golden Hour', 'https://music.example.com/album/9', '00:38:40', '2021-11-08', 311105, true),
    (10, 'Afterglow', 'https://music.example.com/album/10', '00:38:55', '2021-11-28', 323450, true);

-- Link Creator to album

-- 1x (1x Album / 5x Creator)
-- 2x (2x Album / 3x Creator)
-- 3x (3x Album / 2x Creator)
-- 5x (1x Album / 1x Creator)
INSERT INTO album_creators (album_id, creator_id)
VALUES
    (4, 2),(4, 6),(4, 10),(4, 15),(4, 24),
    (2, 4),(2, 12),(2, 18),
    (7, 21),(7, 27),(7, 33),
    (5, 8),(5, 30),
    (9, 38),(9, 45),
    (1, 2),
    (3, 33),
    (6, 21),
    (8, 12),
    (10, 45);

-- Link Genre to album
INSERT INTO album_genres (album_id, genre_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 2),
    (7, 4),
    (8, 1),
    (9, 3),
    (10, 5);

-- Insert SONG ---------------------------------------------------------------------------------------------------------
INSERT INTO song (song_id, song_title, song_url, song_length, song_release_date, song_number_of_streams, song_active)
VALUES
    (1, 'Aurora Echo', 'https://music.example.com/song/1', '00:03:12', '2022-01-01', 5137, true),
    (2, 'Neon Skyline', 'https://music.example.com/song/2', '00:03:28', '2022-01-02', 5274, true),
    (3, 'Midnight Drive', 'https://music.example.com/song/3', '00:03:45', '2022-01-03', 5411, true),
    (4, 'Crystal Waves', 'https://music.example.com/song/4', '00:04:02', '2022-01-04', 5548, true),
    (5, 'Electric Bloom', 'https://music.example.com/song/5', '00:04:18', '2022-01-05', 5685, true),
    (6, 'Solar Flare', 'https://music.example.com/song/6', '00:04:36', '2022-01-06', 5822, true),
    (7, 'Lunar Drift', 'https://music.example.com/song/7', '00:04:50', '2022-01-07', 5959, true),
    (8, 'Velvet Horizon', 'https://music.example.com/song/8', '00:05:05', '2022-01-08', 6096, true),
    (9, 'Chromatic Pulse', 'https://music.example.com/song/9', '00:02:58', '2022-01-09', 6233, true),
    (10, 'Digital Mirage', 'https://music.example.com/song/10', '00:03:33', '2022-01-10', 6370, true),

    (11, 'Starlit Avenue', 'https://music.example.com/song/11', '00:03:12', '2022-01-11', 6507, true),
    (12, 'Phantom Radio', 'https://music.example.com/song/12', '00:03:28', '2022-01-12', 6644, true),
    (13, 'Sapphire Highway', 'https://music.example.com/song/13', '00:03:45', '2022-01-13', 6781, true),
    (14, 'Ember City', 'https://music.example.com/song/14', '00:04:02', '2022-01-14', 6918, true),
    (15, 'Prism Garden', 'https://music.example.com/song/15', '00:04:18', '2022-01-15', 7055, true),
    (16, 'Quantum Harbor', 'https://music.example.com/song/16', '00:04:36', '2022-01-16', 7192, true),
    (17, 'Static Meadow', 'https://music.example.com/song/17', '00:04:50', '2022-01-17', 7329, true),
    (18, 'Echo Harbor', 'https://music.example.com/song/18', '00:05:05', '2022-01-18', 7466, true),
    (19, 'Mercury Lights', 'https://music.example.com/song/19', '00:02:58', '2022-01-19', 7603, true),
    (20, 'Indigo Circuit', 'https://music.example.com/song/20', '00:03:33', '2022-01-20', 7740, true),

    (21, 'Gravity Waltz', 'https://music.example.com/song/21', '00:03:12', '2022-01-21', 7877, true),
    (22, 'Violet Cascade', 'https://music.example.com/song/22', '00:03:28', '2022-01-22', 8014, true),
    (23, 'Carbon Dreams', 'https://music.example.com/song/23', '00:03:45', '2022-01-23', 8151, true),
    (24, 'Neon Valley', 'https://music.example.com/song/24', '00:04:02', '2022-01-24', 8288, true),
    (25, 'Paper Satellites', 'https://music.example.com/song/25', '00:04:18', '2022-01-25', 8425, true),
    (26, 'Chrome Lanterns', 'https://music.example.com/song/26', '00:04:36', '2022-01-26', 8562, true),
    (27, 'Velvet Atlas', 'https://music.example.com/song/27', '00:04:50', '2022-01-27', 8699, true),
    (28, 'Golden Static', 'https://music.example.com/song/28', '00:05:05', '2022-01-28', 8836, true),
    (29, 'Serotonin Sunset', 'https://music.example.com/song/29', '00:02:58', '2022-01-29', 8973, true),
    (30, 'Binary Carousel', 'https://music.example.com/song/30', '00:03:33', '2022-01-30', 9110, true),

    (31, 'Glass Cathedral', 'https://music.example.com/song/31', '00:03:12', '2022-01-31', 9247, true),
    (32, 'Ivory Engines', 'https://music.example.com/song/32', '00:03:28', '2022-02-01', 9384, true),
    (33, 'Infrared Summer', 'https://music.example.com/song/33', '00:03:45', '2022-02-02', 9521, true),
    (34, 'Pixel Bouquet', 'https://music.example.com/song/34', '00:04:02', '2022-02-03', 9658, true),
    (35, 'Mercury Letters', 'https://music.example.com/song/35', '00:04:18', '2022-02-04', 9795, true),
    (36, 'Velvet Parabola', 'https://music.example.com/song/36', '00:04:36', '2022-02-05', 9932, true),
    (37, 'Midnight Cartography', 'https://music.example.com/song/37', '00:04:50', '2022-02-06', 10069, true),
    (38, 'Electric Canopy', 'https://music.example.com/song/38', '00:05:05', '2022-02-07', 10206, true),
    (39, 'Quartz Arcade', 'https://music.example.com/song/39', '00:02:58', '2022-02-08', 10343, true),
    (40, 'Cinnamon Halcyon', 'https://music.example.com/song/40', '00:03:33', '2022-02-09', 10480, true),

    (41, 'Lavender Datastream', 'https://music.example.com/song/41', '00:03:12', '2022-02-10', 10617, true),
    (42, 'Celestial Postcards', 'https://music.example.com/song/42', '00:03:28', '2022-02-11', 10754, true),
    (43, 'Sapphire Algebra', 'https://music.example.com/song/43', '00:03:45', '2022-02-12', 10891, true),
    (44, 'Analog Daydream', 'https://music.example.com/song/44', '00:04:02', '2022-02-13', 11028, true),
    (45, 'Porcelain Frequencies', 'https://music.example.com/song/45', '00:04:18', '2022-02-14', 11165, true),
    (46, 'Ultraviolet Orchard', 'https://music.example.com/song/46', '00:04:36', '2022-02-15', 11302, true),
    (47, 'Cobalt Horizon', 'https://music.example.com/song/47', '00:04:50', '2022-02-16', 11439, true),
    (48, 'Celadon Skyline', 'https://music.example.com/song/48', '00:05:05', '2022-02-17', 11576, true),
    (49, 'Indigo Paradox', 'https://music.example.com/song/49', '00:02:58', '2022-02-18', 11713, true),
    (50, 'Amber Arcade', 'https://music.example.com/song/50', '00:03:33', '2022-02-19', 11850, true),

    (51, 'Neon Meadow', 'https://music.example.com/song/51', '00:03:12', '2022-02-20', 11987, true),
    (52, 'Porcelain Moonrise', 'https://music.example.com/song/52', '00:03:28', '2022-02-21', 12124, true),
    (53, 'Marble Echoes', 'https://music.example.com/song/53', '00:03:45', '2022-02-22', 12261, true),
    (54, 'Turbine Garden', 'https://music.example.com/song/54', '00:04:02', '2022-02-23', 12398, true),
    (55, 'Luminous Alleys', 'https://music.example.com/song/55', '00:04:18', '2022-02-24', 12535, true),
    (56, 'Static Aquarium', 'https://music.example.com/song/56', '00:04:36', '2022-02-25', 12672, true),
    (57, 'Kinetic Nocturne', 'https://music.example.com/song/57', '00:04:50', '2022-02-26', 12809, true),
    (58, 'Digital Vineyard', 'https://music.example.com/song/58', '00:05:05', '2022-02-27', 12946, true),
    (59, 'Honeycomb Satellites', 'https://music.example.com/song/59', '00:02:58', '2022-02-28', 13083, true),

    (60, 'Crimson Carousel', 'https://music.example.com/song/60', '00:03:33', '2022-03-01', 13220, true),
    (61, 'Vapor City', 'https://music.example.com/song/61', '00:03:12', '2022-03-02', 13357, true),
    (62, 'Velvet Observatory', 'https://music.example.com/song/62', '00:03:28', '2022-03-03', 13494, true),
    (63, 'Phantom Harbor', 'https://music.example.com/song/63', '00:03:45', '2022-03-04', 13631, true),
    (64, 'Mosaic Boulevard', 'https://music.example.com/song/64', '00:04:02', '2022-03-05', 13768, true),
    (65, 'Quartz Lanterns', 'https://music.example.com/song/65', '00:04:18', '2022-03-06', 13905, true),
    (66, 'Lunar Postcards', 'https://music.example.com/song/66', '00:04:36', '2022-03-07', 14042, true),
    (67, 'Sapphire Compass', 'https://music.example.com/song/67', '00:04:50', '2022-03-08', 14179, true),
    (68, 'Electric Starfields', 'https://music.example.com/song/68', '00:05:05', '2022-03-09', 14316, true),
    (69, 'Umbra Boulevard', 'https://music.example.com/song/69', '00:02:58', '2022-03-10', 14453, true),

    (70, 'Prism Telegram', 'https://music.example.com/song/70', '00:03:33', '2022-03-11', 14590, true),
    (71, 'Pixel Rainfall', 'https://music.example.com/song/71', '00:03:12', '2022-03-12', 14727, true),
    (72, 'Golden Parabola', 'https://music.example.com/song/72', '00:03:28', '2022-03-13', 14864, true),
    (73, 'Graphite Lullaby', 'https://music.example.com/song/73', '00:03:45', '2022-03-14', 15001, true),
    (74, 'Carbon Postcards', 'https://music.example.com/song/74', '00:04:02', '2022-03-15', 15138, true),
    (75, 'Nebula Terrace', 'https://music.example.com/song/75', '00:04:18', '2022-03-16', 15275, true),
    (76, 'Analog Orchard', 'https://music.example.com/song/76', '00:04:36', '2022-03-17', 15412, true),
    (77, 'Ceramic Arcadia', 'https://music.example.com/song/77', '00:04:50', '2022-03-18', 15549, true),
    (78, 'Violet Telegram', 'https://music.example.com/song/78', '00:05:05', '2022-03-19', 15686, true),
    (79, 'Indigo Boulevard', 'https://music.example.com/song/79', '00:02:58', '2022-03-20', 15823, true),

    (80, 'Starlight Carousel', 'https://music.example.com/song/80', '00:03:33', '2022-03-21', 15960, true),
    (81, 'Magnetic Meadow', 'https://music.example.com/song/81', '00:03:12', '2022-03-22', 16097, true),
    (82, 'Porcelain Moonrise', 'https://music.example.com/song/82', '00:03:28', '2022-03-23', 16234, true),
    (83, 'Turquoise Circuit', 'https://music.example.com/song/83', '00:03:45', '2022-03-24', 16371, true),
    (84, 'Paper Parabola', 'https://music.example.com/song/84', '00:04:02', '2022-03-25', 16508, true),
    (85, 'Amber Frequencies', 'https://music.example.com/song/85', '00:04:18', '2022-03-26', 16645, true),
    (86, 'Crystal Observatory', 'https://music.example.com/song/86', '00:04:36', '2022-03-27', 16782, true),
    (87, 'Velvet Constellations', 'https://music.example.com/song/87', '00:04:50', '2022-03-28', 16919, true),
    (88, 'Nocturne Engines', 'https://music.example.com/song/88', '00:05:05', '2022-03-29', 17056, true),
    (89, 'Vapor Telegram', 'https://music.example.com/song/89', '00:02:58', '2022-03-30', 17193, true),

    (90, 'Prism Arcade', 'https://music.example.com/song/90', '00:03:33', '2022-03-31', 17330, true),
    (91, 'Sonic Halcyon', 'https://music.example.com/song/91', '00:03:12', '2022-04-01', 17467, true),
    (92, 'Chrome Daydream', 'https://music.example.com/song/92', '00:03:28', '2022-04-02', 17604, true),
    (93, 'Opal Skyline', 'https://music.example.com/song/93', '00:03:45', '2022-04-03', 17741, true),
    (94, 'Quantum Postcards', 'https://music.example.com/song/94', '00:04:02', '2022-04-04', 17878, true),
    (95, 'Electric Parabola', 'https://music.example.com/song/95', '00:04:18', '2022-04-05', 18015, true),
    (96, 'Honeyed Horizon', 'https://music.example.com/song/96', '00:04:36', '2022-04-06', 18152, true),
    (97, 'Azure Telegram', 'https://music.example.com/song/97', '00:04:50', '2022-04-07', 18289, true),
    (98, 'Neon Observatory', 'https://music.example.com/song/98', '00:05:05', '2022-04-08', 18426, true),
    (99, 'Polaris Garden', 'https://music.example.com/song/99', '00:02:58', '2022-04-09', 18563, true),
    (100, 'Midnight Harbor', 'https://music.example.com/song/100', '00:03:33', '2022-04-10', 18700, true);

-- Link Creator to song
INSERT INTO song_creators (song_id, creator_id)
VALUES
    (1,2),(2,4),(3,6),(4,8),(5,10),(6,12),(7,15),(8,18),(9,21),(10,24),
    (11,27),(12,30),(13,33),(14,38),(15,45),(16,2),(17,4),(18,6),(19,8),(20,10),
    (21,12),(22,15),(23,18),(24,21),(25,24),(26,27),(27,30),(28,33),(29,38),(30,45),
    (31,2),(32,4),(33,6),(34,8),(35,10),(36,12),(37,15),(38,18),(39,21),(40,24),
    (41,27),(42,30),(43,33),(44,38),(45,45),(46,2),(47,4),(48,6),(49,8),(50,10),
    (51,12),(52,15),(53,18),(54,21),(55,24),(56,27),(57,30),(58,33),(59,38),(60,45),
    (61,2),(62,4),(63,6),(64,8),(65,10),(66,12),(67,15),(68,18),(69,21),(70,24),
    (71,27),(72,30),(73,33),(74,38),(75,45),(76,2),(77,4),(78,6),(79,8),(80,10),
    (81,12),(82,15),(83,18),(84,21),(85,24),(86,27),(87,30),(88,33),(89,38),(90,45),
    (91,2),(92,4),(93,6),(94,8),(95,10),(96,12),(97,15),(98,18),(99,21),(100,24);

-- Link Songs to Genres
INSERT INTO song_genres (song_id, genre_id)
VALUES
    (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),
    (11,1),(12,2),(13,3),(14,4),(15,5),(16,6),(17,7),(18,8),(19,9),(20,10),
    (21,1),(22,2),(23,3),(24,4),(25,5),(26,6),(27,7),(28,8),(29,9),(30,10),
    (31,1),(32,2),(33,3),(34,4),(35,5),(36,6),(37,7),(38,8),(39,9),(40,10),
    (41,1),(42,2),(43,3),(44,4),(45,5),(46,6),(47,7),(48,8),(49,9),(50,10),
    (51,1),(52,2),(53,3),(54,4),(55,5),(56,6),(57,7),(58,8),(59,9),(60,10),
    (61,1),(62,2),(63,3),(64,4),(65,5),(66,6),(67,7),(68,8),(69,9),(70,10),
    (71,1),(72,2),(73,3),(74,4),(75,5),(76,6),(77,7),(78,8),(79,9),(80,10),
    (81,1),(82,2),(83,3),(84,4),(85,5),(86,6),(87,7),(88,8),(89,9),(90,10),
    (91,1),(92,2),(93,3),(94,4),(95,5),(96,6),(97,7),(98,8),(99,9),(100,10);


-- Insert ALBUM_TRACKS -------------------------------------------------------------------------------------------------
INSERT INTO album_tracks (album_track_id, album_id, song_id, track_index)
VALUES
    (1, 1, 1, 1),
    (2, 1, 2, 2),
    (3, 1, 3, 3),
    (4, 1, 4, 4),
    (5, 1, 5, 5),
    (6, 1, 6, 6),
    (7, 1, 7, 7),
    (8, 1, 8, 8),
    (9, 1, 9, 9),
    (10, 1, 10, 10),

    (11, 2, 11, 1),
    (12, 2, 12, 2),
    (13, 2, 13, 3),
    (14, 2, 14, 4),
    (15, 2, 15, 5),
    (16, 2, 16, 6),
    (17, 2, 17, 7),
    (18, 2, 18, 8),
    (19, 2, 19, 9),
    (20, 2, 20, 10),

    (21, 3, 21, 1),
    (22, 3, 22, 2),
    (23, 3, 23, 3),
    (24, 3, 24, 4),
    (25, 3, 25, 5),
    (26, 3, 26, 6),
    (27, 3, 27, 7),
    (28, 3, 28, 8),
    (29, 3, 29, 9),
    (30, 3, 30, 10),

    (31, 4, 31, 1),
    (32, 4, 32, 2),
    (33, 4, 33, 3),
    (34, 4, 34, 4),
    (35, 4, 35, 5),
    (36, 4, 36, 6),
    (37, 4, 37, 7),
    (38, 4, 38, 8),
    (39, 4, 39, 9),
    (40, 4, 40, 10),

    (41, 5, 41, 1),
    (42, 5, 42, 2),
    (43, 5, 43, 3),
    (44, 5, 44, 4),
    (45, 5, 45, 5),
    (46, 5, 46, 6),
    (47, 5, 47, 7),
    (48, 5, 48, 8),
    (49, 5, 49, 9),
    (50, 5, 50, 10),

    (51, 6, 51, 1),
    (52, 6, 52, 2),
    (53, 6, 53, 3),
    (54, 6, 54, 4),
    (55, 6, 55, 5),
    (56, 6, 56, 6),
    (57, 6, 57, 7),
    (58, 6, 58, 8),
    (59, 6, 59, 9),
    (60, 6, 60, 10),

    (61, 7, 61, 1),
    (62, 7, 62, 2),
    (63, 7, 63, 3),
    (64, 7, 64, 4),
    (65, 7, 65, 5),
    (66, 7, 66, 6),
    (67, 7, 67, 7),
    (68, 7, 68, 8),
    (69, 7, 69, 9),
    (70, 7, 70, 10),

    (71, 8, 71, 1),
    (72, 8, 72, 2),
    (73, 8, 73, 3),
    (74, 8, 74, 4),
    (75, 8, 75, 5),
    (76, 8, 76, 6),
    (77, 8, 77, 7),
    (78, 8, 78, 8),
    (79, 8, 79, 9),
    (80, 8, 80, 10),

    (81, 9, 81, 1),
    (82, 9, 82, 2),
    (83, 9, 83, 3),
    (84, 9, 84, 4),
    (85, 9, 85, 5),
    (86, 9, 86, 6),
    (87, 9, 87, 7),
    (88, 9, 88, 8),
    (89, 9, 89, 9),
    (90, 9, 90, 10),

    (91, 10, 91, 1),
    (92, 10, 92, 2),
    (93, 10, 93, 3),
    (94, 10, 94, 4),
    (95, 10, 95, 5),
    (96, 10, 96, 6),
    (97, 10, 97, 7),
    (98, 10, 98, 8),
    (99, 10, 99, 9),
    (100, 10, 100, 10);