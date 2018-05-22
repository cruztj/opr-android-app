package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class JobOpenings extends AppCompatActivity {

    private ImageButton backButton;
    static ListView listView;
    private TabLayout tabLayout;
    private FetchDataJobs fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_openings);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        backButton = (ImageButton) findViewById(R.id.backButton);
        listView = (ListView) findViewById(R.id.listViewJobOpenings);


        fetchData = new FetchDataJobs(this, 0);
        fetchData.execute();

        initLayout();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fetchData = new FetchDataJobs(JobOpenings.this, tab.getPosition());
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
                Intent intent = new Intent(JobOpenings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create popUp displaying other details and an I'm Interested Button
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(JobOpenings.this, PopUpActivityJobs.class);
                        intent.putExtra("unit", fetchData.getUnit(position));
                        intent.putExtra("position", fetchData.getPosition(position));
                        intent.putExtra("itemNumber", fetchData.getItemNum(position));
                        intent.putExtra("minEducation", fetchData.getMinEduc(position));
                        intent.putExtra("minExperience", fetchData.getMinExp(position));
                        intent.putExtra("minTraining", fetchData.getMinTraining(position));
                        intent.putExtra("minEligibility", fetchData.getMinEligibility(position));
                        intent.putExtra("dueDate", fetchData.getDueDate(position));
                        intent.putExtra("contactPerson", fetchData.getContactPerson(position));
                        startActivity(intent);
                    }
                });
            }
        });

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
