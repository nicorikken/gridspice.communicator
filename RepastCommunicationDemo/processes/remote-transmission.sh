#! /bin/bash
# script to receive messages from Repast Process
ant -f /Users/jimmydu/git/gridspice.communicator/build.xml -Dlocal=$1 -Dtarget=$2 RemoteTransmission
