#!/bin/sh

FINDNAME=$0
while [ -h $FINDNAME ] ; do FINDNAME=`ls -ld $FINDNAME | awk '{print $NF}'` ; done
SERVER_HOME=`echo $FINDNAME | sed -e 's@/[^/]*$@@'`
unset FINDNAME

if [ "$SERVER_HOME" = '.' ]; then
   SERVER_HOME=$(echo `pwd` | sed 's/\/bin//')
else
   SERVER_HOME=$(echo $SERVER_HOME | sed 's/\/bin//')
fi


case $1 in
start)

    JAVA_OPTS="-server -XX:+HeapDumpOnOutOfMemoryError "
    
    shift
    ARGS=($*)
    for ((i=0; i<${#ARGS[@]}; i++)); do
        case "${ARGS[$i]}" in
        -D*)    JAVA_OPTS="${JAVA_OPTS} ${ARGS[$i]}" ;;
        -Module*) MODULE="${ARGS[$i+1]}" ;;
        -Heap*) HEAP_MEMORY="${ARGS[$i+1]}" ;;
        -JmxPort*)  JMX_PORT="${ARGS[$i+1]}" ;;
        -HttpPort*)  HTTP_PORT="${ARGS[$i+1]}" ;;
		-Env*)  PROFILE_ENV="${ARGS[$i+1]}" ;;
          *) parameters="${parameters} ${ARGS[$i]}" ;;
        esac
    done
   
    if [ "$HEAP_MEMORY" != '' ]; then
        JAVA_OPTS="${JAVA_OPTS} -Xms$HEAP_MEMORY -Xmx$HEAP_MEMORY"
    fi
   
    JAVA_OPTS="${JAVA_OPTS} -Duser.dir=$SERVER_HOME"
    
    if [ "$JMX_PORT" != '' ]; then
        JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=${JMX_PORT}"
    fi
	
	if [ "$PROFILE_ENV" != '' ]; then
        PROFILE_ENV="--spring.profiles.active=${PROFILE_ENV}"
    fi

    echo  "Starting ${MODULE} ... "
    if [ "$HTTP_PORT" = '' ]; then
        nohup java $JAVA_OPTS -jar ${SERVER_HOME}/bin/${MODULE}.jar ${PROFILE_ENV} &
    else
        nohup java $JAVA_OPTS -jar ${SERVER_HOME}/bin/${MODULE}.jar --server.port=${HTTP_PORT} ${PROFILE_ENV} &
    fi
    echo STARTED
    ;;

stop)

    shift
    ARGS=($*)
    for ((i=0; i<${#ARGS[@]}; i++)); do
        case "${ARGS[$i]}" in
        -Module*) MODULE="${ARGS[$i+1]}" ;;
          *) parameters="${parameters} ${ARGS[$i]}" ;;
        esac
    done
    
    echo "Stopping ${MODULE} ... "
    PROID=`ps -ef|grep ${MODULE}.jar|grep -v grep|awk '{print $2}'`
	if [ -n "$PROID" ]; then
  		echo "Kill process id - ${PROID}"
  		kill -9 ${PROID}
  		echo STOPPED
	else
  		echo "No process running."
	fi
    ;;

    
esac

exit 0
