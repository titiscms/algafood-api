# The image of this application will use the image below as a basis.
FROM openjdk:11-jre-slim

# Directory where some instructions will be executed below.
WORKDIR /app

# Parameter to define a variable at the time of building
ARG JAR_FILE

# This parameter receives two values
# The first value is the source of the application's jar file. 
# The second value is the destination for the application copy.
COPY target/${JAR_FILE} /app/api.jar

# Information on which port the container will listen to.
# This parameter does not publish the port.
EXPOSE 8080

# Standard command when the container is started.
# It is not necessary to inform the path, because these commands will be inside the WORKDIR.
CMD ["java", "-jar", "api.jar"]






