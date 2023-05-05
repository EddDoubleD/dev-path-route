# Dev Path Route
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)<br/>

## Build docker
Use the liberica image, depending on the architecture of your runners:
* bellsoft/liberica-openjdk-debian:17.0.6  for *arm64*
* bellsoft/liberica-openjdk-debian:17.0.6-x86_64 for *amd64*

``` bash
# maven build app 
mvn -e clean install
# build docker image 
docker build --build-arg JAR_FILE=target/dev-path-route-1.0.0.jar -t edddoubled/dev_path_route:1.0.0 .
# run docker image
docker run -d --name dev-path-route edddoubled/dev_path_route:1.0.0
```
