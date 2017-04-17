package vitalu.ua.gmail.com.homemoney.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.dialog.CreateObjectReview;
import vitalu.ua.gmail.com.homemoney.fragment.ExpandReviewFragment;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

public class ReviewActivity extends SingleFragmentActivity implements CreateObjectReview.AddingReviewObjectListener{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_CATEGORY = "vitalu.ua.gmail.com.homemoney.activity.extra_category";
/*
    public static final int INCOM_CATEGORY = 1;
    public static final int OUTCOM_CATEGORY = 2;*/

 //   ExpandListSourceFragment elsf;
   // int operation;

    @Override
    protected Fragment createFragment() {
//
//        operation = getIntent().getIntExtra(EXTRA_CATEGORY, INCOM_CATEGORY);

 //       Log.d(TAG, "operation = " + operation);
 //       elsf = ExpandListSourceFragment.newInstance(operation);
       // return elsf;
        return new ExpandReviewFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        DialogFragment addingTaskDialogFragment = new CreateObjectReview();
        addingTaskDialogFragment.show(getFragmentManager(), "CreateSourceDialog");
    }

    @Override
    public void onAdded(Review review) {
        ReviewQuery.addReviewObject(review);
        Toast.makeText(ReviewActivity.this, R.string.add_operation, Toast.LENGTH_SHORT).show();
    }
/*    @Override
    public void onAdded(Source source) {
        source.setId((int) SourceQuery.addSource(source));
        onChangeIteme.onChangeItem(source);
        elsf.updateFragment(source);
    }*/
}
