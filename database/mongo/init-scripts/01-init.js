// MongoDB initialization script for Full Stack Java Developer Toolkit
// This script creates collections and inserts sample data

// Switch to the application database
db = db.getSiblingDB('fullstack_dev');

// Create users collection with validation
db.createCollection('users', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      required: ['username', 'email', 'password'],
      properties: {
        username: {
          bsonType: 'string',
          minLength: 3,
          maxLength: 50,
          description: 'Username must be a string between 3 and 50 characters'
        },
        email: {
          bsonType: 'string',
          pattern: '^\\S+@\\S+\\.\\S+$',
          description: 'Email must be a valid email address'
        },
        password: {
          bsonType: 'string',
          minLength: 6,
          description: 'Password must be at least 6 characters'
        },
        firstName: {
          bsonType: 'string',
          maxLength: 100,
          description: 'First name must not exceed 100 characters'
        },
        lastName: {
          bsonType: 'string',
          maxLength: 100,
          description: 'Last name must not exceed 100 characters'
        },
        active: {
          bsonType: 'bool',
          description: 'Active status must be a boolean'
        },
        createdAt: {
          bsonType: 'date',
          description: 'Creation timestamp'
        },
        updatedAt: {
          bsonType: 'date',
          description: 'Last update timestamp'
        }
      }
    }
  }
});

// Create indexes for better query performance
db.users.createIndex({ "username": 1 }, { unique: true });
db.users.createIndex({ "email": 1 }, { unique: true });
db.users.createIndex({ "active": 1 });
db.users.createIndex({ "createdAt": 1 });

// Insert sample users
db.users.insertMany([
  {
    username: 'admin',
    email: 'admin@fullstack.local',
    password: '$2a$10$N9qo8uLOickgx2ZMRZoMye', // bcrypt hash for 'password'
    firstName: 'Admin',
    lastName: 'User',
    active: true,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    username: 'testuser',
    email: 'test@fullstack.local',
    password: '$2a$10$N9qo8uLOickgx2ZMRZoMye',
    firstName: 'Test',
    lastName: 'User',
    active: true,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    username: 'demo',
    email: 'demo@fullstack.local',
    password: '$2a$10$N9qo8uLOickgx2ZMRZoMye',
    firstName: 'Demo',
    lastName: 'User',
    active: false,
    createdAt: new Date(),
    updatedAt: new Date()
  }
]);

// Create products collection for e-commerce example
db.createCollection('products', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      required: ['name', 'price'],
      properties: {
        name: {
          bsonType: 'string',
          minLength: 1,
          maxLength: 200,
          description: 'Product name is required'
        },
        description: {
          bsonType: 'string',
          maxLength: 1000,
          description: 'Product description'
        },
        price: {
          bsonType: 'decimal',
          minimum: 0,
          description: 'Price must be a positive number'
        },
        category: {
          bsonType: 'string',
          description: 'Product category'
        },
        inStock: {
          bsonType: 'bool',
          description: 'Stock availability'
        },
        tags: {
          bsonType: 'array',
          items: {
            bsonType: 'string'
          },
          description: 'Product tags'
        }
      }
    }
  }
});

// Create indexes for products
db.products.createIndex({ "name": "text", "description": "text" });
db.products.createIndex({ "category": 1 });
db.products.createIndex({ "price": 1 });

// Insert sample products
db.products.insertMany([
  {
    name: 'Laptop Computer',
    description: 'High-performance laptop for developers',
    price: 1299.99,
    category: 'Electronics',
    inStock: true,
    tags: ['computer', 'laptop', 'technology'],
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Coffee Mug',
    description: 'Ceramic coffee mug with company logo',
    price: 12.99,
    category: 'Accessories',
    inStock: true,
    tags: ['mug', 'coffee', 'ceramic'],
    createdAt: new Date(),
    updatedAt: new Date()
  }
]);

// Print confirmation
print('Database initialized successfully!');
print('Collections created: users, products');
print('Sample data inserted');
