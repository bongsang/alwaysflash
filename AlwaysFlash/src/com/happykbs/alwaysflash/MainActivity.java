/**
 * @author happykbs (Bongsang Kim)
 * 
 * 2014.9.18 
 * This is my first android app in my life.
 * I'm so proud of me and happy.
 * I will do startup soon.
 * See you, everyone in the world.
 * 
 * Cheers,
 * Bongsang
 */
 
package com.happykbs.alwaysflash;

import com.happykbs.util.CameraUtil;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
	private final static String TAG = "HAPPYKBS[MAIN]";
	private Camera mCamera;
	private Camera.Parameters mCameraParams;
	private CameraUtil cameraUtil = new CameraUtil();
	private boolean isSwitchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Camera and Parameters Initializing
        cameraInitilize();

        setContentView(R.layout.activity_main);

        Switch flashSwitch = (Switch)findViewById(R.id.flashSwitch);
        flashSwitch.setOnCheckedChangeListener(this);
        flashSwitch.setChecked(true);
    }
    
    private void cameraInitilize() {
        if(cameraUtil.checkCameraHardware(this) == false) {
        	Log.e(TAG, "Camera hardware is not supported.");
        	Toast.makeText(this, "Camera hardware is not supported", Toast.LENGTH_SHORT).show();
        	finish();
        }
        if((mCamera = cameraUtil.getCameraInstance()) == null) {
        	Log.e(TAG, "Camera instance is not available.");
        	Toast.makeText(this, "Camera instance is not available.", Toast.LENGTH_SHORT).show();
        	finish(); 
        }
        if((mCameraParams = cameraUtil.getCameraParameters(mCamera)) == null) {
        	Log.e(TAG, "Camera parameters is not supported.");
        	Toast.makeText(this, "Camera parameters is not supported.", Toast.LENGTH_SHORT).show();
        	finish();
        }
    }
    
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if((mCamera != null) && (mCameraParams != null)){
			if (isChecked) {
				// Flash On
				isSwitchOn = true;
				mCameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(mCameraParams);
				mCamera.startPreview(); // Very very important option!! Especially LG G2 smartphone needs startPreview() option.
				Log.i(TAG, "Flash light mode is "+ mCameraParams.getFlashMode());
			} else {
				// Flash Off
				isSwitchOn = false;
				mCameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(mCameraParams);
				Log.i(TAG, "Flash light mode is "+ mCameraParams.getFlashMode());
			}
		}
	}

	@Override
    protected void onStop() {
        super.onStop();
        // The activity is about to be destroyed.
        if(isSwitchOn == false) {
        	releaseCamera();
            cameraUtil = null;
            mCamera = null;
            mCameraParams = null;
        	finish();
        	Log.i(TAG, "Finished on Stop.");
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
        	try {
        		mCamera.release();        // release the camera for other applications
        	} catch (Exception e){
        		e.printStackTrace();
        	}
          }
    }
    
	@Override
    protected void onDestroy() {
		super.onDestroy();
        // The activity is about to be destroyed.
        releaseCamera();
        cameraUtil = null;
        mCamera = null;
        mCameraParams = null;
        finish();
        Log.i(TAG, "Finished on Destroy.");
    }
}