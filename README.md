# B-DEV-500-NCY-5-1-cardgames-hugo.jeanmaire

## Introduction

The purpose of this project is to familiarize you with the software platform you have chosen (Java, .NET,
node.js) through the creation of a dashboard.
To do this, you need to implement a web application that works like Netvibes.

## Features

    * Register User on our Application
    * The registered user then confirms their enrollment on the application before being able to use it
    * The application then asks the authenticated user to subscribe to Services 
    * Each Service offers Widgets
    * The authenticated user composes his Dashboard by inserting previously configured widget instances
    * A Timer allows to refresh the information displayed by the different widget instances present on the
      Dashboard
      
## Work Group

The project is to be carried out in a group. Validation of the associated unit will take into account not only
the quality of the work accomplished but also the quantity of available features.
Here is the minimum expected configuration for a group of X students:

    * Let NBS be the number of Services supported by your web application
    * NBW is the total number of Widgets supported by all available Services
    
The following conditions must be respected:

    * NBS >= 1 + X
    * NBW >= 3 * X


## Our Services

We have 5 Services:

    * CoinMarketCap
    * Github (Oauth)
    * Spotify (Oauth)
    * Twitter (Oauth)
    * Twitch (OAuth)
    
We have 12 widgets like search for repository (Github), search for artist (Spotify)
search for user or trends (Twitter), etc..

## Run Project

To run this project you need to have npm installed.

For the back, you just need to:

```shell
$ gradle run
```

For the front, you just need to:

```shell
$ ng serve
```

To check Unit Test for backend, you need to run this:

```shell
$ gradle test
```

## Adding new Services/ widgets

You just need to push a new service in Cloud Firestore (Firebase) with this associated widgets.
Next, you need to add it to front, by creating a general component and subcomponent
foreach widgets.
Next, just add it to backend with your request in your controller by
a routing like this:
> /services/:name/login

> /services/:name/login/callback

and other routing needed.

## Group

Alex Cayotte
Benjamin Kuhnel
Hugo Jeanmaire
