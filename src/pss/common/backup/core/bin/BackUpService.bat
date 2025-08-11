@ECHO OFF
SET ROOT=..
SET JARDIR=%ROOT%/lib

REM SET CP=C:\production\pc\bin\WEB-INF\classes;C:/production/pc/bin/WEB-INF/lib/cocoon/*;C:/production/pc/bin/WEB-INF/lib/*
SET CP=C:/Eclipse/workspace/pss/bin/WEB-INF/classes;C:/Eclipse/workspace/pss/bin/WEB-INF/lib/cocoon/*;C:/Eclipse/workspace/pss/bin/WEB-INF/lib/*

ECHO starting PASE Server
SET JVMOPTIONS=-Xms64m -Xmx256m
@ECHO OFF 
java -cp %CP%  %JVMOPTIONS% pss.lexpase.core.service.PaseManager    

