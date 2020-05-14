# Movie-Browse-Sample-App

A sample android app demonstrating usage of The Movie Database (TMDb) API

How to install:

1) Clone repo
2) Import project in Android Studio
3) Add your api key obtained from TMDb to `Application/build.gradle` (check out https://developers.themoviedb.org/3/getting-started/authentication for more details)

Functionality:

- Display list of movies sorted by release date (future movies at the top)
- Filter movies by release date
- Display short movie details: description, overall rating and number of votes contributed to rating

Frameworks used:

- DI: Kodein (https://github.com/Kodein-Framework/Kodein-DI)
- Networking: Retrofit (https://square.github.io/retrofit/), OkHttp (https://square.github.io/okhttp/)
- Json: Gson (https://github.com/google/gson)
- Misc: Google JetPack Components (https://developer.android.com/topic/libraries/architecture), RxJava (https://github.com/ReactiveX/RxJava)

TODO:

- Add more unit tests. Add UI tests
- Improve error handling

Ready to install .apk is available here: https://github.com/grede/Movie-Browse-Sample-App/releases
