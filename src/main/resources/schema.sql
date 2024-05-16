
-- DROP TABLE IF EXISTS product;

-- DROP TABLE IF EXISTS stock;

-- DROP TABLE IF EXISTS product_category;


CREATE TABLE IF NOT EXISTS stock (
    id VARCHAR(255) PRIMARY KEY,
    productName VARCHAR(255),
    productStock INT
);
CREATE TABLE IF NOT EXISTS product_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    productCategoryId INT, -- Foreign key referencing product_category_id
    stockId VARCHAR(255), -- Foreign key referencing stockId
    price DOUBLE,
    FOREIGN KEY (productCategoryId) REFERENCES product_category(id),
    FOREIGN KEY (stockId) REFERENCES stock(id)
);