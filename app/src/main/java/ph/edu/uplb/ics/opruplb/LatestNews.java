package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class LatestNews extends AppCompatActivity {

    private ImageButton backButton;
    static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        backButton = (ImageButton) findViewById(R.id.backButton);
        listView = (ListView) findViewById(R.id.listViewLatestNews);

        //using RSS
        FetchDataNews fetchData = new FetchDataNews(this);
        fetchData.execute();

        //TODO: Use webview when link is clicked or direct to link using browser

        initLayout();
    }

    private void initLayout(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatestNews.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
