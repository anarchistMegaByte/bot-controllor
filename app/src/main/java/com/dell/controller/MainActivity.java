package com.dell.controller;

import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements VlcListener {

    VideoView videoView;
    SurfaceView surfaceView;
//    Button connection;
    ImageView btnup,btndown,btnleft,btnright,btnstop;
    String ssid, key;
    VlcVideoLibrary vlcVideoLibrary;

    String cmd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnup=findViewById(R.id.up);
        btndown=findViewById(R.id.down);
        btnleft=findViewById(R.id.left);
        btnright=findViewById(R.id.right);
        btnstop=findViewById(R.id.stop);
//        connection=findViewById(R.id.connect);
        surfaceView=findViewById(R.id.video_view);
        newLib();
//        videoView=findViewById(R.id.video_view);
//        setVideoStreaming();

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = "F,5";
                // loop while user not enters "bye"
                new DownloadFilesTask().execute();

            }
        });

        btndown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = "B,5";
                // loop while user not enters "bye"
                new DownloadFilesTask().execute();
            }
        });

        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = "L,5";
                // loop while user not enters "bye"
                new DownloadFilesTask().execute();
            }
        });

        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = "R,5";
                // loop while user not enters "bye"
                new DownloadFilesTask().execute();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd = "S,5";
                // loop while user not enters "bye"
                new DownloadFilesTask().execute();
            }
        });

//        connection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WifiConfiguration wifiConfig = new WifiConfiguration();
//                wifiConfig.SSID = String.format("\"%s\"", ssid);
//                wifiConfig.preSharedKey = String.format("\"%s\"", key);
//
//                WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
//                int netId = wifiManager.addNetwork(wifiConfig);
//                wifiManager.disconnect();
//                wifiManager.enableNetwork(netId, true);
//                wifiManager.reconnect();
//            }
//        });
    }

    private void newLib() {
        String videoUrl = "rtsp://192.168.5.1:8554/test.mp4";
        vlcVideoLibrary = new VlcVideoLibrary(this, this, surfaceView);
        vlcVideoLibrary.play(videoUrl);
    }

    private void setVideoStreaming() {
        MediaController mediaController = new MediaController(this);

        String videoUrl = "rtsp://192.168.5.1:8554/test.mp4";

        //videoUrl = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";

        mediaController.setAnchorView(videoView);
        final Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                try {
                    mp.setDataSource(MainActivity.this, uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mp.start();


                //pdialog.dismiss();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {

                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        mp.start();
                    }
                });
            }
        });
    }

    @Override
    public void onComplete() {
        Log.e("Manav", "OnComplete");
    }

    @Override
    public void onError() {
        Log.e("Manav", "OnError");
        vlcVideoLibrary.stop();
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Step 1:Create the socket object for
            // carrying the data.
            Log.e("Inside", "1");
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }

            InetAddress ip = null;
            Log.e("Inside", "3");
            try {
                ip = InetAddress.getByName("192.168.5.1");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            byte buf[] = null;
            String inp = "";
            inp = cmd;


            // convert the String input into the byte array.
            buf = inp.getBytes();

            // Step 2 : Create the datagramPacket for sending
            // the data.
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 5000);

            // Step 3 : invoke the send call to actually send
            // the data.
            Log.e("Inside", "3");
            try {
                ds.send(DpSend);
            } catch (IOException e) {
                e.printStackTrace();
            }


            byte[] buff = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buff, 1024);
            String ab[] = inp.split(",");
            int a=Integer.parseInt(ab[1]);
            for(int i=0; i<a; i++)
            {
                try {
                    ds.receive(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String str = new String(dp.getData(), 0, dp.getLength());
                Log.e("Server messages", str);
            }
            return null;
        }
    }
}
