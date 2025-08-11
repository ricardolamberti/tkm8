@ECHO OFF
ECHO Configurando variables de entorno...
SET ZEUSROOT=../..
SET JARDIR=%ZEUSROOT%/Zeus/jar
SET CP=%ZEUSROOT%;%JARDIR%/acj5rt.zip;%JARDIR%/acj5des.zip;%JARDIR%/erdj9xl.jar;%JARDIR%/acme.jar;%JARDIR%/classes12.jar;%JARDIR%/comm.jar;%JARDIR%/j2ee.jar;%JARDIR%/jacob.jar;%JARDIR%/jcfield450K.jar;%JARDIR%/jcgauge450K.jar;%JARDIR%/jmf.jar;%JARDIR%/JData2_0.jar;%JARDIR%/JSQLConnect.jar;%JARDIR%/mediaplayer.jar;%JARDIR%/multiplayer.jar;%JARDIR%/NetCharts.jar;%JARDIR%/mail.jar;%JARDIR%/mailapi.jar;%JARDIR%/pop3.jar;%JARDIR%/smtp.jar;%JARDIR%/imap.jar;%JARDIR%/servlets2.2.jar;%JARDIR%/skinlf.jar;%JARDIR%/sound.jar;%JARDIR%/Compression.jar
ECHO Iniciando Zeus...
SET JVMOPTIONS=-Xms64m -Xmx64m
java -cp %CP% %JVMOPTIONS% Zeus.core.Scheduler.JbDispacher %1 %2 %3 %4 %5 %6 %7 %8 %9
