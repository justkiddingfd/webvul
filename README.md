# WebVul Shop (Struts2 + MySQL)

Website shopping mẫu phục vụ mục đích học tập và thực hành hardening (không cố ý cài lỗ hổng khai thác).

## Chạy bằng Docker
Yêu cầu: Docker Desktop.

```bash
docker compose up --build
```

Mở:
- HTTPS (self-signed): `https://localhost/`
- HTTP direct (dev): `http://localhost:8080/` (cần compose dev override)

Ghi chú: chứng chỉ HTTPS là self-signed, trình duyệt sẽ cảnh báo. Khi test bằng curl dùng `-k`.

### Dev override (mở port 8080/3306 ra localhost)
```bash
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
```

## Deploy domain (HTTPS public)
1) Trỏ DNS:
- `A` record cho domain (và `www` nếu cần) về IP của server
- Mở port `80` và `443` từ internet vào server

2) Tạo file env (vd: `.env.prod`):
```env
DOMAIN=shop-lab.example.com
ACME_EMAIL=admin@shop-lab.example.com
```

3) Chạy:
```bash
docker compose --env-file .env.prod -f docker-compose.yml -f docker-compose.prod.yml up -d --build
```

## Tự động reset DB (cron)
Script `reset.sh` sẽ dừng stack, xoá volume DB (wipe dữ liệu), dọn uploads, rồi chạy lại compose.

1) Cấp quyền chạy:
```bash
chmod +x /opt/webvul/reset.sh
```

2) Mở crontab:
```bash
crontab -e
```

3) Thêm job chạy mỗi 2 giờ:
```cron
0 */2 * * * cd /opt/webvul && ./reset.sh >> /var/log/webvul-reset.log 2>&1
```

4) Kiểm tra:
```bash
crontab -l
tail -n 100 /var/log/webvul-reset.log
```

## Tài khoản mặc định
- Admin:
  - Email: `admin@webvul.local`
  - Password: `admin123!`
- Student:
  - Email: `student@webvul.local`
  - Password: `student123!`

## Quyền admin
- Admin có thêm mục `Admin` trên header để quản trị sản phẩm.
- Trang quản trị:
  - Danh sách: `https://localhost/admin-products.action`
  - Thêm/Sửa: `https://localhost/admin-product.action`
  - Upload ảnh: chọn file ở form Add/Edit (PNG/JPG/GIF/WEBP, tối đa 5MB)

Nếu bạn đã chạy trước đó và MySQL đã có volume dữ liệu, script `db/init.sql` có thể không được chạy lại. Reset DB:
```bash
docker compose down -v
docker compose up --build
```

## Chạy local (không Docker)
1) Tạo MySQL DB `webvul_shop` và chạy script `db/init.sql`
2) Set env:
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`

Build WAR:
```bash
mvn -DskipTests package
```

Deploy `target/webvul-shop.war` vào Tomcat 9.x.

## CI/CD
Workflow: `.github/workflows/docker-image.yml` build & push image lên GHCR:
- `ghcr.io/<owner>/<repo>/webvul-shop:latest`
- `ghcr.io/<owner>/<repo>/webvul-shop:<sha>`
