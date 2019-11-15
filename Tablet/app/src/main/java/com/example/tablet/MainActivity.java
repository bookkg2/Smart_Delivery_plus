package com.example.tablet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    Button button_on;
    Button button3;
    Button button_off;
    TextView textView, textView2;
    ServerSocket serverSocket;

    //add map setting start
    LinearLayout linearLayoutTmap;
    TMapView tMapView;
    TMapPoint tMapPointEnd;
    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSION_REQUEST_CODE = 100;
    String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };
    //add map setting end
    private InputStream in;
    private OutputStream out;

    boolean flag = true;
    public static String str;
    Server server = null;
    Socket socket, socket2;
    //Map<String, String> nickMap = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_on = findViewById(R.id.button1);
        button3 = findViewById(R.id.button3);
        button_off = findViewById(R.id.button4);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        //add map
        linearLayoutTmap = findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("8fe8578d-7236-4214-82fe-a41d45b6d10b");
        linearLayoutTmap.addView(tMapView);
        tMapPointEnd = new TMapPoint(37.504196,127.048041);
        //임시 목적지 좌표 37.501970, 127.041158

        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        }else{
            checkRunTimePermission();
        }

        GpsThread gpsThread = new GpsThread();
        gpsThread.execute();
        //add map

        try {
            server = new Server(8889);
        } catch (IOException e) {
            e.printStackTrace();
        }


        button_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                String ipAddress = Formatter.formatIpAddress(ip);
                Log.d("ip*******", ipAddress);

            }
        });
    }

    class GpsThread extends AsyncTask<String, String, String>{
        public GpsThread(){

        }

        @Override
        protected void onPreExecute() {
            gpsTracker = new GpsTracker(MainActivity.this);
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onProgressUpdate(String... values) {

            gpsTracker = new GpsTracker(MainActivity.this);
            Toast.makeText(MainActivity.this, "UPDATE", Toast.LENGTH_SHORT).show();
            textView2.setText("");
            String[] arr = values[0].split("#");
            textView2.append("주소: " + arr[0]+"\n");
            textView2.append("X: " + arr[1]+"\n");
            textView2.append("Y: " + arr[2]);

            double x = Double.parseDouble(arr[1]);//latitude, 위도
            double y = Double.parseDouble(arr[2]);//longitude, 경도

            TMapPoint tMapPoint = new TMapPoint(x, y);

            try{
                TMapMarkerItem mapMarkerItem = new TMapMarkerItem();

                mapMarkerItem.setTMapPoint(tMapPoint);
                mapMarkerItem.setName("현재 위치");
                mapMarkerItem.setVisible(TMapMarkerItem.VISIBLE);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_pin);
                mapMarkerItem.setIcon(bitmap);
                mapMarkerItem.setPosition(0.5f,0.5f);

                TMapPolyLine tMapPolyLine = new TMapPolyLine();
                //원래 TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPoint, tMapPointEnd);
                //로 해줬는데 안되서 아래 addLinePoint로 수정했더니 됨

                tMapPolyLine.setLineColor(Color.RED);
                tMapPolyLine.setLineWidth(3);
                tMapPolyLine.addLinePoint(tMapPoint);
                tMapPolyLine.addLinePoint(tMapPointEnd);

                tMapView.addTMapPolyLine("Line1", tMapPolyLine);

                tMapView.addMarkerItem("markerItem", mapMarkerItem);
                tMapView.setCenterPoint(y, x);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            while(flag) {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String address = getCurrentAddress(latitude, longitude);
                publishProgress(address+"#"+latitude+"#"+longitude);
                //publishProgress("주소 : " + address + "\n X: " +latitude + "\n Y: " + longitude);
            }
            /*Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 "
                    + longitude, Toast.LENGTH_LONG).show();*/
            return null;
        }
    }
    public MainActivity() {
    }







    public String getCurrentAddress(double latitude, double longitude){
        //GPS의 X, Y 좌표를 주소로 변환해준다.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try{
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
            Log.d("CHECK_ADDRESS****************", addresses.toString());
        }catch (IOException e){
            //네트워크 문제 발생시
            //Toast.makeText(this,"Geocoder not Available", Toast.LENGTH_LONG).show();
            return "Geocoder not Available";
        }catch(IllegalArgumentException ie){
            //Toast.makeText(this,"Invalid GPS Value", Toast.LENGTH_LONG).show();
            return "Invalid GPS Value";
        }

        if(addresses == null || addresses.size() == 0){
            //Toast.makeText(this, "not Found Address", Toast.LENGTH_LONG).show();
            return "Address not Found";
        }

        Address address = addresses.get(0); //??? Address 클래스 알아보고
        //addresses에 값이 무엇이 들어가는지 확인하기
        return address.getAddressLine(0).toString()+"\n"; // 변환된 주소 문자열 들어있음
    }


    void checkRunTimePermission(){
        //먼저 위치 퍼미션을 가지고 있는지 체크
        int findPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(findPermission == PackageManager.PERMISSION_GRANTED &&
                coarsePermission == PackageManager.PERMISSION_GRANTED){
            //퍼미션을 가지고 있을 경우 위치 정보를 가져올 수 있다.
        }else{ //퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요
            //3-1 사용자가 퍼미션 거부를 한 적이 있는 경우
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[0])){
                Toast.makeText(MainActivity.this, "앱을 실행하려면 위치 접근 권한이 필요", Toast.LENGTH_LONG).show();
                //사용자에게 퍼미션 요청, 요청 결과는 onRequestPermissionResult에서 수신됨
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
            }
            else{
                //4-1) 사용자가 퍼미션 거부를 한 적이 없는 경우 퍼미션 요청
                //요청 결과는 onRequestPermissionResult에서 수신됨
                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    public boolean checkLocationServicesStatus(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //스마트폰 위치는 위치관리자(LocationManager)라는 시스템 서비스가 관리
        //android.location 패키지에 포함되어 있어 위치 정보 확인하는데 필요한 클래스들이 정의되어 있음
        //위치 관리자 객체 참조
        return manager.isProviderEnabled(manager.GPS_PROVIDER)
                || manager.isProviderEnabled(manager.NETWORK_PROVIDER);
        //여기서 GPS_PROVIDER는 WIFI를 통해 받을 때, NETWORK_PROVIDER는 기지국을 통해 받을 때 사용
    }

    private void showDialogForLocationServiceSetting(){
        //이 함수는 위치 관리자 참조를 못한 경우(??)
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정 수정하세요");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE && grantResults.length == permissions.length){
            //요청 코드가 PERMISSION_REQUEST_CODE이고, 요청한 퍼미션 개수만큼 수신될 경우
            boolean check_result = true;

            //아래 반복문에서 모든 퍼미션을 허용했는지 확인
            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false;
                    break;
                }
            }

            if(check_result){
                //위치 값을 가져올 수 있음
            }
            else{ //거부 퍼미션이 있을 경우 앱을 사용할 수 없는 경우 설명해주고 앱 종료
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[1])){
                    Toast.makeText(MainActivity.this, "퍼미션이 거부, 앱을 다시 실행하여 퍼미션을 허용하시오",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "퍼미션이 거부 설정에서 허용하시오", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    private class SerialWriter implements Runnable {
        String data;

        public SerialWriter() {
            // 나 참가할게 메세지임.
            // 이걸 안 해주면 안 됨.
            this.data = ":G11A9\r";
        }

        public SerialWriter(String serialData) {
            /*
             * W28: 데이터를 쏘겠다는 뜻 W28 00000000 000000000000 id data :W28 00000000 000000000000
             * 53 \r
             */
            String sdata = sendDataFormat(serialData);
            System.out.println(sdata);
            this.data = sdata;
        }

        public String sendDataFormat(String serialData) {
            serialData = serialData.toUpperCase();
            char c[] = serialData.toCharArray();
            int cdata = 0;
            for (char cc : c) {
                cdata += cc;
            }

            // 비트연산
            System.out.println("before 0xff : " + cdata);
            cdata = (cdata & 0xFF);
            System.out.println("after 0xff : " + cdata);

            String returnData = ":";
            returnData += serialData + Integer.toHexString(cdata).toUpperCase();
            returnData += "\r";
            return returnData;
        }

        public void run() {
            try {
                byte[] inputData = data.getBytes();
                out.write(inputData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void sendData(String data) {
        SerialWriter sw = new SerialWriter(data);
        new Thread(sw).start();
    }

    class HttpTask extends AsyncTask<String,Void,String>{
        String url;

        public HttpTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String str1= HttpHandler.getString(url);
            return str1;
        }
    }

    class Server {
        boolean flag = true;
        boolean rflag = true;

        BufferedOutputStream oos;
        Map<String,DataOutputStream> map= new HashMap<>();
        Map<String, String> nickMap = new HashMap<>();
        ServerSocket serverSocket;
        OutputStream out2;
        DataOutputStream dout;
        public Server() {}

        public Server(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Start..");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        while(flag) {
                            System.out.println("Server Ready..");
                            socket = serverSocket.accept();
                            System.out.println(socket.toString());
                            new Receiver(socket).start();
                        }
                        System.out.println("Server End..");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(r).start();
        }


        public void sendMsg() throws IOException  {
            Sender sender = new Sender();
            //sender.setMsg(msg);
            sender.start();
        }
        class Sender extends Thread{
            String msg;
            //OutputStream out;
            DataOutputStream dout;

            {
                try {
                    dout = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ;


            public void Sender() {


            }
            public void setMsg(String msg) {
                this.msg = msg;
            }

            public void run() {
                /*Collection<DataOutputStream>
                        col = map.values();
                Iterator<DataOutputStream>
                        it = col.iterator();
                while(it.hasNext()) {
                    try {
                        it.next().writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
                try {
                    dout.writeUTF("saf");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        class Receiver extends Thread{
            Socket socket;
            InputStream in;
            DataInputStream din;

            String ip;

            public Receiver(Socket socket) throws Exception {
                //receiver는 정보를 받아서 객체화해서 보내야한다.
                this.socket = socket;
                in = socket.getInputStream();
                din = new DataInputStream(in);
                out = socket.getOutputStream();
                dout = new DataOutputStream(out);
                ip = socket.getInetAddress().toString();
                map.put(ip, dout);
                System.out.println("map1"+map.size());
                sendMsg();
            }

            public void run() {
                try {

                    while(rflag) {
                        str = din.readUTF();
                        //string ?섎닠??蹂???좊떦?댁＜湲?
                        //swich-case 臾?
                        if(str.equals("q")) {
                            map.remove(ip);
                            System.out.println("map"+map.size());
                            break;
                        }
                        if(Integer.parseInt(str.substring(5,7)) >= 50) {
                            textView.setTextColor(Color.parseColor("#FF0000"));
                        }else{
                            textView.setTextColor(Color.parseColor("#009688"));
                        }

                        textView.setText(str);

                        Log.d("str=================", str);
                        //sendMsg(str);
                    }
                }catch(Exception e) {
                    //e.printStackTrace();
                    System.out.println("ip"+ip);
                    map.remove(ip);
                    System.out.println("map2"+map.size());
                    if(din != null) {
                        try {
                            din.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }finally {
                    if(socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }




}