ALTER TABLE ecommerce.product DROP COLUMN id;

ALTER TABLE ecommerce.product ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();