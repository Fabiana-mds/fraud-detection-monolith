CREATE TABLE risk_report (
    id BIGSERIAL PRIMARY KEY,
    generated_at TIMESTAMP NOT NULL,
    total_transactions BIGINT,
    rejected_transactions BIGINT,
    total_amount_processed DECIMAL(19, 2)
);