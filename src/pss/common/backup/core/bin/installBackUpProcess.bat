REM SET ROOT=C:\javaworkspace\pss\bin
SET ROOT=C:\Eclipse\workspace\pss\bin
SET JARDIR=%ROOT%/lib

SET CP=%ROOT%\WEB-INF\classes;%ROOT%/WEB-INF/lib/cocoon/*;%ROOT%/WEB-INF/lib/*

ECHO starting PASE Server
SET JVMOPTIONS=-Xms64m -Xmx256m
@ECHO OFF 
java -cp %CP%  %JVMOPTIONS% pss.lexpase.core.service.PaseManager install
