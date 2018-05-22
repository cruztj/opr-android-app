package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpActivityJobs extends AppCompatActivity {

    private TextView unitTextView;
    private TextView positionTextView;
    private TextView itemNumTextView;
    private TextView minEducTextView;
    private TextView minExpTextView;
    private TextView minTrainTextView;
    private TextView minEliTextView;
    private TextView dueDateTextView;
    private TextView contactPersonTextView;
    private ImageButton closeButton;
    private LinearLayout imInterestedSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_jobs);

        unitTextView = (TextView) findViewById(R.id.unitTextView);
        positionTextView = (TextView) findViewById(R.id.positionTextView);
        itemNumTextView = (TextView) findViewById(R.id.itemNoTextView);
        minEducTextView = (TextView) findViewById(R.id.minEducationTextView);
        minExpTextView = (TextView) findViewById(R.id.minExperienceTextView);
        minTrainTextView = (TextView) findViewById(R.id.minTrainingTextView);
        minEliTextView = (TextView) findViewById(R.id.minEligibilityTextView);
        dueDateTextView = (TextView) findViewById(R.id.dueDateTextView);
        contactPersonTextView = (TextView) findViewById(R.id.contactPersonTextView);

        imInterestedSpace = (LinearLayout) findViewById(R.id.imInterestedSpace);

        closeButton= (ImageButton) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        String unitString = intent.getStringExtra("unit");
        String positionString = intent.getStringExtra("position");
        String itemNumString = intent.getStringExtra("itemNumber");
        String minEducString = intent.getStringExtra("minEducation");
        String minExpString = intent.getStringExtra("minExperience");
        String minTrainString = intent.getStringExtra("minTraining");
        String minEliString = intent.getStringExtra("minEligibility");
        String dueDateString = intent.getStringExtra("dueDate");
        String contactPersonString = intent.getStringExtra("contactPerson");

        setPopUpLayout(0.9, 0.93);

        unitTextView.setText(unitString);
        positionTextView.setText(positionString);
        itemNumTextView.setText(itemNumString);
        minEducTextView.setText(minEducString);
        minExpTextView.setText(minExpString);
        minTrainTextView.setText(minTrainString);
        minEliTextView.setText(minEliString);
        dueDateTextView.setText(dueDateString);
        contactPersonTextView.setText(contactPersonString);

        imInterestedSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpActivityJobs.this, "IM INTERESTED", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setPopUpLayout(double widthMultiplier, double heightMultiplier){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*widthMultiplier),(int)(height*heightMultiplier));
    }
}
