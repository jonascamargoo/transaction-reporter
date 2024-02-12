CREATE TABLE IF NOT EXISTS operation (
    op_id SERIAL PRIMARY KEY,
    op_type INT,
    op_date DATE,
    op_value DECIMAL,
    op_cnpj VARCHAR(255),
    op_card VARCHAR(255),
    op_hour TIME,
    op_store_owner VARCHAR(255),
    op_store_name VARCHAR(255)
);