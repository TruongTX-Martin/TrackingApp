#!/usr/bin/env bash
echo "Begin"
echo "copy GWT.xml file"
cordovaProject="/home/truongtechno/MyProject/CordovaProject/AppTracker"
gwtProject="/home/truongtechno/MyProject/TrackingApp/TrackingApp"

sudo apt-get install oracle-java8-set-default
cd $cordovaProject	
#cordova platform remove android
#cordova platform add android
cordova build android
cd /home/truongtechno/MyProject/CordovaProject/AppTracker/platforms/android/build/outputs/apk
adb install android-debug.apk
sudo apt-get install oracle-java7-set-default
echo "Done! enjoy result ^^"