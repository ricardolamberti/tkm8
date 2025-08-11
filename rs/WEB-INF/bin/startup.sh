ROOT=/home/ec2-user/production/pcnew/bin/WEB-INF
export ROOT
JARDIR=$ROOT/lib
export JARDIR
echo $ROOT
echo $JARDIR


CP="$ROOT/classes":"$ROOT/lib/*":"$ROOT/lib/cocoon/*"
echo $CP

java -Duser.timezone=America/Buenos_Aires -cp $CP pss.common.scheduler.BizScheduler 
