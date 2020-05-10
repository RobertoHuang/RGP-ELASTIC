FROM docker.elastic.co/elasticsearch/elasticsearch:6.4.3

COPY ./x-pack-core-6.4.3.2.jar /usr/share/elasticsearch/modules/x-pack-core/x-pack-core-6.4.3.jar

ADD ./elasticsearch-analysis-ik-6.4.3.zip /usr/share/elasticsearch/plugins/ik/

WORKDIR /usr/share/elasticsearch/plugins/ik/

RUN unzip elasticsearch-analysis-ik-6.4.3.zip

WORKDIR /usr/share/elasticsearch
