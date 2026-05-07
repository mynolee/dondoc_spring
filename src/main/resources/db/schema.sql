CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(50) UNIQUE NOT NULL COMMENT '로그인 ID',
    user_password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    current_pig_level INT DEFAULT 5,
    current_house_level INT DEFAULT 3 COMMENT '현재 집 레벨 (1~5)',
    monthly_income BIGINT NOT NULL COMMENT '일일 예산 계산의 근거',
    target_expense_ratio INT NOT NULL COMMENT '일일 예산 계산의 근거',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE farms (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE farm_members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    farm_id BIGINT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_farm_members_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_farm_members_farm
        FOREIGN KEY (farm_id) REFERENCES farms(id)
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(100),
    type VARCHAR(10) COMMENT 'INCOME, EXPENSE'
);

CREATE TABLE records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    category_id BIGINT,
    amount BIGINT NOT NULL,
    description TEXT,
    memo TEXT,
    record_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_records_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_records_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE monthly_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    target_month VARCHAR(7) COMMENT 'YYYY-MM',
    avg_ratio FLOAT COMMENT '한 달간의 평균 페이스/비율',
    house_level INT,
    CONSTRAINT fk_monthly_history_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);
