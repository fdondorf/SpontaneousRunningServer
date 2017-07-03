-- Add columns image and gender to USER-table
ALTER TABLE user ADD (gender VARCHAR(255));
ALTER TABLE user ADD (image  MEDIUMBLOB);

UPDATE user SET gender = 'MALE';