FROM ubuntu:disco
RUN apt-get update
RUN apt-get install -y wget
RUN apt-get install -y gnupg
RUN sh -c "wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add -"
RUN apt-get install -y software-properties-common
RUN add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/
RUN apt-get install -y adoptopenjdk-11-hotspot
RUN apt-get install -y maven