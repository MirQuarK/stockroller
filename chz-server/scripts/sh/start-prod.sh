#!/bin/sh

FINDNAME=$0
while [ -h $FINDNAME ] ; do FINDNAME=`ls -ld $FINDNAME | awk '{print $NF}'` ; done
SERVER_HOME=`echo $FINDNAME | sed -e 's@/[^/]*$@@'`
unset FINDNAME

cd $SERVER_HOME
./serverctrl.sh start -Heap 2048m -Module ${project.artifactId}-${project.version} -Env prod