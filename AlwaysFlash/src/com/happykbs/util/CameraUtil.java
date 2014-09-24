package com.happykbs.util;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

public class CameraUtil {
	private final static String TAG = "HAPPYKBS[CAMERA]";
	public CameraUtil() {
		
	}
    /** Check if this device has a camera */
    public boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
        		context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            // this device has a camera
        	Log.i(TAG, "checkCameraHardware=true");
            return true;
        } else {
            // no camera on this device
        	Log.e(TAG, "checkCameraHardware=false["+context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)+"]");
            return false;
        }
    }
    
    public Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            Log.i(TAG, "getCameraInstance=["+c.toString()+"]");
        }
        catch (Exception e){
        	e.printStackTrace();
            // Camera is not available (in use or does not exist)
        	Log.e(TAG, "Camera is not available (in use or does not exist).");
        }
        return c; // returns null if camera is unavailable
    }
    
    public Camera.Parameters getCameraParameters(Camera camera) {
    	Camera.Parameters params = null;
    	try {
    		params = camera.getParameters();
            Log.i(TAG, "getParameters=["+params.toString()+"]");
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	List<String> flashModes = params.getSupportedFlashModes();
        Log.i(TAG, flashModes.toString());
        
       	if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
       		// Flash Torch mode is supported
       		Log.i(TAG, "FLASH_MODE_TORCH is supported.");
       		return params;
       	} else {
       		Log.e(TAG, "FLASH_MODE_TORCH is not supported.");
       		return null;
       	}
    }
}