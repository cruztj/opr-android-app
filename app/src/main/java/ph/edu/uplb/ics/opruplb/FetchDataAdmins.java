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
import java.net.URL;
import java.util.ArrayList;

public class FetchDataAdmins extends AsyncTask<Void, Void, Void> {
//    String urlString = "http://10.11.222.46:3001/admins";
    String urlString = "http://10.0.2.2:3001/admins";


    String data = "";
    private Activity context;
    String checkEmail = "";

    private ArrayList<String> adminEmail = new ArrayList<String>();
    private ArrayList<String> adminAddedBy = new ArrayList<String>();


    public FetchDataAdmins(Activity context){
        this.context = context;
    }

    protected Void doInBackground(Void... voids) {
//        String urlString = "http://192.168.1.160:3001/admins";
//        String urlString = "http://10.0.3.42:3001/admins";


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
            Log.d("ParseDataAdmins", data);
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
                Log.d("JSONException", e.toString());
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, "Error in getting Admin data from server", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseData(String data) throws JSONException {
        JSONArray JA = new JSONArray(data);
        for (int i = JA.length()-1; i >= 0; i--) {
            JSONObject JO = (JSONObject) JA.get(i);
            this.adminEmail.add(JO.getString("admin_email"));
            this.adminAddedBy.add(JO.getString("admin_added_by"));
        }
    }

    public boolean checkIfEmailIsAdmin(String email){
        if(this.adminEmail.isEmpty())
            Log.d("adminEmail", "adminEmail is empty");

        for(int i=0; i<this.adminEmail.size(); i++){
            Log.d("Fetched Email", this.adminEmail.get(i));
        }

        if(this.adminEmail.contains(email))
            return true;
        else
            return false;
    }

    public void setCheckEmail(String email){
        this.checkEmail = email;
    }
}
