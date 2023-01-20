# Graceful shutdown of spring boot applications
Objective of this short project is to devise a mechanism to gracefully shutdown services using Spring Boot version less than 2.3 when they receive SIGTERM while the pod is terminating on k8s.

### Command used to send SIGTERM while running tests
`kill -s SIGTERM <pid>`

### Running project in remote development environment using Visual Studio Code
Follow the steps give below to open project in a remote container

1. After loading project open command palette in vscode
2. Make sure that remote container extension is installed in vscode.
3. Select option `Dev Containers: Reopen in Container`

### Creating image for project to deloy on k8s
To create an image we will first generate the `.jar` file for this project in `target` folder. To do that use following command on your terminal inside vscode when the application is running inside remote container.
`mvn package`

Once the jar file has been generated then run following command on your terminal from root directory of this project
`docker build -t gracefulshutdown:latest .`

### Deploying project in kubernetes cluster running on local machine
I have used single node kubernetes cluster provided by Docker desktop for deployment purpose. You can choose other alternatives such as minikube for your local environment.

Run following commands on your terminal from root directory of this project in the given order to deploy the application on k8s:
1. `kubectl config user-context docker-desktop`
2. `kubectl apply -f k8s/namespace.yaml`
3. `kubectl apply -f k8s/deployment.yaml`
4. `kubectl apply -f k8s/service.yaml`

You should now be able to see the pods and service load balancer running in namespace `tiket-demo` by using following command
`kubectl get all --namespace=tiket-demo`

After running these commands your application should be running on `http://localhost:8101`

### Steps to run the test
1. Install k6 by garafana for load testing using Docker command `docker pull grafana/k6` on your terminal.
2. Run test by executing command `docker run --rm -i grafana/k6 run --vus 10 --duration 300s - <k6/script.js` on your terminal.
3. Immeidately after step 2 delete pod by using command `kubectl delete pod <podname> --namespace=tiket-demo` on your terminal.

If you don't find any timeouts while running the test using k6 even after deleting one pod then it proves that the application is shutting down gracefully.