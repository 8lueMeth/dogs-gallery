# Dog Breeds Gallery
Make your own image gallery from your favorite dog breeds.

## About
An android native application which uses https://dog.ceo/dog-api and fetches all available dog breeds and their images. Written entirely with kotlin and jetpack compose. 

## How it works
This app consists of three main screens. 
- 1st screen shows a list of dog breeds. The user can navigate to their favorite breeds screen by tapping on the FAB button. Parent breeds and Sub breeds are are distinguished by some extra padding in the list.
- 2nd screen is a gallery which shows all the available images for a breed. The user can like/unlike an image by double-tapping on the image.
- 3rd one is a screen that shows user's favorite images from local room databasae with a tag that specifices which breed the image belongs to. The user can remove (unlike) an image by double tapping on the image. There is a filter button on top bar which the user can filter the images result by selecting a breed or clear the filter.

## Project's structure

```code
├── com.example.dogbreeds
│   ├── data
│   ├── database
│   ├── di
│   ├── extension
│   ├── model
│   ├── network
│   ├── ui
│   ├── util 
│   └── DogBreedsApplication.kt
```

`data`:

> Contains the repositoriy interfaces and implementations.

`database`:

> Contains all the files related to room database which includes dao, entity and database class itself.

`di`:

> Contains all the hilt dependency injection modules.

`extension`:

> Contains files for extension functions.

`model`:

> Contains models that is being used across the app.

`network`:

> Contains retrofit api and response data classes.

`ui`:

> Contains all the screens and their viewmodels, navigation graphs and ui components.

`util`:

> Contains util methods.

## Todo

Things need to be done.

- [ ] Offline first functionality.
- [ ] Unit tests.
- [ ] Modularization.
- [ ] Code comments.
- [ ] Re-think about folder structure.
- [ ] Enhance Ui.
- [ ] Create and update application launcher icon.

## Features & Libraries
* Kotlin
* MVVM
* Retrofit
* Hilt
* Jetpack Compose
* Room
* Flow
* Navigation
* Coil
* Coroutines
