# 1. Use a base image that has Java and Maven pre-installed
FROM maven:3.9-eclipse-temurin-25

RUN java -version
# 2. Install Chrome browser inside the container
RUN apt-get update && apt-get install -y wget gnupg \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt-get update && apt-get install -y google-chrome-stable


RUN mvn dependency:go-offline
# 3. Set the working directory inside the container
WORKDIR /app

# 4. Copy your project files into the container
COPY . .

# 5. Run Maven to download dependencies and run tests
# We use 'clean test' to trigger TestNG
#CMD ["mvn", "clean", "test"]