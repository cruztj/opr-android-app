package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class LatestNews extends AppCompatActivity {

    private ImageButton backButton;
    private ListView listView;

    //TODO: Use a function to get these from website
    String[] postTitle = {"UPLB", "UPD", "UPB", "UPM"};
    String[] timeStamp = {"1/2/2018", "1/30/2018", "1/22/2018", "1/12/2018"};
    String[] postContent = {"UP Los Banos", "UP Diliman", "UP Baguio", "UP Manila"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        initLayout();

    }

    private void initLayout(){
        backButton = (ImageButton) findViewById(R.id.backButton);

        listView = (ListView) findViewById(R.id.listViewLatestNews);
        CustomListViewLatestNews customListViewLatestNews = new CustomListViewLatestNews(this, postTitle, timeStamp, postContent);
        listView.setDivider(null);
        listView.setAdapter(customListViewLatestNews);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatestNews.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
