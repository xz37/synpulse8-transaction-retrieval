./mvnw clean package
docker build -t synpulse-transaction-retrieval:0.0.1 ./consumer
docker run -p 8080:8080 synpulse-transaction-retrieval:0.0.1