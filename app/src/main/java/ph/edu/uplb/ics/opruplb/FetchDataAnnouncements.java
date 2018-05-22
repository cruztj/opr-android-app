package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchDataAnnouncements extends AsyncTask<Void, Void, Void>{
    String data = "";
    private Activity context;

    private ArrayList<String> postTitle = new ArrayList<String>();
    private ArrayList<String> timeStamp = new ArrayList<String>();
    private ArrayList<String> postContent = new ArrayList<String>();


    public FetchDataAnnouncements(Activity context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String urlString = "http://192.168.1.160:3001/announcements";

        try {
            URL url = new URL(urlString);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(!data.isEmpty()) {
            try {
                parseData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomListViewAnnouncements customListViewAnnouncements = new CustomListViewAnnouncements(context, postTitle, timeStamp, postContent);
            StudentAnnouncements.listView.setDivider(null);
            StudentAnnouncements.listView.setAdapter(customListViewAnnouncements);
        }else{
            Toast.makeText(context, "Error in getting data from server", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseData(String data) throws JSONException {
        JSONArray JA = new JSONArray(data);
        for (int i = JA.length()-1; i > 0; i--) {
            JSONObject JO = (JSONObject) JA.get(i);
            this.postTitle.add(JO.getString("announcement_title"));
            this.postContent.add(JO.getString("announcement_text"));
            this.timeStamp.add(JO.getString("announcement_date_schedule"));
        }
    }
}
