package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;

/**
 * Created by Виталий on 02.03.2016.
 */
public class CreateObjectReview extends DialogFragment  implements View.OnClickListener{

    private static final int REQUST_PARENT_CHOICE = 1;

    private static final int EMPTY_PARENT = 0;
    private int idParent;
    private Review review;

    EditText etDialogReviewName;
    TextView tvChoiceSource;

    public interface AddingReviewObjectListener{
        void onAdded(Review review);
    }

    private AddingReviewObjectListener addingReviewObjectListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //final int mSourceCreate = getArguments().getInt(CREATE_SOURCE);

        idParent = EMPTY_PARENT;
        //source = new Source();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.create_category);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_object_review, null);

        final TextInputLayout tilDialogCategoryName = (TextInputLayout)view
                .findViewById(R.id.tilDialogCategoryName);
        tilDialogCategoryName.setHint(getResources().getString(R.string.dialog_category_name));
        etDialogReviewName = tilDialogCategoryName.getEditText();

        tvChoiceSource = (TextView)view.findViewById(R.id.tvParentcategory);
        tvChoiceSource.setOnClickListener(this);
        ImageView imSourceChoice = (ImageView)view.findViewById(R.id.imSourceChoice);
        imSourceChoice.setOnClickListener(this);

        builder.setTitle(R.string.create_category_review);
        //Log.d(TAG, "idOperation = " + idOperation);

   /*     if(getArguments().getParcelable(EXTRA_SOURCE_DATA) != null){
            builder.setTitle(R.string.update_category);
            source = getArguments().getParcelable(EXTRA_SOURCE_DATA);
            setUI();
        }else{
            builder.setTitle(R.string.create_category);
            source = new Source();
        }*/
        review = new Review();

        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                review.setNameReview(etDialogReviewName.getText().toString());
                if(idParent != EMPTY_PARENT){
                    review.setParent(idParent);
                }else{
                    review.setParent(EMPTY_PARENT);
                }

                addingReviewObjectListener = (AddingReviewObjectListener)getActivity();
                addingReviewObjectListener.onAdded(review);
             /*   if(mSourceCreate == CREATE_SOURCE_ACTIVITY){//Если диалог запущен с activity

                    try {
                        addingSourceListener = (AddingSourceListener)getActivity();
                        addingSourceListener.onAdded(source);// передача активити
                    } catch (ClassCastException e) {
                        throw new ClassCastException(getActivity().toString()
                                + " необходимо реализовать AddingSourceListener");
                    }
                }else{
                    sendResult(Activity.RESULT_OK);//вызов метода отправка результата фрагменту
                }*/
            }
        });

        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                final Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);

                if(etDialogReviewName.length() == 0){
                    positiveButton.setEnabled(false);
                    tilDialogCategoryName.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etDialogReviewName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilDialogCategoryName.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else {
                            positiveButton.setEnabled(true);
                            tilDialogCategoryName.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvParentcategory:
            case R.id.imSourceChoice:
                DialogFragment dialogFragment = ChoiceParentReview.newInstance();
                dialogFragment.setTargetFragment(CreateObjectReview.this, REQUST_PARENT_CHOICE);
                dialogFragment.show(getFragmentManager(), "ChoiceParentCategory");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//получаем родительскую категорию

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUST_PARENT_CHOICE:
                idParent = data.getIntExtra(ChoiceParentReview.EXTRA_PARENT_DATA, 0);
                // positionList = data.getIntExtra(ChoiceParentCategory.ETRA_ITEM_LIST, 0);
                if(idParent == EMPTY_PARENT){
                    tvChoiceSource.setText(R.string.parent_category);
                }else {
                    tvChoiceSource.setText(ReviewQuery.getReviewParent(idParent).get(0).getNameReview());
                }
                //Toast.makeText(getActivity(), "id = " + idParent, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
