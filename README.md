
## Configuration
in application.conf replace your url and key from mailgun
* API_KEY
* BASE_URI


## Running Application
sbt run

## Rest Endpoints
GET      /webmail/templates

POST     /webmail/send
Example Json Body

'{ "to":"exampleemail@yahoo.com", "subject":"Test Email", "template":"welcome_email.html" }'

## Authentication and Authorization
need to implement oAuth grants for api access