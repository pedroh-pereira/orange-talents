CREATE SEQUENCE IF NOT EXISTS seq_customers
    INCREMENT 1
    MINVALUE 1
    START 1
    CACHE 1;

CREATE TABLE IF NOT EXISTS customers (
  customer_id SERIAL NOT NULL constraint customer_pk primary key,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(60) NOT NULL,
  cpf VARCHAR(14),
  birthday timestamp NOT NULL,
  status NUMERIC(1) NOT NULL,
  street VARCHAR(120),
  number INTEGER,
  complement VARCHAR(50),
  neighborhood VARCHAR(70),
  city VARCHAR(70),
  state VARCHAR(2),
  zip_code NUMERIC(8)
);

ALTER TABLE IF EXISTS customers
	ADD CONSTRAINT uk_customer_name UNIQUE (name);

ALTER TABLE IF EXISTS customers
	ADD CONSTRAINT uk_customer_cpf UNIQUE (cpf);

CREATE SEQUENCE IF NOT EXISTS seq_accounts
    INCREMENT 1
    MINVALUE 1
    START 1
    CACHE 1;

CREATE TABLE IF NOT EXISTS accounts (
  account_id SERIAL NOT NULL constraint account_pk primary key,
  customer_id bigint constraint fk_customers references customers,
  agency NUMERIC(6) NOT NULL,
  agency_digit NUMERIC(1) NOT NULL,
  number NUMERIC(6) NOT NULL,
  account_digit NUMERIC(1) NOT NULL,
  balance MONEY NOT NULL,
  status NUMERIC(1) NOT NULL
);

ALTER TABLE IF EXISTS accounts
	ADD CONSTRAINT uk_account_number UNIQUE (number);