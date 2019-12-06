FROM ubuntu:disco
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
RUN apt-get install -y tar
RUN apt-get install -y gzip
RUN tar -zxvf waf.tar -C /opt

CMD /opt/waf/bin/waf