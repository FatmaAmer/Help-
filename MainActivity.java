package ideasgate.com.horizontallistview;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ArrayList<SectionDataModel> allSampleData;
    ArrayList<SingleItemModel> singleItem;
    int i = 0;
    String Name="";
    String tname0,tname1,tname2,tname3,tname4,tname5;
    String[] Table_Name;
    String[] URLS = {"http://bihmall.com/web/special_offer", "http://bihmall.com/web/Featured", "http://bihmall.com/web/latest", "http://bihmall.com/web/show_product_by_catid/1", "http://bihmall.com/web/show_product_by_catid/2", "http://bihmall.com/web/show_product_by_catid/3"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        allSampleData = new ArrayList<SectionDataModel>();

        singleItem = new ArrayList<SingleItemModel>();

        String s0 = URLS[0];
        String[] strArray0 = s0.split("_");
        for (int k = 0; k < strArray0.length; k++) {
            tname0=strArray0[1];
//            Toast.makeText(this, "This is " + strArray0[1], Toast.LENGTH_SHORT).show();
        }
        String s3 = URLS[3];
        String[] strArray3 = s3.split("_");
        for (int k = 0; k < strArray3.length; k++) {
            tname3=strArray3[1];
//            Toast.makeText(this, "This is " + strArray3[1], Toast.LENGTH_SHORT).show();
        }
        String s4 = URLS[4];
        String[] strArray4 = s4.split("_");
        for (int k = 0; k < strArray4.length; k++) {
            tname4=strArray4[1];
//            Toast.makeText(this, "This is " + strArray4[1], Toast.LENGTH_SHORT).show();
        }
        String s5 = URLS[5];
        String[] strArray5 = s5.split("_");
        for (int k = 0; k < strArray5.length; k++) {
            tname5=strArray5[1];
//            Toast.makeText(this, "This is " + strArray5[1], Toast.LENGTH_SHORT).show();
        }


        String s1 = URLS[1];
        String[] strArray1 = s1.split("/");
        for(int  n = 0; n < strArray1.length; n++) {
            tname1=strArray1[4];
//            Toast.makeText(this, "This is " + strArray1[4], Toast.LENGTH_SHORT).show();
        }
        String s2 = URLS[2];
        String[] strArray2 = s2.split("/");
        for(int  n = 0; n < strArray2.length; n++) {
            tname2=strArray2[4];
//            Toast.makeText(this, "This is " + strArray2[4], Toast.LENGTH_SHORT).show();
        }

        Table_Name= new String[]{tname0, tname1, tname2, tname3, tname4, tname5};



        createDummyData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);

    }

    public void createDummyData() {
        for ( i = 0; i <URLS.length; i++) {
            SectionDataModel dm = new SectionDataModel();
            dm.setHeaderTitle(Table_Name[i]);
            Name=Table_Name[i];
//                Toast.makeText(this, "This is "+ Name, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "This is  a"+ URLS[i], Toast.LENGTH_SHORT).show();
//              Toast.makeText(this, "This is "+ i, Toast.LENGTH_SHORT).show();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(URLS[i]);

                }
            });
            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);
            Toast.makeText(this, "This is  a"+ allSampleData, Toast.LENGTH_SHORT).show();
        }
    }


    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray(Name);
                Log.d("msg",Name);
                singleItem.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    singleItem.add(new SingleItemModel(
                            productObject.getString("P_name"),
                            productObject.getString("P_price"),
                            "http://bihmall.com/uploads/" + productObject.getString("Master_img")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
