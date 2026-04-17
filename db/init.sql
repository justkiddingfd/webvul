SET NAMES utf8mb4;
SET time_zone = '+00:00';

CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'student',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS products (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  description TEXT NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  image_url TEXT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_products_name ON products(name);

CREATE TABLE IF NOT EXISTS orders (
  id CHAR(36) PRIMARY KEY,
  user_id CHAR(36) NULL,
  receiver_name VARCHAR(120) NOT NULL,
  receiver_email VARCHAR(255) NOT NULL,
  receiver_address TEXT NOT NULL,
  total_amount DECIMAL(12,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_orders_created_at ON orders(created_at);

CREATE TABLE IF NOT EXISTS order_items (
  id CHAR(36) PRIMARY KEY,
  order_id CHAR(36) NOT NULL,
  product_id CHAR(36) NOT NULL,
  product_name VARCHAR(200) NOT NULL,
  unit_price DECIMAL(12,2) NOT NULL,
  quantity INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_order_items_order_id ON order_items(order_id);

-- [A03-XSS] Stored XSS: content stored raw, displayed without escaping
CREATE TABLE IF NOT EXISTS reviews (
  id CHAR(36) PRIMARY KEY,
  product_id CHAR(36) NOT NULL,
  reviewer_name VARCHAR(120) NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_reviews_product_id ON reviews(product_id);

-- [A07-WeakReset] Token chỉ 6 chữ số, không có expiry, brute-force được
CREATE TABLE IF NOT EXISTS password_reset_tokens (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  token VARCHAR(6) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (id, email, password_hash, role)
SELECT * FROM (
  SELECT UUID(), 'admin@webvul.local', '$2a$10$itJ5SlwPaIfOvkwewzmHuOYsZbKhK5PGtMBpgPePWEuRWnozf7HNq', 'admin'
  UNION ALL SELECT UUID(), 'student@webvul.local', '$2a$10$BS2crDjZFwMZFEziavTjheDXdqcX51nhYKLpxn.vlUVzMdg3QvAgK', 'student'
) AS v
WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO products (id, name, description, price, image_url)
SELECT * FROM (
  SELECT UUID(), 'USB-C Hub 6-in-1', 'Hub đa cổng cho laptop (HDMI/USB/PD).', 590000.00, '/assets/product-images/usb-c-hub.svg'
  UNION ALL SELECT UUID(), 'Tai nghe Bluetooth', 'Tai nghe không dây, pin 30 giờ.', 490000.00, '/assets/product-images/headphones.svg'
  UNION ALL SELECT UUID(), 'Bàn phím cơ', 'Bàn phím cơ layout TKL, switch tactile.', 890000.00, '/assets/product-images/keyboard.svg'
  UNION ALL SELECT UUID(), 'Chuột gaming', 'Chuột nhẹ, cảm biến 26K DPI.', 650000.00, '/assets/product-images/mouse.svg'
  UNION ALL SELECT UUID(), 'Đèn bàn LED', 'Đèn bàn chống chói, 3 nhiệt độ màu.', 320000.00, '/assets/product-images/lamp.svg'
  UNION ALL SELECT UUID(), 'Giá đỡ laptop', 'Giá đỡ nhôm, nâng cao tản nhiệt.', 280000.00, '/assets/product-images/stand.svg'
  UNION ALL SELECT UUID(), 'Cáp sạc USB-C', 'Cáp bền, hỗ trợ sạc nhanh 60W.', 120000.00, '/assets/product-images/cable.svg'
  UNION ALL SELECT UUID(), 'SSD di động 1TB', 'SSD NVMe trong hộp di động USB 3.2.', 1950000.00, '/assets/product-images/ssd.svg'
) AS v
WHERE NOT EXISTS (SELECT 1 FROM products);

