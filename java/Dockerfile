FROM gradle:latest AS BUILD
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle shadowJar || return 0
COPY . .
RUN gradle clean shadowJar

# actual container
FROM adoptopenjdk/openjdk11:alpine-jre
ENV ARTIFACT_NAME=DashboardApi-1.0.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}