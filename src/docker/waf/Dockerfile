FROM ubuntu:disco

RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
RUN apt-get install -y tar
RUN apt-get install -y gzip

COPY waf.tar /tmp

RUN tar -zxvf /tmp/waf.tar -C /opt

CMD /opt/waf/bin/waf