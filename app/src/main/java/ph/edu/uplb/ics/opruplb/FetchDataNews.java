package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FetchDataNews extends AsyncTask<Void, Void, Boolean>{
    private String urlString = "";
    private boolean isItem = false;
    private Activity context;
    private int tabPosition;

    private String title=null;
    private String link=null;
    private String description=null;

    private ArrayList<String> postTitle = new ArrayList<String>();
    private ArrayList<String> timeStamp = new ArrayList<String>();
    private ArrayList<String> postLink = new ArrayList<String>();
    private ArrayList<String> postContent = new ArrayList<String>();

    public FetchDataNews(Activity context, int tabPosition){
        this.context = context;
        this.tabPosition = tabPosition;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        switch (tabPosition){
            case 0: urlString = "https://uplb.edu.ph/component/k2/itemlist?format=feed&moduleID=279";
                break;
            case 1: urlString = "https://uplb.edu.ph/component/k2/itemlist?format=feed&moduleID=282";
                break;
            case 2: urlString = "https://uplb.edu.ph/component/k2/itemlist?format=feed&moduleID=280";
                break;
            case 3: urlString = "https://uplb.edu.ph/component/k2/itemlist?format=feed&moduleID=283";
                break;
        }

        try {
            URL url = new URL(urlString);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            parseData(inputStream);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        CustomListViewLatestNews customListViewLatestNews = new CustomListViewLatestNews(context, postTitle, timeStamp, postContent, postLink);
        LatestNews.listView.setDivider(null);
        LatestNews.listView.setAdapter(customListViewLatestNews);
    }

    private void parseData(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(inputStream, null);

        xmlPullParser.nextTag();
        while(xmlPullParser.next() != XmlPullParser.END_DOCUMENT){
            int eventType = xmlPullParser.getEventType();

            String name = xmlPullParser.getName();
            if(name == null)
                continue;
            if(eventType == XmlPullParser.END_TAG){
                if(name.equalsIgnoreCase("item"))
                    isItem = false;
                continue;
            }
            if(eventType == XmlPullParser.START_TAG){
                if(name.equalsIgnoreCase("item")) {
                    isItem = true;
                    continue;
                }
            }

            Log.d("XmlParser", "Parsing name: "+name);
            String result = "";
            if(xmlPullParser.next() == XmlPullParser.TEXT){
                result = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }

            if(name.equalsIgnoreCase("title")){
                title = result;
                postTitle.add(result);
            } else if(name.equalsIgnoreCase("link")){
                postLink.add(result);
            } else if(name.equalsIgnoreCase("description")){
                postContent.add(result);
            }

            if(title != null && link != null && description!=null){
                title = null;
                link = null;
                description = null;
                isItem = false;
            }
        }

    }
}
