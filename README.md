[![No Maintenance Intended](http://unmaintained.tech/badge.svg)](http://unmaintained.tech/)
# DEPRECATED
This is no longer supported. You can use this fine tool: https://github.com/alexta69/metube

# youtube-dl web ui

This project extends the youtube-dl (https://rg3.github.io/youtube-dl/) tool with a web ui for remote usage.

### Prerequisites

- installed JDK minimum version 1.8
- installed maven build tool
- installed docker deamon

### Build the web ui

In root of the project execute the following command:

`mvn clean install`

### Build the docker image

In root of the project execute the following command:

`mvn clean install`

Docker image build is a part of the default build lifecycle.

### Start the web ui

In root of the project execut the following command:

`java -jar ytdl-web-ui-app/target/ytdl-web-ui-app-0.0.1-SNAPSHOT.jar`
