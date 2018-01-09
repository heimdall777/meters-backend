CREATE TABLE users (
  id         SERIAL PRIMARY KEY,
  username   VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL,
  email      VARCHAR(60)  NOT NULL
);

CREATE TABLE unit (
  id      SERIAL PRIMARY KEY,
  unit_cd VARCHAR(50) NOT NULL,
  name    VARCHAR(50)
);

CREATE TABLE energy (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC NOT NULL,
  read_date DATE    NOT NULL,
  unit_id   INTEGER REFERENCES unit (ID),
  user_id   INTEGER REFERENCES users (ID)
);

CREATE TABLE gas (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC NOT NULL,
  read_date DATE    NOT NULL,
  unit_id   INTEGER REFERENCES unit (ID),
  user_id   INTEGER REFERENCES users (ID)
);

CREATE TABLE hot_water (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC NOT NULL,
  read_date DATE    NOT NULL,
  unit_id   INTEGER REFERENCES unit (ID),
  user_id   INTEGER REFERENCES users (ID)
);

CREATE TABLE cold_water (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC NOT NULL,
  read_date DATE    NOT NULL,
  unit_id   INTEGER REFERENCES unit (ID),
  user_id   INTEGER REFERENCES users (ID)
);

CREATE TABLE heating (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC NOT NULL,
  read_date DATE    NOT NULL,
  unit_id   INTEGER REFERENCES unit (ID),
  user_id   INTEGER REFERENCES users (ID)
);