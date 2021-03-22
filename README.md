# Parks Seeker API

Add National Park Service API to run properties or property file

`national.park.service.key=`

and to start the api 

`mvnw.cmd spring-boot:run`

The API spec (OpenAPI specification is acceptable)

`/swagger.html`

How did you decide which technologies to use as part of your solution?

* Maven what I do most of my work is with
* Spring framework what I do most of my work is with 
* DeferredResult for asynchronous request processing
* RestTemplate to talk to National Park Service API
* H2 in memory DB for easy use
* Junit 5 for testing and to get use to it
* Mockito for object mocking  
* lombok for cleaner code
* OpenAPI for documentation 

Are there any improvements you could make to your submission?
* integration test
* filtering the get parks 

What would you do differently if you were allocated more time?
* integration test
* memory caching for data
* some kind of paging 
