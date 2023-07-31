
<h1 align="center">NewsFeed</h1>

## Introduction
NewsFeed is a Native Android Application that fetches news data from CBC News Api and displays the news feed.

## Screenshots

<p align="center"> <img src="docs/ss1.png" width="250"/>  <img src="docs/ss2.png" width="250"/>  <img src="docs/ss3.png" width="250" /> <img src="docs/ss4.png" width="250" /></p>

## Base Features

- [x] App fetches news content from CBC Api and shows in the app with Android View.
- [x] Filterers the news feed by the dynamic "type" field from the Api.
- [x] When App loses network connection it notifies the UI.
- [x] 2-3 Unit and/or UI Tests.
- [x] A Readme file to onboard a developer.

## Bonus Features

- [x] Data persistence.
- [ ] Pagination.

## TO-DO
- [ ] Fix the label bug when saving each News Item.
- [ ] Fix ui when we save each item (when saving data it goes straight to offline mode).
- [ ] More Unit tests with Mockk or Mockito

## Insights
- Based on the scope of project and requirement decided to go with Model- View - Model Architecture.
- Decided not implement dependency injection for the simplicity and maintainability and reduction of overhead in terms of setup and initialization
- Chose recycler view over list view based on it's performance, flexibility, Customizability, Separation of concerns and Integration with DiffUtil
- Used horizontal- recycler view for filtering because of the dynamic nature of "type" data coming from api


## Architecture Diagram - MVVM

![NewsFeed](/Users/febin_rukfan/Desktop/Work/NewsFeed/docs/NEWFEED_MVVM.png "NEWSFEED v1.0")


### Built With

* [Kotlin](http://kotlinlang.org/) - Programming Language
* [Retrofit](https://square.github.io/retrofit/) - REST Client Library
    * Retrofit was used as a way to make the GET Request to the API quicker
      and more efficient
* [Glide](https://bumptech.github.io/glide/) - Image Loading Framework
    * This library was used to load the images from src links obtained from a GET request
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - ORM
    * A persistance library that is used to map objects to database

###### Check Build
```bash
# Checks build and lint
$ ./gradlew build check
```
###### Unit Test
```bash
# Execute local unit tests
$ ./gradlew test
```
###### Instrumented Test
```bash
# Start Android emulator
$ ./emulator -avd <name_of_emulator>
```
###### Execute instrumented tests
```bash
$ ./gradlew connectedAndroidTest

Test results: path_to_app/app/build/reports/androidTests/connected/ directory
```