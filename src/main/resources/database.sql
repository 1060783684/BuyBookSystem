CREATE TABLE evaluation_info
(
    goods_id VARCHAR(20) NOT NULL,
    user_id BIGINT(20) NOT NULL,
    evaluate ENUM('NO', 'YES'),
    descs TEXT,
    img_1 VARCHAR(30),
    img_2 VARCHAR(30),
    img_3 VARCHAR(30),
    CONSTRAINT `PRIMARY` PRIMARY KEY (goods_id, user_id),
    CONSTRAINT evaluation_info_ibfk_1 FOREIGN KEY (goods_id) REFERENCES goods_info (id),
    CONSTRAINT evaluation_info_ibfk_2 FOREIGN KEY (user_id) REFERENCES user_info (id)
);
CREATE INDEX user_id ON evaluation_info (user_id);
CREATE TABLE goods_info
(
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    store_id BIGINT(20),
    name VARCHAR(50),
    descs TEXT,
    cost FLOAT,
    good_est BIGINT(20),
    bad_est BIGINT(20),
    sell_num BIGINT(20),
    num BIGINT(20),
    img VARCHAR(30),
    ischeck ENUM('NO', 'YES'),
    CONSTRAINT goods_info_ibfk_1 FOREIGN KEY (store_id) REFERENCES store_info (id)
);
CREATE INDEX store_id ON goods_info (store_id);
CREATE TABLE orders_info
(
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    user_id BIGINT(20),
    goods_id VARCHAR(20),
    status ENUM('WAIT_PAY', 'WAIT_DELIVERY', 'WAIT_RECEIPT', 'WAIT_EVALUATION'),
    goods_num BIGINT(20),
    addr_start VARCHAR(10),
    addr_start_ext TEXT,
    addr_end VARCHAR(10),
    addr_end_ext TEXT,
    CONSTRAINT orders_info_ibfk_1 FOREIGN KEY (user_id) REFERENCES user_info (id),
    CONSTRAINT orders_info_ibfk_2 FOREIGN KEY (goods_id) REFERENCES goods_info (id)
);
CREATE INDEX goods_id ON orders_info (goods_id);
CREATE INDEX user_id ON orders_info (user_id);
CREATE TABLE shop_car
(
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    goods_id VARCHAR(20),
    user_id BIGINT(20),
    goods_num BIGINT(20),
    deleted INT(11),
    CONSTRAINT shop_car_ibfk_1 FOREIGN KEY (goods_id) REFERENCES goods_info (id),
    CONSTRAINT shop_car_ibfk_2 FOREIGN KEY (user_id) REFERENCES user_info (id)
);
CREATE INDEX goods_id ON shop_car (goods_id);
CREATE INDEX user_id ON shop_car (user_id);
CREATE TABLE store_info
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    descs VARCHAR(30),
    name VARCHAR(20),
    img VARCHAR(60),
    addr VARCHAR(10),
    user_id BIGINT(20),
    visit_num BIGINT(20),
    ischeck ENUM('NO', 'YES'),
    CONSTRAINT store_info_ibfk_1 FOREIGN KEY (user_id) REFERENCES user_info (id)
);
CREATE INDEX user_id ON store_info (user_id);
CREATE TABLE user_ext
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    name VARCHAR(10),
    id_num VARCHAR(20),
    phone VARCHAR(11),
    CONSTRAINT user_ext_ibfk_1 FOREIGN KEY (id) REFERENCES user_info (id)
);
CREATE TABLE user_info
(
    id BIGINT(20) PRIMARY KEY NOT NULL,
    name VARCHAR(16),
    password VARCHAR(16)
);
CREATE UNIQUE INDEX name ON user_info (name);
CREATE TABLE address_info
(
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    username VARCHAR(10),
    mail VARCHAR(30),
    user_id BIGINT(20),
    addr TEXT,
    CONSTRAINT address_info_ibfk_1 FOREIGN KEY (user_id) REFERENCES user_info (id)
);
CREATE INDEX user_id ON address_info (user_id);
