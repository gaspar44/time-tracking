FROM openjdk:11.0.12-slim-buster

EXPOSE 8080

RUN useradd timetracker

USER timetracker
ENTRYPOINT [ "./bin/run" ]