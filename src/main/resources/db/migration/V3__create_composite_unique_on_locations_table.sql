ALTER TABLE locations
ADD CONSTRAINT no_duplicated_locations UNIQUE(user_id, longitude, latitude);
