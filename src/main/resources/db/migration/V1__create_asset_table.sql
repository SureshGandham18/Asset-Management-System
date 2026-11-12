CREATE TABLE assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    serial_number VARCHAR(50) NOT NULL,
    asset_type VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_assets_serial_number UNIQUE (serial_number)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_assets_status ON assets (status);

CREATE INDEX idx_assets_type ON assets (asset_type);