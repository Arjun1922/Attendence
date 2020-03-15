package com.example.admin.i_attendence;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


class socss{
    static Socket s;
    static PrintWriter pw;
    static String state;
}

class ipadd{
    static String ipaddress;
    static int set=0;
}

public class message extends AsyncTask<String,String,String>{


    DataOutputStream dos;
    PrintWriter pw;

    int i=1;
    @Override
    protected String doInBackground(String... strings) {
        socss.s=null;
        while(socss.s==null)
        {
            try{
                socss.s=new Socket();
                ipadd.ipaddress="192.168.43.197";
                socss.s.connect(new InetSocketAddress(ipadd.ipaddress,60000),100);
                //Log.e("target",ipadd.ipaddress);
                socss.pw = new PrintWriter(socss.s.getOutputStream(),true);
           ipadd.set=1;
                //socss.pw.println("connected");
                socss.pw.println(details.enroll);
                socss.pw.flush();


                // Log.e("target","flushed");


            }
            catch(Exception e)
            {
                  Log.e("target",ipadd.ipaddress);
                i++;
                if(i==255)
                    i=1;

                socss.s=null;
            }


        }

        //  Log.e("target",ipadd.ipaddress);



        return "connected";

    }
}