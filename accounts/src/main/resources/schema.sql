CREATE TABLE IF NOT EXISTS `customer` (
    `customer_id` int AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL,
    `mobile_number` varchar(20) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `account` (
    `customer_id` int NOT NULL,
    `account_number` int PRIMARY KEY,
    `account_type` varchar(100) NOT NULL,
    `branch_address` varchar(200) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );
CREATE TABLE IF NOT EXISTS account_transactions (
    `transaction_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `customer_id` BIGINT NOT NULL,
    `transaction_type` VARCHAR(255) NOT NULL,
    `amount` DECIMAL(19, 2) NOT NULL,
    `transaction_date` TIMESTAMP NOT NULL,
    `account_number` BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (account_number) REFERENCES account(account_number)
    );

