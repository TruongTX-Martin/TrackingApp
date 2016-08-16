function onBodyLoad() {
	 console.log("onBodyLoad");
    document.addEventListener("deviceready", onDeviceReady, false);
}

function onDeviceReady() {
    console.log("Device ready");
    document.addEventListener("volumedownbutton", onVolumeDownKeyDown, false);
    document.addEventListener("volumeupbutton", onVolumeUpKeyDown, false);
}
function onVolumeDownKeyDown() {
	console.log("on Volume DownKey Down");
	VolumeControl.setVolume(10, onVolSuccess, onVolError);
}

function onVolumeUpKeyDown() {
	console.log("on Volume DownKey up");
	VolumeControl.setVolume(90, onVolSuccess, onVolError);
}

function onVolSuccess(){
    console.log("Volume changed");
}
function onVolError(){
	console.log("Volume error ");
}

function splashShow() {
     navigator.splashscreen.show();
}

function splashHide() {
    navigator.splashscreen.hide();
}