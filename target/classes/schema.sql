CREATE TABLE IF NOT EXISTS operation (
    id SERIAL PRIMARY KEY,
    type INT,
    date DATE,
    value DECIMAL,
    cpf BIGINT,
    card VARCHAR(255),
    hour TIME,
    store_owner VARCHAR(255),
    store_name VARCHAR(255)
);
