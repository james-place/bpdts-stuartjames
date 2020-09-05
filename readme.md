# BPDTS Technical Test - Stuart James

## Brief

Build an API which calls this API, and returns people who are listed as either 
living in London, or whose current coordinates are within 60 miles of London. 
Then write code that you would use to deploy it. 
Send us a link to a repo or repos containing the API code and the code used to deploy it. 
Commentary in a README.md about how you would scale the system, protect it,
or make it more highly available would be useful. 
Please only spend a couple of hours on this task.
Please also note that we don't want you spending any money on the task. 
If you think there's a risk of that happening, of any cost being incurred on a cloud platform,
please don't take the risk. All we want from you is the code itself.

## Solution

The implementation is a spring boot application that consumes the API given for the test to 
retrieve users in or around London within a range of sixty miles. 

### Building and Running

The project uses maven to manage dependencies, compile and build.

```
mvn clean install
```

The resultant artifact is a standalone java jar application that can be executed on
the command line. 

```
java -jar target/bpdtstest-stuartjames-0.0.1-SNAPSHOT.jar
```

### How to use standalone
The Following url can be used to get the results of the implemented solution.
GET http://localhost:8080/api/bpdts/techtest/users-in-london-or-within-sixty-miles-of-london

### Deploying with Docker.
I've chosen to containerise the application using docker. For easy deployment I've chose 
to create a docker-compose.yml file that will create a runtime container and start up the 
application.

### Application scale
The application can be scaled using --scale option of the "docker-compose up" command
example

```
docker-compose up --scale bpdtstest-stuartjames-app-container=5 -d
```

### High availability
To make the application highly available I would start up multiple instances of the application 
across a cluster of servers.  Continuing to use Docker, I would create a docker swarm cluster.  
I would also introduce a service registry so that running services can self-register and make 
themselves discoverable. The service registry can also serve as a load balancer ensuring that 
traffic is evenly distribute across all the available instances of the service. 

### Securing or protecting the service
To prevent unauthorised access to the endpoint I would add an additional 
Secured API Gateway layer in front on the services.  This gateway would be a single entry point
and will be protected by an identity and access management application like Keycloak. In order to access 
the service a valid token (JWT) will need to be passed in by the client.  

  



    


