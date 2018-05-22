package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

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

public class FetchDataJobs extends AsyncTask<Void, Void, Void> {
    private Activity context;

    private String urlString = "";
    private String data = "";
    private int tabPosition=0;
    private String[] dataArray;

    private ArrayList<String> unitList = new ArrayList<String>();
    private ArrayList<String> positionList = new ArrayList<String>();
    private ArrayList<String> itemnumberList = new ArrayList<String>();
    private ArrayList<String> mineducationList = new ArrayList<String>();
    private ArrayList<String> minexperienceList = new ArrayList<String>();
    private ArrayList<String> mintrainingList = new ArrayList<String>();
    private ArrayList<String> mineligibilityList = new ArrayList<String>();
    private ArrayList<String> duedateList = new ArrayList<String>();
    private ArrayList<String> contactpersonList = new ArrayList<String>();


    public FetchDataJobs(Activity context, int tabPosition){
        this.context = context;
        this.tabPosition = tabPosition;
    }


    @Override
    protected Void doInBackground(Void... voids) {
//        10.0.2.2 is localhost for android emulator
        urlString = "http://10.0.2.2/publicrelations/UPLBJobPosting.php";

        try{
            URL url = new URL(urlString);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";


            Log.d("FetchDataJobs", "URL OPENED");

            while (line != null && line != "breaker b") {
                line = bufferedReader.readLine();
                data = data + line;
            }

            dataArray = data.split("breaker");
        } catch (MalformedURLException e) {
            Log.d("FetchDataJobs", "MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FetchDataJobs", "IOException");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            parseData(dataArray[tabPosition]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomListViewJobOpenings customListViewJobOpenings = new CustomListViewJobOpenings(context, unitList,
                positionList, itemnumberList, mineducationList, minexperienceList, mintrainingList,
                mineligibilityList, duedateList, contactpersonList);
        JobOpenings.listView.setDivider(null);
        JobOpenings.listView.setAdapter(customListViewJobOpenings);
    }

    public void parseData(String data) throws JSONException {
        JSONArray JA = new JSONArray(data);
        for(int i=0; i<JA.length(); i++){
            JSONObject JO = (JSONObject) JA.get(i);
            this.unitList.add(JO.getString("unit").trim().replaceAll(" +", " "));
            this.positionList.add(JO.getString("position").trim().replaceAll(" +", " "));
            this.itemnumberList.add(JO.getString("itemnumber").trim().replaceAll(" +", " "));
            this.mineducationList.add(JO.getString("mineducation").trim().replaceAll(" +", " "));
            this.minexperienceList.add(JO.getString("minexperience").trim().replaceAll(" +", " "));
            this.mintrainingList.add(JO.getString("mintraining").trim().replaceAll(" +", " "));
            this.mineligibilityList.add(JO.getString("mineligibility").trim().replaceAll(" +", " "));
            this.duedateList.add(JO.getString("duedate").trim().replaceAll(" +", " "));
            this.contactpersonList.add(JO.getString("contactperson").trim().replaceAll(" +", " ")
                    .replaceAll(" Dean", "\nDean")
                    .replaceAll(" Head", "\nHead")
                    .replaceAll(" Institute", "\nInstitute")
                    .replaceAll(" College", "\nCollege")
                    .replaceAll(" Office", "\nOffice")
                    .replaceAll(" Chair", "\nChair"));
        }
    }


    public String getUnit(int position){
        return unitList.get(position);
    }

    public String getPosition(int position){
        return positionList.get(position);
    }

    public String getItemNum(int position){
        return itemnumberList.get(position);
    }

    public String getMinEduc(int position){
        return mineducationList.get(position);
    }

    public String getMinExp(int position){
        return minexperienceList.get(position);
    }

    public String getMinTraining(int position){
        return mintrainingList.get(position);
    }

    public String getMinEligibility(int position){
        return mineligibilityList.get(position);
    }

    public String getDueDate(int position){
        return duedateList.get(position);
    }

    public String getContactPerson(int position){
        return contactpersonList.get(position);
    }

}
