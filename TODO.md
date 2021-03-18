# Parks Seeker

## Overview

To complete this assessment, you will need to write a simple REST web service in Java, and provide us the source files to be built.

The purpose of this assessment is to assess your **skills and approach to composing a simple REST web servicep** with the interaction with an givan API feed.

This assessment is expected to take up to **24** hours.

## What to do?

Your goal is to implement a simple REST web service, where users will be able to query parks, get a park details by a park code, add a park as well as update a park by a park code.

Although its a very basic exercise, we will be looking for simple, well-designed, well-commented and tested code in the submission.

You can use whatever frameworks, libraries, task runners and build processes you like. Java is the only requirements.

Please include a `README` with setup instructions, and any other documentation you created as part of your solution.

Also, add very short info for the following to your `README`:

* The API spec (OpenAPI specification is acceptable)
* How did you decide which technologies to use as part of your solution?
* Are there any improvements you could make to your submission?
* What would you do differently if you were allocated more time?

## Requirements

* `GET /parks` - list all parks; optionally accept query parameters e.g. stateCode (US state codes)
* `GET /parks/{park code}` - retrieve details of the park identified by *park code*
* `POST /parks` - create a park and save it in the database
* `PUT /parks/{park code}` - update the existing park identified by *park code*
* JSON request and response

*For the first two points, you will need to use the [National Park Service API](https://www.nps.gov/subjects/developer/api-documentation.htm) to retrieve more parks on the fly.*


## Evaluation Criteria

1. Correctness of solution
2. Java knowledge
3. Test converage
4. Coding style
4. Clean, maintainable code adhering to SOLID principles

## Once you complete implementation
Please send us the link to the hosted repository (e.g. Github, Bitbucket...). Alternatively, you may submit your code as a ZIP file too.