set path=C:\Program Files (x86)\Java\jdk1.7.0_51\bin;%path%
set jars.path=D:\Workspace\SimpleGatewayServer

set app.lib=%jars.path%\.;%jars.path%\lib\jpos-ee-1.6.9.jar;%jars.path%\lib\log4j-1.2.15.jar;%jars.path%\target\SimpleGatewayServer-0.0.1.jar

echo "Starting Simple Gateway Server by Sigit"
echo

java -classpath %app.lib% org.jpos.q2.Q2
pause
