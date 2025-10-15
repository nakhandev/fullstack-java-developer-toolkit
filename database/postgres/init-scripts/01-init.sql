-- PostgreSQL initialization script for Full Stack Java Developer Toolkit
-- This script creates the initial database schema and sample data

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on username for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Create index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Create index on active status for filtering
CREATE INDEX IF NOT EXISTS idx_users_active ON users(is_active);

-- Create sample users for testing
INSERT INTO users (username, email, password, first_name, last_name, is_active) VALUES
('admin', 'admin@fullstack.local', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'Admin', 'User', true),
('testuser', 'test@fullstack.local', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'Test', 'User', true),
('demo', 'demo@fullstack.local', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'Demo', 'User', false)
ON CONFLICT (username) DO NOTHING;

-- Create function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create trigger to automatically update updated_at
DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Grant necessary permissions
GRANT ALL PRIVILEGES ON DATABASE fullstack_dev TO fullstack_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO fullstack_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO fullstack_user;

-- Create application-specific schema (optional)
CREATE SCHEMA IF NOT EXISTS app_schema;

-- Move users table to app schema (optional)
-- ALTER TABLE users SET SCHEMA app_schema;

COMMENT ON TABLE users IS 'User accounts for the Full Stack Java Developer Toolkit';
COMMENT ON COLUMN users.username IS 'Unique username for user authentication';
COMMENT ON COLUMN users.email IS 'Unique email address for user communication';
COMMENT ON COLUMN users.is_active IS 'Flag indicating if the user account is active';
