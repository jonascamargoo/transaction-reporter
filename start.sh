docker compose down

# build backend image
docker build -t backend-transaction-reporter:latest ./tr-backend

# build frontend image
docker build -t frontend-transaction-reporter:latest ./tr-frontend

# start environment - removendo caches e subindo os dois de uma vez
docker compose up --build --force-recreate --remove-orphans
