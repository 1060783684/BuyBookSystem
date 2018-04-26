CREATE TABLE user_info
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    name VARCHAR(15) NOT NULL,
    password VARCHAR(16) NOT NULL
);
CREATE TABLE user_ext
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    headsrc VARCHAR(35) DEFAULT '/user/img/default',
    name VARCHAR(20),
    id_number VARCHAR(30),
    phone VARCHAR(11),
    sex VARCHAR(6),
    CONSTRAINT user_ext_ibfk_1 FOREIGN KEY (id) REFERENCES user_info (id)
);