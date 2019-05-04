git pull
mvn package
sudo java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=5005 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=raspberrypi -jar target/SplineExperiments-1.0-SNAPSHOT-jar-with-dependencies.jar