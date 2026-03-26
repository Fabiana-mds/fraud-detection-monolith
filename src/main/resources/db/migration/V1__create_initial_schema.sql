CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    costumer_name VARCHAR(255) NOT NULL,
    base_risk_score DECIMAL(19, 2) DEFAULT 50.00
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(19, 2) NOT NULL,
    base_risk_score DECIMAL(19, 2),
    status VARCHAR(50),
    timestamp TIMESTAMP,
    type VARCHAR(50)
);