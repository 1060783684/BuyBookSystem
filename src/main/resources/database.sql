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
  id      VARCHAR(40) PRIMARY KEY,
  name    VARCHAR(30),
  mail    VARCHAR(10),
  user_id BIGINT,
  addr    VARCHAR(70),
  phone   VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE TABLE store_info
(
  id BIGINT(20) PRIMARY KEY NOT NULL,
  descs VARCHAR(200),
  name VARCHAR(40),
  imgsrc VARCHAR(60),
  addr VARCHAR(20),
  user_id BIGINT(20),
  visit_num BIGINT(20),
  isCheck INT(11),
  CONSTRAINT store_info_ibfk_1 FOREIGN KEY (user_id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX user_id ON store_info (user_id);