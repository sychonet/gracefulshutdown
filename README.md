# Graceful shutdown of spring boot applications

Objective of this short project is to devise a mechanism to gracefully shutdown services using Spring Boot version less than 2.3 when they receive SIGTERM while the pod is terminating in k8s.

### Command used to send SIGTERM while running tests

kill -s SIGTERM <pid>

