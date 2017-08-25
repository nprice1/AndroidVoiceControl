# Android Voice Control App

This is a very simplistic android app meant for issuing voice commands to your phone that make simple POST requests to a specified REST endpoint. 
I tried to make it semi-generic to allow anybody to get up and running quickly. 

## Usage

The main screen has two options, `Command` and `Config`. The config button takes you to the list of current commands, on which you can add new commands. 
In order to delete an existing command, just click the item in the list (weird UX I know, trying to make it simple to start). After your command(s) have 
been added, the `Command` button on the main screen can be clicked and a voice prompt will be shown. Just say the command and it will take you to a page
that will print out the response from the server.
