-- Add columns image and gender to USER-table
ALTER TABLE USER ADD (gender VARCHAR(255));
ALTER TABLE USER ADD (image  MEDIUMBLOB);

UPDATE USER SET gender = 'male';