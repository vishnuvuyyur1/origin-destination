# Origin Destination  Documentation
## Technology stack
  - Java 11
  - Spring Boot 2+
  - Angular 13
  - Gradle Build
  - Environment: Embedded Tomcat
  
## Instructions to run
3 seperate terminals needed: <br>
   - Need to have git installed. Clone the project: git clone https://github.com/vishnuvuyyur1/origin-destination.git <br>
   ### Terminal-1:
   - From the repository root folder origin-destination perform the operations
   -  cd original-case
   -  ./gradlew clean or gradle clean(windows)
   -  ./gradlew npmInstall or gradle npmInstall(windows)
   -  ./gradlew build or gradle build 
   -  ./gradlew bootRun or gradle bootrun (windows)
   -  http://localhost:9000 <br>
   ### Terminal-2:
   - From the repository root folder origin-destination 
   -  cd metrics
   -  (optional)./gradlew build or gradle build (windows)
   -  ./gradlew bootRun or gradle bootrun (windows) <br>
   -  http://localhost:8000/wallboard <br>
   ### Terminal-3:
   - Run mock service
   
## Output: <br>
 Task1: Fare details (Parallel calls)
![airports](https://user-images.githubusercontent.com/22782834/142881906-cd48d502-4f47-4444-90bc-394894ffa7d5.JPG) <br>
Task2: Metrics (click on metrics btn and navigate to http traces)<br>
![metrics](https://user-images.githubusercontent.com/22782834/142883128-ab2faa92-c290-4bab-bcb4-a82ac20e07d9.png)

 ## End points:
  
    open-case API Base URL: http://localhost:9000/origin-destination 
    open-case API Metrics Dashboard URL: http://localhost:8000/wallboard
    click and explore the dashboard
 
  open-case API Operations:
  
  |No| Operation | Endpoint | Method |Phase|
|----|---|---|---|---|
|1| get airports   | /airports | GET |Task1 |
|2| get faredetails | /fare | GET |Task 1|
<br>
Task 2: Metrics dashboard<br>
Spring Boot Admin server Implemrentation <br>
http://localhost:8000/wallboard <br>

## 1. get airports: Interacts with Mocki ApI
- URI: /airports
- Method: GET
- Eg: http://localhost:9000/origin-destination/airports
<br>
Response :

```
[
    {
        "code": "BBA",
        "name": "location.airport.BBA.long",
        "description": "location.city.BBA.long - location.airport.BBA.long (BBA), Chile"
    },
    {
        "code": "YOW",
        "name": "Ottawa International",
        "description": "Ottawa - Ottawa International (YOW), Canada"
    }
 ]
  
```
## 2. get fareDetails : interacts with mock api with 3 parallel calls 
- URI: /fare/{originCode}/{destinationCOde}
- Method: GET
- Eg: http://localhost:9000/origin-destination/fare/BBA/YOW
<br>

Response

```
{
    "fare": {
        "amount": 102.09,
        "currency": "EUR",
        "origin": "BBA",
        "destination": "YOW"
    },
    "origin": {
        "code": "BBA",
        "name": "location.airport.BBA.long",
        "description": "location.city.BBA.long - location.airport.BBA.long (BBA), Chile"
    },
    "destination": {
        "code": "YOW",
        "name": "Ottawa International",
        "description": "Ottawa - Ottawa International (YOW), Canada"
    }
 }
```
- Three parallel calls to mock API  1. getFare 2. getOriginLocationDetails 3. getDestinationLocationDetails
## 3. Metrics Dashboard <br>
http://localhost:8000/wallboard

- spring boot admin server retrieves http traces which has information about total requests processed and their status of our API
- Get information using /actuator/httptrace 
- The dashboad shows lot of information about mappings, health, metrics etc

