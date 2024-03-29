# Fetching latest version of Java
FROM openjdk:14
 
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/pp_project.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "pp_project.jar"]