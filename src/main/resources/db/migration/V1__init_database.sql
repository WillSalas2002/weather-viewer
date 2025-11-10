CREATE TABLE "public".users(
    id SERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(150) NOT NULL,
    UNIQUE (login)
);


CREATE TABLE "public".locations(
    id SERIAL PRIMARY KEY,
    "name" VARCHAR(150),
    userId INT NOT NULL,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id)
);


CREATE TABLE "public".sessions(
    id UUID PRIMARY KEY,
    userId INT NOT NULL,
    expiresAt TIMESTAMP NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id)
);
