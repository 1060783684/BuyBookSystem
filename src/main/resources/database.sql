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
  id        BIGINT(20) PRIMARY KEY NOT NULL,
  descs     VARCHAR(200),
  name      VARCHAR(40),
  imgsrc    VARCHAR(60),
  addr      VARCHAR(20),
  user_id   BIGINT(20),
  visit_num BIGINT(20),
  isCheck   INT(11),
  CONSTRAINT store_info_ibfk_1 FOREIGN KEY (user_id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX user_id
  ON store_info (user_id);
CREATE TABLE goods
(
  id        VARCHAR(40) PRIMARY KEY NOT NULL,
  store_id  BIGINT(20),
  name      VARCHAR(60),
  descs     VARCHAR(200),
  cost      FLOAT,
  good_est  BIGINT(20),
  bad_est   BIGINT(20),
  sell_num  BIGINT(20),
  sales_num BIGINT(20),
  num       BIGINT(20),
  img       VARCHAR(60),
  status    INT(11),
  type      INT(11),
  CONSTRAINT goods_ibfk_1 FOREIGN KEY (store_id) REFERENCES store_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX store_id
  ON goods (store_id);
CREATE TABLE shop_car
(
  id        VARCHAR(40) PRIMARY KEY NOT NULL,
  goods_id  VARCHAR(40),
  user_id   BIGINT(20),
  goods_num BIGINT(20),
  CONSTRAINT shop_car_ibfk_1 FOREIGN KEY (goods_id) REFERENCES goods (id),
  CONSTRAINT shop_car_ibfk_2 FOREIGN KEY (user_id) REFERENCES user_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX goods_id
  ON shop_car (goods_id);
CREATE INDEX user_id
  ON shop_car (user_id);
CREATE TABLE orders_info
(
  id         VARCHAR(40) PRIMARY KEY NOT NULL,
  store_id   BIGINT(20),
  user_id    BIGINT(20),
  goods_id   VARCHAR(40),
  status     INT(11),
  goods_num  BIGINT(20),
  addr_start VARCHAR(70),
  addr_end   VARCHAR(70),
  express_id VARCHAR(40),
  CONSTRAINT orders_info_ibfk_1 FOREIGN KEY (store_id) REFERENCES store_info (id),
  CONSTRAINT orders_info_ibfk_2 FOREIGN KEY (user_id) REFERENCES user_info (id),
  CONSTRAINT orders_info_ibfk_3 FOREIGN KEY (goods_id) REFERENCES goods (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX goods_id
  ON orders_info (goods_id);
CREATE INDEX store_id
  ON orders_info (store_id);
CREATE INDEX user_id
  ON orders_info (user_id);
CREATE TABLE manager
(
  id       INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username VARCHAR(20),
  password VARCHAR(20)
)
  DEFAULT CHARSET = "utf8";
CREATE TABLE store_user
(
  id         BIGINT(20) PRIMARY KEY NOT NULL,
  name       VARCHAR(20),
  idnumber   VARCHAR(30),
  store_name VARCHAR(40),
  type       VARCHAR(20),
  business   VARCHAR(16),
  tax        FLOAT,
  store_id   BIGINT(20),
  CONSTRAINT store_user_ibfk_1 FOREIGN KEY (store_id) REFERENCES store_info (id)
)
  DEFAULT CHARSET = "utf8";
CREATE INDEX store_id
  ON store_user (store_id);
ALTER TABLE orders_info
  ADD COLUMN addr_id VARCHAR(40);
ALTER TABLE orders_info
  ADD FOREIGN KEY (addr_id) REFERENCES address_info (id);
ALTER TABLE store_user
  DROP COLUMN tax;
ALTER TABLE user_ext
  DROP COLUMN id_number;
ALTER TABLE store_user
  DROP COLUMN business;