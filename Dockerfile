FROM ubuntu

LABEL version="1.0"
LABEL description="Spring-Boot Template"

RUN apt update

RUN apt install -y openjdk-8-jre

COPY ./target/sptemplate.jar /opt/sptemplate/
COPY ./story.txt /opt/sptemplate/

# ENV JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005

EXPOSE 8080 5005

CMD /opt/sptemplate/sptemplate.jar ${MyEnv}

