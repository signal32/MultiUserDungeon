# MultiUserDungeon
Prebuilt binaries are avaliable for the latest version [here](https://github.com/signal32/MultiUserDungeon/releases/tag/v1.0.0).

## Dependencies

The MUD Server makes use of SLF4J for logging abstraction. This is bundled inside pre-built binaries.

This project was built and tested on _OpenJDK 11_ and makes heavy use of components introduced in JDK 9.

## Build from Source

Gradle is used to simplify the build process using the included build file:  *build.gradle*.

The included Gradle Wrapper *gradlew* simplifies the build process.

To build the both server and client binaries:

`$ gradlew buildServer`

`$ gradlew buildClient`

Gradle will locate or download dependencies then compile the application binaries to *build/libs*.

## Run

**Note:** Server must be run prior to starting any clients.

### Server

Run the server much like any standard Jar:

`$ java -jar MUD-Server-1.0-SNAPSHOT.jar`

The server will find and load the configuration file. If initialisation is successful you shall see the following in the terminal:

```
[main] INFO ac.abdn.cs3524.mud.server.MudMainline - Server initialised on localhost:8081
[main] INFO ac.abdn.cs3524.mud.server.MudMainline - Remote registry initialised on localhost:8080
[main] INFO ac.abdn.cs3524.mud.server.MudMainline - Startup complete!
 ```

The server is now ready to accept clients.


### Client

Run the client in the same way as the server:

`$ java -jar MUD-Client-1.0-SNAPSHOT.jar`

The client will find and load the configuration file. It will then attempt to connect to the specified server. If initialisation is successful you shall see the following in the terminal:

```
Client initiated at port 8082
Enter a name and press ENTER to continue:
 ```

The client is now ready to use.
