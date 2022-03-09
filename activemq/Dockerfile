# Using jdk as base image
FROM openjdk:8-jdk-alpine

# Copy the whole directory of activemq into the image
#COPY apache-activemq-5.15.12 /opt/apache-activemq-5.15.12
COPY apache-activemq-5.16.4-bin.tar.gz  amq.tar.gz

RUN tar -xvf amq.tar.gz

RUN chmod -R 777 apache-activemq-5.16.4

# Set the working directory to the bin folder 
WORKDIR apache-activemq-5.16.4/bin

# Start up the activemq server
ENTRYPOINT ["./activemq","console"]