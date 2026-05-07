INSERT IGNORE INTO users (
    id,
    user_id,
    user_password,
    name,
    age,
    current_pig_level,
    current_house_level,
    monthly_income,
    target_expense_ratio,
    created_at
) VALUES
    (1, 'piglet01', 'pw1234', '민수', 27, 5, 3, 3200000, 55, '2026-04-01 09:00:00'),
    (2, 'savepig02', 'pw5678', '지은', 25, 4, 2, 2800000, 60, '2026-04-03 10:30:00'),
    (3, 'farmhero03', 'pw9999', '도윤', 29, 6, 4, 4100000, 50, '2026-04-05 14:15:00');

INSERT IGNORE INTO farms (
    id,
    name,
    created_at
) VALUES
    (1, '절약 농장', '2026-04-01 08:00:00'),
    (2, '저축 마을', '2026-04-02 08:30:00');

INSERT IGNORE INTO categories (
    id,
    name,
    icon,
    type
) VALUES
    (1, '월급', 'salary', 'INCOME'),
    (2, '식비', 'food', 'EXPENSE'),
    (3, '교통', 'bus', 'EXPENSE'),
    (4, '용돈', 'gift', 'INCOME');

INSERT IGNORE INTO farm_members (
    id,
    user_id,
    farm_id,
    joined_at
) VALUES
    (1, 1, 1, '2026-04-01 09:30:00'),
    (2, 2, 1, '2026-04-01 09:40:00'),
    (3, 3, 2, '2026-04-02 11:00:00');

INSERT IGNORE INTO records (
    id,
    user_id,
    category_id,
    amount,
    description,
    memo,
    record_date,
    created_at
) VALUES
    (1, 1, 1, 3200000, '4월 급여', '월급 입금', '2026-04-01', '2026-04-01 09:05:00'),
    (2, 1, 2, 12000, '점심', '회사 앞 김치찌개', '2026-04-02', '2026-04-02 12:10:00'),
    (3, 2, 3, 2500, '버스', '출근 버스', '2026-04-02', '2026-04-02 08:10:00'),
    (4, 3, 4, 50000, '가족 용돈', '생일 축하', '2026-04-05', '2026-04-05 20:20:00');

INSERT IGNORE INTO monthly_history (
    id,
    user_id,
    target_month,
    avg_ratio,
    house_level
) VALUES
    (1, 1, '2026-04', 0.82, 3),
    (2, 2, '2026-04', 0.91, 2),
    (3, 3, '2026-04', 0.76, 4);
