ALTER TABLE "public".locations
RENAME COLUMN userId TO user_id;

ALTER TABLE "public".sessions
RENAME COLUMN userId TO user_id;

ALTER TABLE "public".sessions
RENAME COLUMN expiresAt TO expires_at;
