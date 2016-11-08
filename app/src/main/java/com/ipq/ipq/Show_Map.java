package com.ipq.ipq;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ipq.ipq.DataBase.DataBaseSqlite;
import com.ipq.ipq.MapContollers.CalcShortRoad;
import com.ipq.ipq.Model.Passenger;
import com.ipq.ipq.Model.Station;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Show_Map extends FragmentActivity implements OnMapReadyCallback {
    //class which show map to driver , show the direction from his address via points of passengers to distention.

    private GoogleMap map;
    public int index2 = 0;
    public String Dis;
    public LatLng Israel=new LatLng(31.0000,35.0000);
    private static InputStream stream = null;
    private static JSONObject jsonObject = null;
    private static JSONArray jsonArray = null;
    private static final String ROUTE = "routes";
    private static final String LEGS = "legs";
    private static final String Des = "distance";
    private ProgressDialog progressDialog;
    Button btn1;
    TextView waiting;
    public  String DIRECATION = "";
    public List<LatLng> Points = null;
    public ArrayList<LatLng> latlngs_viapoints = null;
    Handler callBack, DistanceBack,DeleteCallBack;
    public  final String DELQUERY="http://igortestk.netai.net/DeletePassengers.php";
    String adddriver;
    ArrayList<Passenger> listofPasseengers = null;
    public ArrayList<Station> stationArrayList = null;
    private static String result = "";
    public double distance = 0;
    public int j=0;
    String[] Address;
    String[] TmpAddress = null;
    public int indexmark=0;
    PolylineOptions polylineOptions = null;
    public String Id_Driver="";
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_map);
        /**
         *  init the all options for google map type,zoom, and the initialization of the map fragment;
         */
        DataBaseSqlite sqlite=new DataBaseSqlite(getApplicationContext());
        try {
            //DIRECATION = sqlite.GetDirecation().get(0);
        }catch (Exception ex)
        {
            Log.e("Error",ex.toString());
        }
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Israel, 5));
        btn1 = (Button) findViewById(R.id.showdirecation);
        waiting = (TextView) findViewById(R.id.wait);
        waiting.setVisibility(View.INVISIBLE);

        Intent mapdata = getIntent(); // get list of the passenger to this activity;
        if(mapdata.hasExtra("ListPass")) //check if the data have list of passengers
        {
            listofPasseengers = (ArrayList<Passenger>) mapdata.getSerializableExtra("ListPass");
            adddriver = mapdata.getStringExtra("DriverAdd"); //get the driver address

            Address = new String[listofPasseengers.size() + 1]; //build string with addresses
            size = listofPasseengers.size(); //take size of the address
            Address[0] = adddriver; //add the address of the driver in first place in the array
            for (int i = 0; i < listofPasseengers.size(); i++) //add other passengers
            {
                Address[i + 1] = listofPasseengers.get(i).getAddress();
            }
        }
        DeleteCallBack=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1)
                {
                    Toast.makeText(getApplication(),"דרך צלחה",Toast.LENGTH_LONG).show();
                }
            }
        };
        //handler for get information of the thread from GetDistance function
        DistanceBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what != -1) { //check if the msg is not -1
                    Bundle bd = msg.getData(); //get from meesage fromthe thread the arraylist of stations
                    stationArrayList = (ArrayList<Station>) bd.getSerializable("Stations");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new DistanceTask().execute(stationArrayList); //execture class with function which working in background
                }
            }
        };
        //callback which get data from Showay function
        callBack = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == -1)
                {
                    Toast.makeText(getApplicationContext(), "אירעה שגיאה נסה שנית", Toast.LENGTH_LONG).show();
                }
                Bundle bd = msg.getData();  //get data from the massage which sent from ShowWay function
                Dis = bd.getString("Distance");
                //Time = bd.getString("Time");
                Points = (List<LatLng>) bd.getSerializable("LatLngs"); //get get all points for poly
                latlngs_viapoints = (ArrayList<LatLng>) bd.getSerializable("latlngs_viapoints"); //get points of the passengers and drivers and destination
                Address = bd.getStringArray("address"); //get array adress
                Toast.makeText(getApplicationContext(), " יש לך " + size + " נוסעים", Toast.LENGTH_LONG).show();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngs_viapoints.get(0), 5));  //move camera in the map
                map.animateCamera(CameraUpdateFactory.zoomTo(12)); //zoom camera to passengers
                if (map != null) { //check if the map not null
                    for (int i = 0; i < latlngs_viapoints.size()-1; i++) //loop for show the markers in the map
                    {
                        if (i == 0)
                        {
                            map.addMarker(new MarkerOptions().position(latlngs_viapoints.get(i)).title("Driver " + Address[i]));

                        } else
                        {
                            map.addMarker(new MarkerOptions().position(latlngs_viapoints.get(i)).title(Address[i]));
                        }
                    }
                    // set marker of distention
                    indexmark=Address.length-1;
                    map.addMarker(new MarkerOptions().position(latlngs_viapoints.get(indexmark)).title("College " + Address[indexmark]));
                    polylineOptions = new PolylineOptions(); //show the polylines of the road to pickup and passengers
                    polylineOptions.addAll(Points);
                    polylineOptions.color(Color.RED);
                    polylineOptions.width(7);
                    map.addPolyline(polylineOptions);
                }
            }
        };
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //build alert dialog for answer the driver
                AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
                AlertDialog alertDialog=dialog.create();
                dialog.setIcon(R.drawable.ic_launcher);
                dialog.setMessage("האם אתה מוכן לצאת לדרך?");
                dialog.setTitle("תחילת נסיעה");
                dialog.setPositiveButton("כן",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        waiting.setVisibility(View.VISIBLE);
                        CalcShortRoad.GetDistance(Address[0], Address, DistanceBack);
                        /**
                         * send to GetDistance function which do first init for distance between driver and other passengers
                         *
                         */
                    }
                });
                dialog.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //back to main activity
                        Intent i=new Intent(getApplicationContext(),IpqMain.class);
                        startActivity(i);

                    }
                });

              dialog.show();
                }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {


    }


    public class DistanceTask extends AsyncTask<ArrayList<Station>, String[], String[]>

    {

        @Override
        protected void onPreExecute() {

           //progressbar dialog show for waiting to calculate the way and process the Dijkstra Algorithm

            progressDialog = new ProgressDialog(Show_Map.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("מחשב מסלול נא המתן...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            waiting.setVisibility(View.INVISIBLE);


        }

        @Override
        protected void onPostExecute(String[] aVoid)
        {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            int j = 0;
            if (aVoid.length == 1) //if avoid array is length 1 we show only way from the driver to passenger and to direcation;
            {
                TmpAddress = new String[aVoid.length + 2];
                TmpAddress[0] = adddriver;
                TmpAddress[1] = aVoid[0];
                TmpAddress[2] = DIRECATION;
            } else { //we build new string array which include the all address with the driver and direcation
                TmpAddress = new String[aVoid.length+1];
                for (int i = 0; i < aVoid.length; i++) {
                    TmpAddress[i] = aVoid[i];
                    j = i;
                }
                TmpAddress[j+1] = DIRECATION;
                CalcShortRoad.ShowWay(TmpAddress, callBack); //call the show way for show way in the map


            }

        }

        @Override
        protected String[] doInBackground(ArrayList<Station>... params) {

            /**
             *
             *  this function work in background thread and connect to the internet;
             *
             *
             */
            //enter the address of the final dest it's a collage
            ArrayList<Station> stations = new ArrayList<Station>(); //init arrays list of object station;
            ArrayList<Station> tmp=null;
            Station driver = params[0].get(0); //get the station of driver
            Station tmpStat=null;
            int Size=0;
            params[0].remove(0); //remove the first station of paramas we know is driver
            for (Station s : params[0])
            {
                stations.add(s);
            }
            ArrayList<Station> initstation = new ArrayList<Station>(); //build new ArrayList of Station for InitStation for save the stations
            FindMin Fm = new FindMin(0, params[0].size()); //build object of class FindMin which help find min distance
            try {
                Fm.SetNewAdress(driver.getNameStation()); //set the first address in String Array

            } catch (Exception ex) {
                Log.e("Address Error", ex.toString());
            }
            /**
             * init helper Arraylist and Strings for Urls and String Array
             *
             */
            ArrayList<Station> stationtmp = null;
            String UrlSort = "", UrlTmp2 = "";
            String[] newAdd = null;
            Fm.FindMinDist(stations);
           //tmpStat=Fm.getMinstation();
            Size=stations.size();
            tmp= (ArrayList<Station>) stations.clone(); //save the original stations array list
            for (int i = 0; i < tmp.size()-1; i++) //loop for all address main loop
            {

                if(Size==2) //check if the Size of the address is 2
                {
                    newAdd = new String[Size]; //build new arrray string
                    newAdd[0]=Fm.getNewAdress()[Fm.getMinplace()]; //get the address with the min distance
                    if(stations.get(0).getNameStation().equals(newAdd[0]))  //check which is address already in the array newAdd
                    {
                        newAdd[1]=stations.get(1).getNameStation(); //if yes we get the station from index=1
                    }else{
                        newAdd[1]=stations.get(0).getNameStation(); //if not we get the station from index=0
                    }

                    tmpStat=Fm.getMinstation();
                }else
                {  //if size is not 2
                    newAdd = new String[Size]; //build new String array
                    newAdd[0]=Fm.getNewAdress()[Fm.getMinplace()]; //again set the min adress to  array newAdd
                    j=0; //init j variable for index of the newAdd array
                    j++;
                    tmpStat=Fm.getMinstation();
                    for (Station s:stations)
                    {
                        if(s!=tmpStat)   //fill the array of address without min station
                        {
                            newAdd[j] = s.getNameStation();
                            j++;
                        }

                    }
                }
                for (int k = 1; k < newAdd.length; k++) //loop for the Dijkstra Algorithm
                {
                    /**
                     * UrlSort is url to get json from google maps with distance between 2 points in the map
                     */
                    UrlSort = "http://maps.googleapis.com/maps/api/directions/json?origin=";
                    UrlSort += URLEncoder.encode(newAdd[0]); //set the origin address
                    UrlTmp2 = "&destination=" + URLEncoder.encode(newAdd[0]) + "&sensor=false"; //set the destination address
                    UrlSort += UrlTmp2;
                    try {
                        //build new urlsort with replace the destination address for the next address
                        UrlSort = UrlSort.replace(UrlTmp2, "&destination=" + URLEncoder.encode(newAdd[k]) + "&sensor=false");
                    }catch (Exception ex)
                    {
                        Log.e("Error1:",ex.toString());
                    }
                    try
                    {
                        //connection to http client and do post method
                        DefaultHttpClient connection = new DefaultHttpClient();
                        HttpPost post = new HttpPost(UrlSort);
                        HttpResponse httpResponse = connection.execute(post);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        stream = httpEntity.getContent();
                    } catch (IOException e)
                    {
                        Log.e("Error", e.toString());
                    }
                    BufferedReader reader = null;
                    try
                    {
                        reader = new BufferedReader(new InputStreamReader(
                                stream, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        stream.close();
                        result = sb.toString();
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }
                    try
                    {
                        //get json object from result and takes the objects which need for get the distance
                       jsonObject = new JSONObject(result);
                       jsonArray = jsonObject.getJSONArray(ROUTE);
                       JSONObject Route = jsonArray.getJSONObject(0);
                       JSONArray jsonArray1 = Route.getJSONArray(LEGS);
                       JSONObject JsonInfo = jsonArray1.getJSONObject(0);
                       JSONObject Distance = JsonInfo.getJSONObject(Des);
                       distance = Double.parseDouble(Distance.getString("value"));
                       initstation.add(new Station(distance, newAdd[k])); //put new station we go to visit
                    } catch (JSONException ex)
                    {
                        Log.e("Error", ex.toString());
                    }
                }
                Size=initstation.size(); //get the new Size of the array list of stations
                if(Size==1)
                {
                    Fm.FindMinDist(initstation); //if size==1 we send the arraylist to check for minimum distance
                }else {
                    try {
                        //check which station is the minimum and put their to  array of new Address
                        Fm.FindMinDist(initstation);
                        stations = (ArrayList<Station>) initstation.clone(); //save the inistation in station arraylist
                        initstation.clear(); // clear the initstation to the next time
                    } catch (Exception ex) {
                        Log.e("Class Fm error:", ex.toString());
                    }
                   }
       }
            return Fm.getNewAdress(); //return the sort address array which saved in FindMin class object
        }

    }

    public class FindMin {

        /**
         *  init the variables of the FindMin class
         *
         */
        public int index2;
        public Station minstation=null;
        public String[] NewAdress = null;
        public ArrayList<Station> Stations = null;
        public  int minplace; //save the minimum index
        public FindMin(int index, int Size) //constructor FindMin
        {
            index2 = index;
            this.NewAdress = new String[Size + 1];
            Stations = new ArrayList<Station>();
        }

        public String[] getNewAdress()
        {
            return this.NewAdress;
        }

        public void SetNewAdress(String address) throws Exception
        {
            if (address.length() > 0)
                this.NewAdress[index2++] = address;
            else {
                throw new Exception();
            }
        }

        public void FindMinDist(ArrayList<Station> FinalStations)

        {
            double min = 0;
            int minp=0;
            if(FinalStations.size()==1) //if the FinalStations size 1 we set to Array NewAdress station name and return back;
            {
                NewAdress[index2] = FinalStations.get(0).getNameStation();
                return;
            }



            min = FinalStations.get(0).getDistance(); // take the min distance

            for (int i = 0; i < FinalStations.size(); i++)
            {

                if (min > FinalStations.get(i).getDistance()) //search for the min distance between the station of the array list
                {
                    min = FinalStations.get(i).getDistance(); //save the min distance
                    minp = i; //save the index
                    minstation=FinalStations.get(minp); //save the object of min station
                }

            }

            NewAdress[index2] = FinalStations.get(minp).getNameStation(); //set in arraylist the min station
            minplace=index2; //save the last minplace index
            index2++;


        }
        public int getMinplace() {
            return minplace;
        }

        public ArrayList<Station> getStations() {
            return Stations;
        }

        public void setStations(ArrayList<Station> stations) {
            Stations = stations;
        }

        public Station getMinstation() {
            return minstation;
        }

        public void removeStation()
        {


        this.Stations.remove(minstation);



        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}












