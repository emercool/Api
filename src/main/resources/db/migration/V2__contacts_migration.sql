-- Create a table for contacts with avatar as BYTEA
CREATE TABLE IF NOT EXISTS contacts (
    id UUID PRIMARY KEY,              -- Unique identifier (UUID)
    avatar VARCHAR(255),              -- Path to avatar image
    background VARCHAR(255),          -- Path to background image
    name VARCHAR(100) NOT NULL,       -- Name of the contact
    title VARCHAR(100),               -- Job title
    company VARCHAR(100),             -- Company name
    birthday TIMESTAMP,               -- Birthday timestamp
    address TEXT,                     -- Address
    notes TEXT                        -- Notes (HTML content)
);

-- Create a table for emails, with a foreign key reference to contacts
CREATE TABLE IF NOT EXISTS emails (
    id UUID PRIMARY KEY,              -- Unique identifier for the email record
    contact_id UUID REFERENCES contacts(id) ON DELETE CASCADE,  -- Contact association
    email VARCHAR(255) NOT NULL,      -- Email address
    label VARCHAR(50) NOT NULL        -- Label for the email (e.g., 'Personal', 'Work')
);

-- Create a table for phone numbers, with a foreign key reference to contacts
CREATE TABLE IF NOT EXISTS phone_numbers (
    id UUID PRIMARY KEY,              -- Unique identifier for the phone record
    contact_id UUID REFERENCES contacts(id) ON DELETE CASCADE,  -- Contact association
    country VARCHAR(2) NOT NULL,      -- Country code (ISO format)
    phone_number VARCHAR(50) NOT NULL, -- The phone number itself
    label VARCHAR(50) NOT NULL        -- Label for the phone number (e.g., 'Mobile', 'Work', 'Home')
);

-- Create a table for tags, assuming tags are UUIDs and have many-to-many relationship with contacts
CREATE TABLE IF NOT EXISTS tags (
    id UUID PRIMARY KEY,              -- Tag identifier (UUID)
    name VARCHAR(255) NOT NULL        -- Tag name (e.g., 'Important', 'Family')
);


-- Create a join table for contacts and tags (many-to-many relationship)
CREATE TABLE IF NOT EXISTS contact_tags (
    contact_id UUID REFERENCES contacts(id) ON DELETE CASCADE,  -- Contact reference
    tag_id UUID REFERENCES tags(id) ON DELETE CASCADE,          -- Tag reference
    PRIMARY KEY (contact_id, tag_id)
);

CREATE TABLE IF NOT EXISTS countries (
    id UUID PRIMARY KEY,              -- Unique identifier for the country (UUID)
    iso VARCHAR(2) NOT NULL,          -- ISO country code (e.g., 'af')
    name VARCHAR(100) NOT NULL,       -- Name of the country (e.g., 'Afghanistan')
    code VARCHAR(20) NOT NULL,       -- Country code (e.g., '+93')
    flag_image_pos VARCHAR(100)      -- Position of the flag image (e.g., '-1px -3180px')
);

-- Example index on emails and phone numbers for fast lookup
CREATE INDEX idx_contact_email ON emails(contact_id, email);
CREATE INDEX idx_contact_phone_number ON phone_numbers(contact_id, phone_number);
