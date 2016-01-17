package com.bmpmark1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    RequestQueue mRequestQueue = VolleySingleton.getInstance().getmRequestQueue();
    private String url="https://www.googleapis.com/youtube/v3/search?key=AIzaSyAuDZbNzp49-Isb4Z9HQ45OEjbBW_ioVU4&channelId=UCPWQWav6BpPvtanCtloXkiw&part=snippet,id&order=date&maxResults=20\n";

    //ArrayList<String> heading=new ArrayList<String>();
    //ArrayList<String> subHeading=new ArrayList<String>();
    //we will get back a url which is a string
    //ArrayList<String> image=new ArrayList<String>();
    //ListView videosListView;

    ArrayList<YoutubeConnector2> videosList = new ArrayList<>();
    private RecyclerView recyclerViewVideo;
    private  CustomAdapter customAdapter;
    private static ScrollingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        instance=this;

        recyclerViewVideo = (RecyclerView) findViewById(R.id.recyclerVideo);
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(this));

        customAdapter = new CustomAdapter(this);
        recyclerViewVideo.setAdapter(customAdapter);

        /*
        videosListView = (ListView) findViewById(R.id.listViewVideos);
        videosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //////////////////////////////////////////////////////////
            }
        });
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(ScrollingActivity.this, SearchActivity.class));
            }
        });

        sendJsonRequest("choice", url);

    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    /*
    // sending request to fetch data from a youtube channel
    */
    void sendJsonRequest(final String choice, String url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //parsing request
                        Log.d("menu", "parsing req");
                        videosList = parseJSONResponse(response, choice);
                        customAdapter.setVideosList(videosList);
                        Log.d("menu", "req parsed");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScrollingActivity.this, "There was an error retreiving the request", Toast.LENGTH_SHORT).show();
                    }
                });
        Log.d("menu", "json request created");
        mRequestQueue.add(request);
        Log.d("menu", "req added to queue");
    }


    private ArrayList<YoutubeConnector2> parseJSONResponse(JSONObject response, String choice) {

        ArrayList<YoutubeConnector2> listVideos = new ArrayList<>();
        Log.d("menu", "parse req");
        /*
        if (response == null || response.length() == 0) {
            return listVideos;
        }
        */
        try {
            Log.d("menu", "try");
            StringBuilder data = new StringBuilder();
            JSONArray jsonArrayResult = response.getJSONArray("items");
            int len = jsonArrayResult.length();
            /*
            String title[] = new String[len];
            String description[] = new String[len];
            //String time[] = new String[len];
            String thumbnail[] = new String[len];
            */

            Log.d("menu", "for loop");
            for (int i = 0; i < len; i++) {
                if (choice.equals("choice")) {
                    Log.d("menu", "choice is police");

                    JSONObject itemsObject = jsonArrayResult.getJSONObject(i);
                    JSONObject snippetObject = itemsObject.getJSONObject("snippet");
                    JSONObject defaultThumbnailObject = itemsObject.getJSONObject("snippet")
                                                                   .getJSONObject("thumbnails")
                                                                   .getJSONObject("default") ;

                    //Log.d("menu", "objects ok");

//////////////////    one array list
                    YoutubeConnector2 videoItem = new YoutubeConnector2();
                    videoItem.setTitle(snippetObject.getString("title"));
                    videoItem.setDescription(snippetObject.getString("description"));
                    videoItem.setThumbnailURL(defaultThumbnailObject.getString("url"));

                    Log.d("menu", "title " + videoItem.getTitle());
                    Log.d("menu", "desc " + videoItem.getDescription());
                    Log.d("menu", "url " + videoItem.getThumbnailURL());

                    listVideos.add(videoItem);

                    Log.d("menu", "arraylist title " + listVideos.get(i).getTitle());
                    Log.d("menu", "arraylist desc " + listVideos.get(i).getDescription());
                    Log.d("menu", "arraylist url " + listVideos.get(i).getThumbnailURL());


/*
///////////////////  3 array lists
                    title[i] = snippetObject.getString("title");
                    description[i] = snippetObject.getString("description");
                    //time[i] = snippetObject.getString("publishedAt");
                    thumbnail[i] = defaultThumbnailObject.getString("url");

                    data.append(title[i] + "\n" +description[i] + "\n" + thumbnail[i] +"\n");
                    Log.d("menu", "data appended");
*/
                }

                // displaying result in map after reading complete response
                //addOnMap(len, name, lat, lng);
/*
                Log.d("menu", "toast");
                //-----------------add data
                heading.add(title[i]);
                subHeading.add(description[i]);
                image.add(thumbnail[i]);

                //videosList.setAdapter(new MyAdapter(this, R.layout.video_item, heading));
                //Toast.makeText(ScrollingActivity.this , data.toString(),Toast.LENGTH_LONG).show();
                Log.d("menu", data.toString());
*/
            }
            /*
            videosListView.setAdapter(new MyAdapter(this, R.layout.video_item, heading));
            */

        } catch (JSONException e) {
            Log.d("menu", "json exception " + e);
        }
        return listVideos;
    }

    /*
    //  custom adapter for listview(videos)
    class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.video_item, parent, false);
            //row.setBackgroundColor(getResources().getColor(R.color.white));
            //top
            TextView title = (TextView) row.findViewById(R.id.video_title);
            title.setText(heading.get(position));

            //bottom
            TextView description = (TextView) row.findViewById(R.id.video_description);
            description.setText(subHeading.get(position));

            ImageView thumbnail = (ImageView) row.findViewById(R.id.video_thumbnail);
            Picasso.with(getApplicationContext()).load(image.get(position)).into(thumbnail);

            return row;
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
}
