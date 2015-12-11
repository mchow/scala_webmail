
## Configuration
in WebMail.scala replace your url and key from mailgun
* API_KEY
* BASE_URI


## Compiling Application
should show up in your target directory
target/${scalaVersion}/classes/WebMail.class

## Running Application
run application where directory where it is compiled

for now you can run this in intellij
 - in program argument you can specific the json blob with escaped quotes

### Templates
only two templates
- welcome_email.html
- password_reset.html


Example Json

 """{ \"to\":\"someemail@blahmail.com\", \"subject\":\"Test Email\", \"template\":\"welcome_email.html\"}"""
