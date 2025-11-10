-- Ensure schema exists
CREATE SCHEMA IF NOT EXISTS public;

-- Users table
CREATE TABLE public.users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(150) NOT NULL,
    CONSTRAINT uq_users_login UNIQUE (login)
);

-- Locations table
CREATE TABLE public.locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    user_id BIGINT NOT NULL,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    CONSTRAINT fk_locations_user FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT no_duplicated_locations UNIQUE (user_id, longitude, latitude)
);

-- Sessions table
CREATE TABLE public.sessions (
    id UUID PRIMARY KEY,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_sessions_user FOREIGN KEY (user_id) REFERENCES public.users(id)
);
