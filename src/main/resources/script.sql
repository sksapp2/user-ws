CREATE TABLE user_tbl (
 user_id BIGINT    NOT NULL AUTO_INCREMENT ,
 mobile_number VARCHAR(20) NOT NULL,
 operator VARCHAR(30) NOT NULL,
 device_number VARCHAR(150),
 country_phone_code VARCHAR(150),
created_date_time TIMESTAMP,
CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);
