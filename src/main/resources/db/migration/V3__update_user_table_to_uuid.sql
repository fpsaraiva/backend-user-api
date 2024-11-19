ALTER TABLE ecommerce.user DROP COLUMN id;

ALTER TABLE ecommerce.user ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();