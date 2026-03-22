-- =============================================
-- FLORA 데이터베이스 스키마
-- MySQL 8.0
-- =============================================

CREATE DATABASE IF NOT EXISTS floradb DEFAULT CHARACTER SET utf8mb4;
USE floradb;

-- 1. 회원
CREATE TABLE IF NOT EXISTS users (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(100) UNIQUE NOT NULL,
    password_hash   VARCHAR(255),
    nickname        VARCHAR(50) NOT NULL,
    profile_emoji   VARCHAR(10) DEFAULT '🌿',
    role            VARCHAR(20) DEFAULT 'USER',
    points          INT DEFAULT 0,
    streak_days     INT DEFAULT 0,
    last_check_in   DATE,
    is_active       TINYINT(1) DEFAULT 1,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. 약관
CREATE TABLE IF NOT EXISTS terms (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    content     TEXT,
    required    TINYINT(1) DEFAULT 1,
    version     VARCHAR(10),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. 약관 동의
CREATE TABLE IF NOT EXISTS user_term_consents (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    term_id       BIGINT NOT NULL,
    consented     TINYINT(1) DEFAULT 1,
    consented_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (term_id) REFERENCES terms(id)
);

-- 4. 상품
CREATE TABLE IF NOT EXISTS products (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id         BIGINT,
    name              VARCHAR(100) NOT NULL,
    description       TEXT,
    price             INT NOT NULL,
    original_price    INT,
    stock_quantity    INT DEFAULT 0,
    category          VARCHAR(30),
    badge_type        VARCHAR(20),
    badge_label       VARCHAR(20),
    product_type      VARCHAR(20) DEFAULT 'normal',
    is_group_buy      TINYINT(1) DEFAULT 0,
    group_buy_min_qty INT,
    group_buy_current INT DEFAULT 0,
    rating            DECIMAL(3,2),
    review_count      INT DEFAULT 0,
    is_active         TINYINT(1) DEFAULT 1,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(id)
);

-- 5. 장바구니
CREATE TABLE IF NOT EXISTS cart_items (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INT DEFAULT 1,
    added_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- 6. 주문
CREATE TABLE IF NOT EXISTS orders (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    order_number     VARCHAR(30) UNIQUE NOT NULL,
    total_price      INT NOT NULL,
    status           VARCHAR(20) DEFAULT 'PENDING',
    delivery_address VARCHAR(255),
    recipient_name   VARCHAR(50),
    recipient_phone  VARCHAR(20),
    ordered_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 7. 주문 상세
CREATE TABLE IF NOT EXISTS order_items (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id    BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INT NOT NULL,
    unit_price  INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- 8. 결제
CREATE TABLE IF NOT EXISTS payments (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id          BIGINT NOT NULL,
    pg_transaction_id VARCHAR(100),
    amount            INT NOT NULL,
    method            VARCHAR(30),
    status            VARCHAR(20) DEFAULT 'PENDING',
    paid_at           TIMESTAMP NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- 9. 식물 캘린더
CREATE TABLE IF NOT EXISTS plant_calendars (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id             BIGINT NOT NULL,
    plant_id            VARCHAR(24),
    plant_nickname      VARCHAR(50),
    watering_cycle_days INT,
    watering_next_date  DATE,
    watering_is_done    TINYINT(1) DEFAULT 0,
    repot_date          DATE,
    repot_is_done       TINYINT(1) DEFAULT 0,
    fertilize_date      DATE,
    fertilize_is_done   TINYINT(1) DEFAULT 0,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 10. 성장일기
CREATE TABLE IF NOT EXISTS growth_diaries (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    calendar_id   BIGINT NOT NULL,
    recorded_date DATE NOT NULL,
    memo          TEXT,
    image_url     VARCHAR(500),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (calendar_id) REFERENCES plant_calendars(id)
);

-- 11. 커뮤니티 게시글
CREATE TABLE IF NOT EXISTS community_posts (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    title         VARCHAR(200) NOT NULL,
    content       TEXT,
    category      VARCHAR(30),
    view_count    INT DEFAULT 0,
    is_active     TINYINT(1) DEFAULT 1,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 12. 댓글
CREATE TABLE IF NOT EXISTS comments (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id           BIGINT NOT NULL,
    user_id           BIGINT NOT NULL,
    parent_comment_id BIGINT,
    content           TEXT NOT NULL,
    is_active         TINYINT(1) DEFAULT 1,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES community_posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 13. 정기구독
CREATE TABLE IF NOT EXISTS subscriptions (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT NOT NULL,
    plan               VARCHAR(20) NOT NULL,
    status             VARCHAR(20) DEFAULT 'ACTIVE',
    next_delivery_date DATE,
    delivery_address   VARCHAR(255),
    subscribed_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 14. 기념일 배송
CREATE TABLE IF NOT EXISTS anniversary_deliveries (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT NOT NULL,
    subscription_id    BIGINT,
    recipient_name     VARCHAR(50),
    recipient_phone    VARCHAR(20),
    anniversary_date   DATE,
    handwritten_letter TEXT,
    status             VARCHAR(20) DEFAULT 'SCHEDULED',
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 15. 퀴즈
CREATE TABLE IF NOT EXISTS quizzes (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    question      VARCHAR(300) NOT NULL,
    option_1      VARCHAR(100),
    option_2      VARCHAR(100),
    option_3      VARCHAR(100),
    option_4      VARCHAR(100),
    answer_idx    INT NOT NULL,
    explanation   VARCHAR(500),
    reward_points INT DEFAULT 10,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 16. 퀴즈 응답
CREATE TABLE IF NOT EXISTS quiz_answers (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    quiz_id     BIGINT NOT NULL,
    selected    INT NOT NULL,
    correct     TINYINT(1),
    answered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_quiz_answer (user_id, quiz_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

-- 17. 뱃지
CREATE TABLE IF NOT EXISTS user_badges (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    badge_code VARCHAR(50) NOT NULL,
    badge_name VARCHAR(100),
    earned_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 18. 축제
CREATE TABLE IF NOT EXISTS festivals (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    emoji       VARCHAR(10),
    region      VARCHAR(50),
    start_date  DATE,
    end_date    DATE,
    description VARCHAR(500),
    bg_color    VARCHAR(20)
);

-- 19. 공지사항
CREATE TABLE IF NOT EXISTS notices (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(200) NOT NULL,
    content    TEXT,
    tag        VARCHAR(30),
    is_pinned  TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 20. 쿠폰
CREATE TABLE IF NOT EXISTS coupons (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    code        VARCHAR(50) UNIQUE NOT NULL,
    discount    INT NOT NULL,
    is_used     TINYINT(1) DEFAULT 0,
    expired_at  DATE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- 더미 데이터
-- =============================================

INSERT INTO quizzes (question, option_1, option_2, option_3, option_4, answer_idx, explanation, reward_points) VALUES
('벚꽃의 꽃말은?', '사랑의 고백', '순결한 아름다움', '진실된 마음', '행운', 2, '벚꽃의 꽃말은 순결한 아름다움이에요.', 10),
('반려동물에게 독성이 없는 식물은?', '튤립', '국화', '알로에', '클로버', 4, '클로버는 반려동물에게 안전한 식물이에요.', 10),
('선인장에 물을 주는 적정 주기는?', '매일', '3일마다', '2주~1달마다', '6개월마다', 3, '선인장은 건조한 환경을 좋아해 2주~1달에 한 번이 적당해요.', 10),
('광합성에 필요하지 않은 것은?', '빛', '이산화탄소', '물', '질소', 4, '광합성에는 빛, 이산화탄소, 물이 필요해요.', 10),
('장미의 꽃말은?', '순결', '사랑', '희망', '행복', 2, '빨간 장미의 꽃말은 사랑이에요.', 10);

INSERT INTO notices (title, content, tag, is_pinned) VALUES
('플로라 앱 정식 출시 안내', '2026년 3월부터 플로라 서비스가 정식 출시되었습니다. 많은 이용 부탁드립니다.', '서비스', 1),
('농가 직거래 마켓 오픈', '드디어 농가 직거래 마켓이 오픈되었습니다. 신선한 식물을 직접 만나보세요.', '마켓', 0),
('봄 시즌 특별 이벤트', '봄을 맞이하여 다양한 이벤트를 준비했습니다. 많은 참여 부탁드립니다.', '이벤트', 0);

INSERT INTO festivals (name, emoji, region, start_date, end_date, description, bg_color) VALUES
('진해 군항제', '🌸', '경남 창원', '2026-03-28', '2026-04-06', '국내 최대 벚꽃 축제', '#fde8ef'),
('태안 튤립 축제', '🌷', '충남 태안', '2026-04-12', '2026-05-05', '100만 송이 튤립 테마파크', '#fde8f8'),
('여의도 봄꽃 축제', '🌺', '서울 영등포', '2026-04-03', '2026-04-10', '한강변 봄꽃 축제', '#fff0f3'),
('함평 나비 대축제', '🌼', '전남 함평', '2026-04-25', '2026-05-05', '나비와 꽃이 어우러지는 생태 축제', '#fffde8');