
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

# Performance test
java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="./heapdump"  -XX:OnOutOfMemoryError="kill -9 %p" -XX:+CrashOnOutOfMemoryError -XX:+ExitOnOutOfMemoryError -XX:+UseG1GC -XX:+UseStringDeduplication -jar ./target/sptemplate.jar

# print jvm env options
java -XX:+PrintFlagsFinal -version 

# show threads
ps -p {process} -lfT | wc -l

echo 100000 > /proc/sys/kernel/threads-max

# for 64 bit
sysctl kernel.pid_max
sysctl -w kernel.pid_max=4194303 






