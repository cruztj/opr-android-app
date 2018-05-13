package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LatestNews extends AppCompatActivity {

    private ImageButton backButton;
    static ListView listView;
    private TabLayout tabLayout;
    private FetchDataNews fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        backButton = (ImageButton) findViewById(R.id.backButton);
        listView = (ListView) findViewById(R.id.listViewLatestNews);

        initLayout();

        fetchData = new FetchDataNews(LatestNews.this, 0);
        fetchData.execute();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fetchData = new FetchDataNews(LatestNews.this, tab.getPosition());
                fetchData.execute();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initLayout(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LatestNews.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LatestNews.this, PopUpActivity.class);
                intent.putExtra("urlString", fetchData.getUrlString(position+1));
                intent.putExtra("titleString", fetchData.getTitleString(position));
                startActivity(intent);
            }
        });

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }
}
