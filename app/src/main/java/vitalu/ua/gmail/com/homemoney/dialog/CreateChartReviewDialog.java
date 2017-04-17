package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.ReviewQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Review;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 10.03.2016.
 */
public class CreateChartReviewDialog extends DialogFragment
        implements View.OnClickListener {

    public static final String KEY_ID_REVIEW = "vitalu.ua.gmail.com.homemoney.dialog_review_data";
    Calendar calendar;
    private EditText etDate;
    private EditText etMount;
    Button positiveButton;

    int reviewId;

    public interface OnChangeListner{
        public void changed();
    }

    OnChangeListner onChangeListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.amount);
        calendar = Calendar.getInstance();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_create_chart_review, null);

        reviewId = getArguments().getInt(KEY_ID_REVIEW);

        TextInputLayout tilDate =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationDate);
        etDate = tilDate.getEditText();
        etDate.setText(Utils.getDate(System.currentTimeMillis()));

        final TextInputLayout tilMount =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationMount);
        etMount = tilMount.getEditText();

        tilDate.setHint(getResources().getString(R.string.dialog_date));
        tilMount.setHint(getResources().getString(R.string.dialog_mount));

        builder.setView(container);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Review review =new Review();
                review.setParent(reviewId);
                review.setAmountReview(Utils.getTwoDouble(etMount.getText().toString()));
                review.setDateReview(calendar.getTimeInMillis());

                ReviewQuery.addReview(review);
                Toast.makeText(getActivity(), getString(R.string.add_operation), Toast.LENGTH_SHORT).show();


          /*      try {
                    onChangeListner = (OnChangeListner) getActivity();

                } catch (ClassCastException e) {
                    throw new ClassCastException(getActivity().toString()
                            + " необходимо реализовать OnChangeListner");
                }*/
                dialog.dismiss();
            }

        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                /*addingTaskListener.onTaskAddingCancel();
                dialog.cancel();*/
                    }
                }

        );

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener(){

            @Override
            public void onShow(DialogInterface dialog) {
                positiveButton =
                        ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etMount.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilMount.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etMount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0 ) {
                            positiveButton.setEnabled(false);
                            if (s.length() == 0)
                                tilMount.setError(getResources().getString(R.string.dialog_error_empty_title));
                            else
                                tilMount.setErrorEnabled(false);
                        } else {
                            positiveButton.setEnabled(true);
                            tilMount.setErrorEnabled(false);
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

    public static CreateChartReviewDialog newInstance(int reviewId) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_REVIEW, reviewId);
        CreateChartReviewDialog fragment = new CreateChartReviewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

    }
}
