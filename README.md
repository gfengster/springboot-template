
mvn clean package

docker build --tag sptemplate:1.0 .

docker stop sp-test
docker rm sp-test

docker run --publish 8080:8080 --cpus="1" --memory="256m" --detach --name sp-test sptemplate:1.0

docker exec -it sp-test /bin/bash 


http://localhost:8080/demo/echo
http://localhost:8080/actuator/health

# Using docker-compose
mvn clean package 

docker-compose build

docker-compose up


#Remote debug port on 5005

#A case for counting the number of characters in each line. 
# url call  
http://localhost:8080/service/calculate/{start line}/{end line}
http://localhost:8080/service/calculate/{end}

# From line 100 to line 200
http://localhost:8080/service/calculate/100/200

#From 1 to 100
http://localhost:8080/service/calculate/100

#Get constant value from application.properties net.gfeng.greatest
http://localhost:8080/service/myvalue


#Create a keystore
# under resources
keytool -genkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore spkeysotre.p12 -validity 365

# http redirect to https
https://localhost:8080/info

# Usefull keytool command
# Create jks
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650 -storepass password
# Create p12
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass password
# List
keytool -list -v -keystore keystore.jks
keytool -list -v -storetype pkcs12 -keystore keystore.p12
# Convert jks to p12
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -deststoretype pkcs12
# Import OpenSSL to p12
keytool -import -alias tomcat -file myCertificate.crt -keystore keystore.p12 -storepass password
# Export to client
keytool -export -keystore keystore.jks -alias tomcat -file myCertificate.crt
# Client import certificate
keytool -importcert -file myCertificate.crt -alias tomcat -keystore $JDK_HOME/jre/lib/security/cacerts


# Performance test
java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="./heapdump"  -XX:OnOutOfMemoryError="kill -9 %p" -XX:+CrashOnOutOfMemoryError -XX:+ExitOnOutOfMemoryError -XX:+UseG1GC -XX:+UseStringDeduplication -jar ./target/sptemplate.jar

