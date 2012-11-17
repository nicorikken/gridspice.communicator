#! /bin/bash
# script to send to RepastProcess
ant -f /Users/jimmydu/git/gridspice.communicator/build.xml -Dlocal=$1 -Dtarget=$2 RemoteDistribution 
