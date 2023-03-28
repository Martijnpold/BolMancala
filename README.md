# Welcome to Mancala!

This little game is written as a bol.com assessment project, and includes a front- and backend for playing Mancala games against other users. To play you can simply invite another user by their email address they used to sign in, and they will receive the invitation on their invitations dashboard.


# Backend

The backend is written using Spring Boot, which is where all the game logic is executed. Any data generated is stored in a MySQL database. Tested using JUnit and Mockito.

[To view swagger documentation you can navigate here!](http://195.201.142.32.nip.io:5000/api/swagger-ui/index.html) or, if no longer live, at [host]/api/ui. 
JSON documentation is also present at [host]/api/docs

# Frontend

The frontend is served through the Spring Boot application and is created in Angular with Angular Material.

# Live Demo
Click the following links for several live demo pages:

 - [Full website](http://195.201.142.32.nip.io:5000/)
 - [Swagger page](http://195.201.142.32.nip.io:5000/api/ui)
 - [JSON API Docs](http://195.201.142.32.nip.io:5000/api/docs)
