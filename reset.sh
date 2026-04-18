#!/usr/bin/env bash
set -euo pipefail

APP_DIR="/opt/webvul"
cd "$APP_DIR"

# Dừng stack + xóa volume (wipe DB)
docker compose down -v --remove-orphans

# Wipe uploads nếu bạn bind-mount ./uploads
rm -rf ./uploads/*
mkdir -p ./uploads

# Kéo image mới nhất (nếu dùng registry) và chạy lại
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --pull always --force-recreate