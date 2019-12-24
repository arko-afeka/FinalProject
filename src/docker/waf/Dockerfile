FROM ubuntu:disco

RUN apt-get update
RUN apt-get install -y openjdk-11-jre
RUN apt-get install -y tar
RUN apt-get install -y gzip

COPY waf.tar /tmp

RUN tar -xvf /tmp/waf.tar -C /opt

ENV WAF_LISTEN_PORT 8080
EXPOSE 8080
CMD /opt/waf/bin/waf