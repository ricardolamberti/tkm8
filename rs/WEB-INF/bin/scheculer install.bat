@ECHO OFF
SET ROOT=C:\production\tkm6\bin\WEB-INF
SET JARDIR=%ROOT%\lib

SET CP=%ROOT%\classes;%ROOT%\lib\*;%ROOT%\lib\cocoon\*

ECHO installing Scheduller
SET JVMOPTIONS=-Xms64m -Xmx256m
@ECHO OFF 
java -cp %CP%  %JVMOPTIONS% pss.common.scheduler.BizScheduler -install
