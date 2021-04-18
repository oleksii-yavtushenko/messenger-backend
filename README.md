# Letter.com - messenger
Text messenger based on Java 11, Spring Boot, JavaFX.
## Project requirements
* Java 11
* Docker
* Maven

## Get started
1. Clone this repository
```bash
git clone https://github.com/volvozachto/KPP.git
```
or
```bash
git clone git@github.com:volvozachto/KPP.git
```
2. Run database in the docker
```bash
docker compose up
```
## Run application

Application has 2 entry points:
* client-side
* server-side

To run *client-side* you have to set up **Run/Debug configuration** in your IDE:

1. Open **Edit Configurations** menu
1. Press the ***+*** to add new configuration
1. Choose *Maven* in the drop-down-list
1. Choose **Working Directory** as ClientSide module
1. In the command line insert next command:
```bash
javafx:run
```
