package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListViewJobOpenings extends ArrayAdapter<String> {

    private String[] jobOpeningCollegeUnitArray;
    private String[] jobOpeningPositionArray;
    private String[] jobOpeningItemNoArray;
    private String[] jobOpeningMinEducationArray;
    private String[] jobOpeningMinExperienceArray;
    private String[] jobOpeningMinTrainingArray;
    private String[] jobOpeningMinEligibilityArray;
    private String[] jobOpeningDueDateArray;
    private String[] jobOpeningContactPersonArray;
    private Activity context;

   public CustomListViewJobOpenings(Activity context, ArrayList<String> jobOpeningCollegeUnit, ArrayList<String> jobOpeningPosition,
                                    ArrayList<String> jobOpeningItemNo, ArrayList<String> jobOpeningMinEducation, ArrayList<String> jobOpeningMinExperience,
                                    ArrayList<String> jobOpeningMinTraining, ArrayList<String> jobOpeningMinEligibility, ArrayList<String> jobOpeningDueDate,
                                    ArrayList<String> jobOpeningContactPerson){
       super(context, R.layout.listview_job_layout, jobOpeningCollegeUnit);

       String[] jobOpeningCollegeUnitArray = jobOpeningCollegeUnit.toArray(new String[jobOpeningCollegeUnit.size()]);
       String[] jobOpeningPositionArray = jobOpeningPosition.toArray(new String[jobOpeningPosition.size()]);
       String[] jobOpeningItemNoArray = jobOpeningItemNo.toArray(new String[jobOpeningItemNo.size()]);
       String[] jobOpeningMinEducationArray = jobOpeningMinEducation.toArray(new String[jobOpeningMinEducation.size()]);
       String[] jobOpeningMinExperienceArray = jobOpeningMinExperience.toArray(new String[jobOpeningMinExperience.size()]);
       String[] jobOpeningMinTrainingArray = jobOpeningMinTraining.toArray(new String[jobOpeningMinTraining.size()]);
       String[] jobOpeningMinEligibilityArray = jobOpeningMinEligibility.toArray(new String[jobOpeningMinEligibility.size()]);
       String[] jobOpeningDueDateArray = jobOpeningDueDate.toArray(new String[jobOpeningDueDate.size()]);
       String[] jobOpeningContactPersonArray = jobOpeningContactPerson.toArray(new String[jobOpeningContactPerson.size()]);

       this.context = context;
       this.jobOpeningCollegeUnitArray = jobOpeningCollegeUnitArray;
       this.jobOpeningPositionArray = jobOpeningPositionArray;
       this.jobOpeningItemNoArray = jobOpeningItemNoArray;
       this.jobOpeningMinEducationArray = jobOpeningMinEducationArray;
       this.jobOpeningMinExperienceArray = jobOpeningMinExperienceArray;
       this.jobOpeningMinTrainingArray = jobOpeningMinTrainingArray;
       this.jobOpeningMinEligibilityArray = jobOpeningMinEligibilityArray;
       this.jobOpeningDueDateArray = jobOpeningDueDateArray;
       this.jobOpeningContactPersonArray = jobOpeningContactPersonArray;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderJobOpenings viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_job_layout, null, false);
            viewHolder = new ViewHolderJobOpenings(view);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolderJobOpenings) view.getTag();
        }
        viewHolder.jobCollegeUnit.setText(jobOpeningCollegeUnitArray[position]);
        viewHolder.jobPosition.setText(jobOpeningPositionArray[position]);
//        viewHolder.jobItemNo.setText(jobOpeningItemNoArray[position]);
//        viewHolder.jobMinEducation.setText(jobOpeningMinEducationArray[position]);
//        viewHolder.jobMinExperience.setText(jobOpeningMinExperienceArray[position]);
//        viewHolder.jobMinTraining.setText(jobOpeningMinTrainingArray[position]);
//        viewHolder.jobMinEligibility.setText(jobOpeningMinEligibilityArray[position]);
//        viewHolder.jobDueDate.setText(jobOpeningDueDateArray[position]);
//        viewHolder.jobContactPerson.setText(jobOpeningContactPersonArray[position]);

        return view;
    }


    class ViewHolderJobOpenings{
        TextView jobCollegeUnit;
        TextView jobPosition;
//        TextView jobItemNo;
//        TextView jobMinEducation;
//        TextView jobMinExperience;
//        TextView jobMinTraining;
//        TextView jobMinEligibility;
//        TextView jobDueDate;
//        TextView jobContactPerson;

        ViewHolderJobOpenings(View view){
            jobCollegeUnit = view.findViewById(R.id.jobOpeningCollegeUnitTextView);
            jobPosition = view.findViewById(R.id.jobOpeningPositionTextView);
//            jobItemNo = view.findViewById(R.id.jobOpeningItemNoTextView);
//            jobMinEducation = view.findViewById(R.id.jobOpeningMinEducationTextView);
//            jobMinExperience = view.findViewById(R.id.jobOpeningMinExperienceTextView);
//            jobMinTraining = view.findViewById(R.id.jobOpeningMinTrainingTextView);
//            jobMinEligibility = view.findViewById(R.id.jobOpeningMinEligibilityTextView);
//            jobDueDate = view.findViewById(R.id.jobOpeningDueDateTextView);
//            jobContactPerson = view.findViewById(R.id.jobOpeningContactPersonTextView);
        }
    }


}
