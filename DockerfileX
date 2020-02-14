ARG maven_image=maven
ARG maven_image_tag=3.6.3-jdk-11
ARG openjdk_image=usgswma/openjdk
ARG openjdk_image_tag=11

FROM ${maven_image}:${maven_image_tag} AS build
LABEL maintainer="gs-w_eto_eb_federal_employees@usgs.gov"
ENV USER=lambda
ENV HOME=/home/$USER

RUN adduser --disabled-password --gecos "" -u 1000 $USER
RUN apt-get install -y curl
RUN curl -sL https://deb.nodesource.com/setup_13.x | bash - && apt-get install -y nodejs

RUN mkdir $HOME/.npm && chmod 777 $HOME/.npm/ && chmod 777 $HOME/

# Add pom.xml and install dependencies
COPY pom.xml /build/pom.xml
WORKDIR /build
RUN mvn -B dependency:go-offline

# Add source code and (by default) build the jar
COPY src /build/src
RUN mvn -B clean package -Dmaven.test.skip=true
