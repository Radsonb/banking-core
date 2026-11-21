CREATE TABLE customers (
    id UUID PRIMARY KEY,
    identity_subject VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING_VERIFICATION',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_customers_identity_subject
        UNIQUE (identity_subject),

    CONSTRAINT ck_customers_full_name
        CHECK (char_length(trim(full_name)) BERWEEN 3 and 150),

    CONSTRAINT ck_customers_status
        CHECK (status IN (
            'PENDING_VERIFICATION',
            'ACTIVE',
            'BLOCKED'
        ))
)