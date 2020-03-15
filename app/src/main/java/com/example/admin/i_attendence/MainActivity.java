package com.example.admin.i_attendence;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


class Conn_Message{
    static String mess;

}



 class MyRunnable implements Runnable {
     String videoPath;
     Object appcon;
     TextView ttt;
    public MyRunnable(Object parameter,Object con,TextView tt) {
videoPath=parameter.toString();
appcon=con;
ttt=tt;
    }

    public void run() {

        File myFile = new File (videoPath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        InputStream in=null;
        try {




                byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = socss.s.getOutputStream();
            //System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + "bytes)");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            DataInputStream dis=new DataInputStream(socss.s.getInputStream());
            socss.s.shutdownOutput();
            int port=socss.s.getPort();
            byte[] msg=new byte[1024];
            String aa="j";

            if(port==50000)
            {

                BufferedReader receiveRead;
                InputStream istream = socss.s.getInputStream();
                receiveRead = new BufferedReader(new InputStreamReader(istream));
                String receiveMessage;

                while((receiveMessage = receiveRead.readLine()) != null) //receive from server
                {
                    Log.e("target", receiveMessage);
                    Conn_Message.mess=receiveMessage;


                }

            }

            socss.s.close();
        }catch (Exception e)
        {
            Log.e("asdasd",e.toString());
        }
    }
}

    public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static int videorequest = 100;

    public static int imagerequest = 200;




       public message mm=null;
        public markattend mk=null;

        private Uri videouri = null;
    private VideoView m;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int IMAGE_PERMISSION_CODE=202;


    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tt=findViewById(R.id.textView3);
        final Handler handler;




        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE);

        checkPermission(
                Manifest.permission.CAMERA,
                IMAGE_PERMISSION_CODE);

        m = findViewById(R.id.videoView);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
              Toast.makeText(getApplicationContext(),Conn_Message.mess,Toast.LENGTH_LONG).show();



                  tt.setText(Conn_Message.mess);



            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Conn_Message.mess=null;
                 mk=new markattend();
                mk.execute();

                Intent videointent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (videointent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(videointent, imagerequest);

                }


            Conn_Message.mess=null;

                new Thread(){

                        public void run(){

                            while(Conn_Message.mess==null);
                            handler.sendMessage(new Message());


                }

                }.start();








                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }else
            if(ContextCompat.checkSelfPermission(MainActivity.this, permission)
                    == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] { permission },
                        requestCode);
            }
        else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            message mm=new message();
            mm.execute();
            Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if (videointent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(videointent, videorequest);

            }

        } else if (id == R.id.nav_gallery) {
            tt.setText(Conn_Message.mess+"  jjjj");


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Toast.makeText(this, "dssada", Toast.LENGTH_SHORT);
        if (requestCode == videorequest && resultCode == RESULT_OK) {

            //mm.doInBackground();


            videouri = data.getData();
            String sourceFilename = videouri.getPath();
           // socss.pw.println(videouri.parse(videouri.getPath()));
           // socss.pw.println(videouri.getPath());
           // socss.pw.println(videouri);
            String videoPath = getRealPathFromURI(videouri);
            //File file= new File(videoPath);
            Runnable r = new MyRunnable(videoPath,getApplicationContext(),tt);
            new Thread(r).start();

        }
        else if (requestCode == imagerequest && resultCode == RESULT_OK)  {

            //mm.doInBackground();



            videouri = data.getData();
            String sourceFilename = videouri.getPath();
            // socss.pw.println(videouri.parse(videouri.getPath()));
            // socss.pw.println(videouri.getPath());
            // socss.pw.println(videouri);
            String videoPath = getRealPathFromURI(videouri);
            //File file= new File(videoPath);
            Runnable r = new MyRunnable(videoPath,getApplicationContext(),tt);
            Thread io=new Thread(r);
            io.start();






        }





        // Log.e("target",videoPath);
            m.setVideoURI(videouri);
            m.start();


        Toast.makeText(this, "play", Toast.LENGTH_SHORT);


        }







    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}

