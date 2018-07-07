package com.ks.phone.usbcamera;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UsbCameraActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,
        View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Camera mCamera;
    private ImageButton mPlayButton;
    private boolean isRecord;
    private MediaRecorder mMediaRecorder;
    private CamcorderProfile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_camera);
        mPlayButton = (ImageButton) findViewById(R.id.play);
        mPlayButton.setOnClickListener(this);
        ((TextureView) findViewById(R.id.textureview))
                .setSurfaceTextureListener(this);

    }

    private void takePic() {
        if (mCamera != null) {
            //调用抓拍摄像头抓拍
            mCamera.takePicture(null, null, pictureCallback);
        } else {
            Log.e("TAG", "请检查摄像头！");
        }
    }

    private Bitmap mBitmap;
    public Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {


        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i("ygy", "onPictureTaken");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            String picName = df.format(new Date());
            Toast.makeText(getApplicationContext(), "正在保存...", Toast.LENGTH_LONG).show();
            mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            File file = new File("/storage/emulated/0/" + picName + ".jpg");
            try {
                file.createNewFile();
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
                Toast.makeText(getApplicationContext(), "图像保存成功", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        mCamera = Camera.open(2);
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(surface);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("TAG", e.getMessage());
            }
        }
    }

    @Override
    protected void onStop() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onStop();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onClick(View v) {
        if (mCamera == null) {
            return;
        }

        takePic();
    }
}
