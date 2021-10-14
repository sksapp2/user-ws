CREATE TABLE user_tbl (
 user_id    VARCHAR(100) NOT NULL UNIQUE,
 mobile_number VARCHAR(20) NOT NULL,
 country_phone_code VARCHAR(150),
 created_date_time TIMESTAMP,
CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);
