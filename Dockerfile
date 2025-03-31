FROM eclipse-temurin:21

RUN apt-get update && apt-get -y upgrade

RUN apt-get install -y inotify-tools dos2unix

ENV HOME=/app

RUN mkdir -p $HOME

WORKDIR $HOME