# Origin Destination
- Architecture <br>
![garage](https://user-images.githubusercontent.com/22782834/90405684-657a6580-e0a4-11ea-94ca-1b92429965ba.png)

# API Documentation
## Technology stack
  - Java 11
  - Spring Boot 2+
  - Angular 13
  - Gradle Build
  - Environment: Embedded Tomcat
  
## Instructions to run
Two seperate terminals needed: <br>
   - Need to have git installed. Clone the project: git clone https://github.com/vishnuvuyyur1/origin-destination.git <br>
   Terminal-1:
   - From the repository root folder origin-destination: then 
   -  cd original-case
   -  ./gradle npmInstall or gradle npmInstall(windows)
   -  (optional)./gradlew build or gradle build 
   -  /.gradlew bootrun or gradle bootrun (windows)
   -  http://localhost:9000 <br>
   Terminal-2:
   - From the repository root folder origin-destination: then 
   -  cd metrics
   -  (optional)./gradlew build or gradle build
   -  /.gradlew bootrun or gradle bootrun (windows) <br>
   Terminal-3:
   - Run mock service
   
## Output:


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
