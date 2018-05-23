package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomListViewAnnouncements extends ArrayAdapter<String> {

    private String[] postTitle;
    private String[] timeStamp;
    private String[] postContent;
    private String[] postLink;
    private Activity context;

    public CustomListViewAnnouncements(Activity context, ArrayList<String> postTitle, ArrayList<String> timeStamp, ArrayList<String> postContent, ArrayList<String> postLink) {
        super(context, R.layout.listview_announcement_layout, postTitle.toArray(new String[postTitle.size()]));

        String[] postTitleArray = postTitle.toArray(new String[postTitle.size()]);
        String[] timeStampArray = timeStamp.toArray(new String[timeStamp.size()]);
        String[] postContentArray = postContent.toArray(new String[postContent.size()]);
        String[] postLinkArray = postLink.toArray(new String[postLink.size()]);

        this.context = context;
        this.postTitle = postTitleArray;
        this.timeStamp = timeStampArray;
        this.postContent = postContentArray;
        this.postLink = postLinkArray;
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
        viewHolder.postLinkTextView.setText(postLink[position]);

        return view;
    }


    class ViewHolderAnnouncements{
        TextView postTitleTextView;
        TextView postTimeStampTextView;
        TextView postContentTextView;
        TextView postLinkTextView;

        ViewHolderAnnouncements(View view){
            postTitleTextView = view.findViewById(R.id.postTitleTextViewAnnouncements);
            postTimeStampTextView = view.findViewById(R.id.postTimeStampTextViewAnnouncements);
            postContentTextView = view.findViewById(R.id.postContentTextViewAnnouncements);
            postLinkTextView = view.findViewById(R.id.postLinkTextViewAnnouncements);
        }
    }
}
