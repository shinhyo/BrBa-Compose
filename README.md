<h1 align="center"> 🧪 Breaking Bad - Jetpack Compose</h1>

<p align="center">
  <a href='https://developer.android.com'><img src='http://img.shields.io/badge/platform-android-green.svg'/></a>
  <a href="https://kotlinlang.org/docs/whatsnew1920.html"><img src = "https://shields.io/badge/kotlin-2.0.10-blue" /></a>
  <a href="https://developer.android.com/jetpack/compose/bom"><img src = "https://img.shields.io/badge/jetpack%20compose-2024.06.00-brightgreen" /></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg"/></a>
</p>

<p align="center">
  <img src="/gif/0.gif" width="32%"/>
  <img src="/gif/1.gif" width="32%"/>
  <img src="/gif/2.gif" width="32%"/>
</p>

## Tech Stack

- [Jetpack](https://developer.android.com/jetpack)
    - Compose - Define your UI programmatically with composable functions that describe its shape
      and data dependencies.
    - Hilt - Extend the functionality of Dagger Hilt to enable dependency injection.
    - Lifecycle - Build lifecycle-aware components that can adjust behavior based on the current
      lifecycle state
    - Room - Create, store, and manage persistent data backed by a SQLite database.
    - ViewModel - Store and manage UI-related data in a lifecycle conscious.
    - App Startup - initialize components at app startup.
- Clean Architecture (nowinandroid)
- MVVM pattern
- Kotlin Coroutines & Flows
- Material Design 3
  - [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)
- Single Activity
- StaggeredVerticalGrid
- [Gradle Version Catalog](https://docs.gradle.org/7.4/userguide/platforms.html)
- [Retrofit2](https://github.com/square/retrofit)
- [Coil-Compose](https://coil-kt.github.io/coil/compose)
- [Timber](https://github.com/JakeWharton/timber)
- [Haze](https://github.com/chrisbanes/haze)
- [SharedElement](https://developer.android.com/guide/fragments/animate#shared)

## Multi Module

```
├── app
├── core
│   ├── common
│   ├── data
│   ├── database
│   ├── datastore
│   ├── designsystem
│   ├── domain
│   ├── model
│   └── network
└── feature
    ├── bottombar
    ├── detail
    ├── favorite
    ├── list
    ├── main
    └── setting
```

## Module Graphs
![](project.dot.png)
