CREATE TABLE user_info
(
  id       BIGINT(20) PRIMARY KEY NOT NULL,
  name     VARCHAR(15)            NOT NULL,
  password VARCHAR(16)            NOT NULL
)
  DEFAULT CHARSET = "utf8";
CREATE TABLE user_ext
(
  id        BIGINT(20) PRIMARY KEY NOT NULL,
  headsrc   VARCHAR(35) DEFAULT '/user/img/default',
  name      VARCHAR(20),
  id_number VARCHAR(30),
  phone     VARCHAR(11),
  sex       VARCHAR(6),
  CONSTRAINT user_ext_ibfk_1 FOREIGN KEY (id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE TABLE address_info (
  id      VARCHAR(30) PRIMARY KEY,
  name    VARCHAR(30),
  mail    VARCHAR(10),
  user_id BIGINT,
  addr    VARCHAR(70),
  phone   VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";