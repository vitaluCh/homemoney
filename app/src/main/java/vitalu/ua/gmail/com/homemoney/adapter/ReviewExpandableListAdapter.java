package vitalu.ua.gmail.com.homemoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

/**
 * Created by Виталий on 02.03.2016.
 */
public class ReviewExpandableListAdapter  extends BaseExpandableListAdapter {

    public static final String TAG = "myLogMainActivity";

    private Context mContext;
    private ArrayList<Map<String, Review>> parentData;
    private ArrayList<ArrayList<Map<String, Review>>> childData;

    private OnChoiceSourceListener choiceSourceListener;

    public interface OnChoiceSourceListener{
        void choiceSource(Review source, int position);
    }

    public ReviewExpandableListAdapter (Context context, ArrayList<Map<String, Review>> parentData,
                                        ArrayList<ArrayList<Map<String, Review>>> childData,
                                        OnChoiceSourceListener choiceSourceListener){
        this.mContext =context;
        this.parentData = parentData;
        this.childData = childData;
        this.choiceSourceListener = choiceSourceListener;
    }

    @Override
    public int getGroupCount() {
        return parentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tree_view_group, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(parentData.get(groupPosition).get(ReviewQuery.GROUP_NAME).getNameReview());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imGroup);

        int sizeChild = childData.get(groupPosition).size();

        // Log.d(TAG, parentData.get(groupPosition).get(SourceQuery.GROUP_NAME).getNameSours() + "  " + sizeChild);

        if(sizeChild > 0){
            imageView.setImageResource(R.drawable.ic_tree_down_32dp);
        }
        else{
            imageView.setImageResource(0);
        }

        if (isExpanded && sizeChild > 0){
            imageView.setImageResource(R.drawable.ic_tree_up_32dp);
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        textGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(mContext, parentData.get(groupPosition)
                        .get(SourceQuery.GROUP_NAME).getNameSours() + sizeChild,
                        Toast.LENGTH_SHORT).show();*/
                choiceSourceListener.choiceSource(parentData.get(groupPosition)
                        .get(ReviewQuery.GROUP_NAME), groupPosition);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tree_view_child, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText(childData.get(groupPosition).get(childPosition).get(ReviewQuery.CHILD_NAME).getNameReview());

        return convertView;
    }

}
