@ECHO OFF
SET ROOT=C:\terminalClient\bin\WEB-INF\bin
SET JARDIR=%ROOT%\lib

SET CP=%ROOT%\classes;%ROOT%\lib\*;%ROOT%\lib\cocoon\*

ECHO installing Terminal Client
SET JVMOPTIONS=-Xms64m -Xmx256m
@ECHO OFF 
java -cp %CP%  %JVMOPTIONS% pss.common.terminals.connection.client.JTerminalPoolClient -install
