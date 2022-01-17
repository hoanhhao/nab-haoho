DROP
DATABASE IF EXISTS `nab_db`;
CREATE
DATABASE IF NOT EXISTS `nab_db`;
use
nab_db;

DROP TABLE IF EXISTS `nab_products`;
CREATE TABLE `nab_products`
(
    `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45)   NOT NULL,
    `category`   VARCHAR(45) DEFAULT NULL,
    `brand`      VARCHAR(45) DEFAULT NULL,
    `color`      VARCHAR(45) DEFAULT NULL,
    `price`      DECIMAL(9, 2) NOT NULL,
    `quantity`   INT           NOT NULL,
    `created_on` DATETIME    DEFAULT now(),
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `nab_orders`;
CREATE TABLE `nab_orders`
(
    `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
    `product_id` BIGINT(20) NOT NULL,
    `quantity`   INT           NOT NULL,
    `price`      DECIMAL(9, 2) NOT NULL,
    `total`      DECIMAL(9, 2) NOT NULL,
    `created_on` DATETIME DEFAULT now(),
    CONSTRAINT `nab_orders_product_id` FOREIGN KEY (`product_id`) REFERENCES `nab_products` (`id`),
    PRIMARY KEY (`id`)
);


INSERT INTO `nab_products` (name, category, brand, color, price, quantity)
VALUES ('Refrigerator 123', 'HOME_APPLIANCE', 'Toshiba', 'Red', 52000, 10);
INSERT INTO `nab_products` (name, category, brand, color, price, quantity)
VALUES ('Refrigerator 456', 'HOME_APPLIANCE', 'Samsung', 'Blue', 55000, 11);
INSERT INTO `nab_products` (name, category, brand, color, price, quantity)
VALUES ('Refrigerator 789', 'HOME_APPLIANCE', 'Hitachi', 'Green', 55000, 15);
INSERT INTO `nab_products` (name, category, brand, color, price, quantity)
VALUES ('Shoes 123', 'SPORT', 'Adidas', 'Red', 34000, 22);
INSERT INTO `nab_products` (name, category, brand, color, price, quantity)
VALUES ('Shoes 456', 'SPORT', 'Nike', 'Black', 67000, 33);

INSERT INTO `nab_orders` (product_id, quantity, price, total)
VALUES (1, 2, 52000, 104000);
