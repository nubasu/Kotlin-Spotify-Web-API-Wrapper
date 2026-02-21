# AGENTS.md

## About
A Kotlin Multiplatform (KMP) wrapper for the Spotify Web API.

Designed to be type-safe, coroutine-friendly, and easy to use from Kotlin/JVM, Android, iOS, and other KMP targets.

This project is Test Driven Development.
All non-private functions must has UnitTest.

## API
- Spotify Web API
    - https://developer.spotify.com/documentation/web-api/reference

### Using Library for
#### Web Framework (client)
- Ktor
    - https://ktor.io/docs

#### Json Parser
- Serialization
    - https://kotlinlang.org/docs/serialization.html

### Compile Error Check
run `./gradlew :shared:compileKotlinJvm`


### Run Test
run 
```
./gradlew :shared:testDebugUnitTest
./gradlew :shared:jvmTest
./gradlew :shared:jsBrowserTest
```

### Run Linter
run
```
./gradlew ktlintFormat
```
