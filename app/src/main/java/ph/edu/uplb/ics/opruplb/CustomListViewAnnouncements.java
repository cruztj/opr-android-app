package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomListViewAnnouncements extends ArrayAdapter<String> {

    private String[] postTitle;
    private String[] timeStamp;
    private String[] postContent;
    private Activity context;

    public CustomListViewAnnouncements(Activity context, String[] postTitle, String[] timeStamp, String[] postContent) {
        super(context, R.layout.listview_announcement_layout, postTitle);

        this.context = context;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.postContent = postContent;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderAnnouncements viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_announcement_layout, null, false);
            viewHolder = new ViewHolderAnnouncements(view);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolderAnnouncements) view.getTag();
        }
        viewHolder.postTitleTextView.setText(postTitle[position]);
        viewHolder.postTimeStampTextView.setText(timeStamp[position]);
        viewHolder.postContentTextView.setText(postContent[position]);

        return view;
    }


    class ViewHolderAnnouncements{
        TextView postTitleTextView;
        TextView postTimeStampTextView;
        TextView postContentTextView;

        ViewHolderAnnouncements(View view){
            postTitleTextView = view.findViewById(R.id.postTitleTextViewAnnouncements);
            postTimeStampTextView = view.findViewById(R.id.postTimeStampTextViewAnnouncements);
            postContentTextView = view.findViewById(R.id.postContentTextViewAnnouncements);
        }
    }
}
