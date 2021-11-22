# Frank's garage
- Architecture <br>
![garage](https://user-images.githubusercontent.com/22782834/90405684-657a6580-e0a4-11ea-94ca-1b92429965ba.png)

# API Documentation
## Technology stack
  - Java 11
  - Spring Boot 2+
  - Basic Authentication
  - Maven Build
  - Environment: Embedded Tomcat
  - Deploymnet: Docker container
  
## Instructions to run
With Docker: <br>
   - Need to have git installed. Clone the project: git clone https://github.com/vishnuvuyyur1/garage.git
   - Need to have docker installed. <br>
   - Command prompt: From insider the project folder mock-api
   - Step1: build the project : docker build -t mock-api-image .
   - Command prompt: From insider the project folder garage-api
   - Step2: build the project : docker build -t garage-api-image .
   - Step3: to run: docker-compose up (pulls image and starts the mongoDB, starts mock-api, garage-api)
   - Base URL: http://localhost:8080/garage/api/v1
   
## Approach:
- A microservice with its own database with a well defined set of functinatlity. And runs in a containerized environment.
- A reactive approach down to the databse to enable the API respond in a non-blocking and event based to improve performance.
- Phase 1 used a mock-api that returns a json from  a json file. The content of json file is : https://api.jsonbin.io/b/5ebe673947a2266b1478d892
- To list all the available cars : Used spring web flux to reactively interact with 3rd party apis (mock-api), using this library enables our client to perform HTTP requests and providing asynchronous behaviour i.e the rest call need not wait till response comes back. Instead when there is a response, a notification will be provided.
- Get Response from mock api data which has cars that are grouped under warehouses and aggregate the results of all warehouses by parallel processing to one list of custom Car POJO.
- By utilizing the parallel processing of data using streams the performance is optimized.
- phase 2 Track the traffic: To track the traffic on 3rd party api, used a counter for each request than can result in either 200, 4XX, 5XX. update the counter based on the result from the 3rd party api(mock-api) in DB.
- phase 3 Allow Frank to add, update, delete car to the store. A collection with Car is used for this purpose.  Used mongo NoSQL Db.
- Reactive Mongo DB NoSQL database enabling a reactive interaction with DB, a nosql Db is document orientend enables storing of data together in documents.

 ## End points:
   Mock API :  http://localhost:8080/mock/garages returns a mock json referred from  https://api.jsonbin.io/b/5ebe673947a2266b1478d892 <br>
   Garage API Base URL: http://localhost:8080/garage/api/v1 <br>
   Basic Authentication<br>
   user name: user <br>
   password: password <br>
  -  Garage API Health check: http://localhost:8080/garage/api/v1/actuator/health
  -  Garage API API Metrics: http://localhost:8080/garage/api/v1/actuator/metrics/http.server.requests

   
  Garage API Operations:
  
  |No| Operation | Endpoint | Method |Phase|
|----|---|---|---|---|
|1| get warehouses  cars   |/warehouses | GET |phase 1|
|2| get 3rd party traffic results | /traffic | GET |phase 2|
|3|  add car | /cars |POST |phase 3|
|4|  update car | /cars |PUT |phase 3|
|5|  delete car | /cars/{id} |DELETE |phase 3|

## 1. get warehouses cars: Interacts with 3rd party API (Mocki ApI)
- URI: /warehouses
- Method: GET
<br>
Request Body : None

Response :
```
[
    {
        "id": 57,
        "year": 1996,
        "model": "Q",
        "make": "Infiniti",
        "price": 28773.14
    },
    {
        "id": 56,
        "year": 1994,
        "model": "del Sol",
        "make": "Honda",
        "price": 18840.96
    },
    {
        "id": 73,
        "year": 2010,
        "model": "Legacy",
        "make": "Subaru",
        "price": 24491.8
    },
    {
        "id": 75,
        "year": 1985,
        "model": "Skyhawk",
        "make": "Buick",
        "price": 10567.27
    },
    .
    .
    .
 ]
  
```
## 2. get traffic results of 3rd party API
- URI: /traffic
- Method: GET
<br>
Request Body

  |Attributes|Type|Allowed values | Required |
|----|---|---|---|
|status|string | 200, 4XX, 5XX, TOTAL| yes|
|trafficCountType|ENUM | MAX,MIN,AVERAGE| yes |
```
{
    "status": "200",
    "trafficCountType": "MAX"
}
```
Response : count: Int, eg 8

## 3. add car
- URI: /cars
- Method: Post
<br>
Request Body

  |Attributes|Type|Validation | Required |
|----|---|---|---|
|id|number | min 1 | yes|
|year|number | min 1500 | yes |
|make|string |any string|yes| 
|model|string| any string |yes |
|price|decimal number | min 1 |yes |
```
{
    "id":1,
    "year": 2002,
    "model": "swift",
    "make": "suzuki",
    "price": 1299.99
}
```
Response 

```
{
    "id":1,
    "year": 2002,
    "model": "swift",
    "make": "suzuki",
    "price": 1299.99
}
```

## 4. update car
- URI: /cars
- Method: Put
<br>
Request Body

  |Attributes|Type|Validation | Required |
|----|---|---|---|
|id|number | min 1 | yes|
|year|number | min 1500 | yes |
|make|string |any string|yes| 
|model|string| any string |yes |
|price|decimal number | min 1 |yes |
```
{
    "id":1,
    "year": 2002,
    "model": "swift",
    "make": "suzuki",
    "price": 4299.99
}
```
Response 

```
{
    "id":1,
    "year": 2002,
    "model": "swift",
    "make": "suzuki",
    "price": 4299.99
}
```
## 5. delete car
- URI: /cars/{id} eg: /cars/1
- Method: DELETE<br>

Response : none

# APP Documentation
## Technology stack
  - Angular 9
  - css
  - Bootstarp 4
  - Type Script
  - npm Build
  - Environment: Browsers (google chrome, firefox etc)
  - Deploymnet: Docker container
  
  ## Instructions to run
With Docker: <br>
   - Need to have git installed. Clone the project: git clone https://github.com/vishnuvuyyur1/garage.git
   - Need to have docker installed. <br>
   Run Mock Api: If not already running from above<br>
   - Command prompt: From insider the project folder mock-api
   - Step1: build the project : docker build -t mock-api-image .
   - Step1: run the api: docker run --name mock-api-container -d -p 8083:8083 mock-api-image <br>
   Run APP <br>
   - Command prompt: From insider the project folder garage-app
   - Step1: build the project : docker build -t garage-app-image .
   - Step1: run the api: docker run --name garage-app-container -d -p 4200:80 garage-app-image
   - Base URL: http://localhost:4200

## Approach:
- A single page application and pure component based approach.  And runs in a containerized environment.
- A service layer to interact with the API to fetch the results. 3rd Party API : http://localhost:8080/mock/garages returns mockdata reference: https://api.jsonbin.io/b/5ebe673947a2266b1478d892
- Phase 1: Display of all cars: car-diplay components displays the cars from franks garage sorted baesd on date added
- Phase 2: show details of a car: car-details compnent displays the details of a particular car
- phase 3: shpping cart: shopping-cart component is display the cars added by user to checkout 
- car-board component: a parent component with two child components car-diaply, shopping-cart and handels the communication between child components. 
- Includes navigation : when a user clicks on view details of a particular car we navigate to anoter route to display the results, and user go back to cars diplay. The reason for this mechanish is to fetch fresh load of cars every time a user navigates to cars display.
- Additional Futures: Search, sort, pagination, control to dispay no of cars 10,25,50 etc 

Results:<br>
- Phase 1<br>
![image](https://user-images.githubusercontent.com/22782834/90391992-5b4d6c80-e08e-11ea-88bd-0d6cf5f68bc5.png)
- phase 2
![image](https://user-images.githubusercontent.com/22782834/90392255-d020a680-e08e-11ea-92b1-26dd3dc1cfae.png)
- phase 3
![image](https://user-images.githubusercontent.com/22782834/90392322-edee0b80-e08e-11ea-9a72-9067dba930fb.png)

## Useful docker commands for independent DB contianers to work with other apis  
 Run Database<br>
   - Step1: pull the image - docker pull mongo
   - Step2: Run the DB - docker run -d -p 27017-27019:27017-27019 --name mongodb mongo <br>
