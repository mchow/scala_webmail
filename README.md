
## Configuration
in WebMail.scala replace your url and key from mailgun
* API_KEY
* BASE_URI


## Compiling Application
should show up in your target directory
target/${scalaVersion}/classes/WebMail.class

## Running Application
run application where directory where it is compiled

spend too much time trying to figure out to package executable jar with commandline with dependency package together...

for now you can run this in intellij
 - in program argument you can specific the json blob with escaped quotes

 example

 """{ \"to\":\"someemail@blahmail.com\", \"subject\":\"Test Email\", \"body\":\"Testing some Mailgun awesomness!\"}"""
