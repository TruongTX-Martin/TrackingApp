#!/usr/bin/env bash
echo "Begin"
echo "copy GWT.xml file"
cordovaProject="/home/truongtechno/MyProject/CordovaProject/AppTracker"
gwtProject="/home/truongtechno/MyProject/TrackingApp/TrackingApp"
ant -f build.xml


rm -rf $cordovaProject/www/trackingapp
rm -rf $cordovaProject/www/components
rm -rf $cordovaProject/www/css
rm -rf $cordovaProject/www/data
rm -rf $cordovaProject/www/images
rm -rf $cordovaProject/www/js
rm -rf $cordovaProject/www/rollups

mkdir -p $cordovaProject/www/trackingapp/
mkdir -p $cordovaProject/www/components/
mkdir -p $cordovaProject/www/css/
#mkdir -p $cordovaProject/www/data/
mkdir -p $cordovaProject/www/images/
mkdir -p $cordovaProject/www/js/
mkdir -p $cordovaProject/www/rollups/


echo "copying newly compiled folder"
cp -r $gwtProject/war/trackingapp $cordovaProject/www
cp -r $gwtProject/war/components/ $cordovaProject/www
cp -r $gwtProject/war/css/ $cordovaProject/www
#cp -r $gwtProject/war/data/ $cordovaProject/www
cp -r $gwtProject/war/images/ $cordovaProject/www
cp -r $gwtProject/war/js/ $cordovaProject/www
cp -r $gwtProject/war/rollups/ $cordovaProject/www

echo "copy file done"
#cd $cordovaProject
sudo apt-get install oracle-java8-set-default
cd $cordovaProject	
cordova build android
cd /home/truongtechno/MyProject/CordovaProject/AppTracker/platforms/android/build/outputs/apk
adb install android-debug.apk
sudo apt-get install oracle-java7-set-default
echo "Done! enjoy result ^^"