package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class JobOpenings extends AppCompatActivity {

    private ImageButton backButton;
    private ListView listView;

    //TODO: Use a function to get these from the website
    String[] jobOpeningCollegeUnitArray = {"CAS", "CA", "CEM"};
    String[] jobOpeningPositionArray = {"Professor", "Assistant Professor", "Lecturer"};
    String[] jobOpeningItemNoArray = {"Item-1", "Item-2", "Item-3"};
    String[] jobOpeningMinEducationArray = {"College", "High School", "None"};
    String[] jobOpeningMinExperienceArray = {"None", "None", "Star Wars"};
    String[] jobOpeningMinTrainingArray = {"Four Hours", "Ten Hours", "Thirty Minutes"};
    String[] jobOpeningMinEligibilityArray = {"Sub Prof", "Sub Prof", "Sub Prof"};
    String[] jobOpeningDueDateArray = {"1/2/2018", "1/9/2018", "1/6/2018"};
    String[] jobOpeningContactPersonArray = {"Kuya Will", "Ina Ganda", "Nigguh"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_openings);

        initLayout();
    }

    private void initLayout(){
        backButton = (ImageButton) findViewById(R.id.backButton);
        listView = (ListView) findViewById(R.id.listViewJobOpenings);
        CustomListViewJobOpenings customListViewJobOpenings = new CustomListViewJobOpenings(this, jobOpeningCollegeUnitArray,
                jobOpeningPositionArray, jobOpeningItemNoArray, jobOpeningMinEducationArray, jobOpeningMinExperienceArray,
                jobOpeningMinTrainingArray, jobOpeningMinEligibilityArray, jobOpeningDueDateArray, jobOpeningContactPersonArray);
        listView.setDivider(null);
        listView.setAdapter(customListViewJobOpenings);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOpenings.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
