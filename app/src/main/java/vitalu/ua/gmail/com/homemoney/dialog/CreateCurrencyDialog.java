package vitalu.ua.gmail.com.homemoney.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCurrencyDialog extends DialogFragment {

    public static final String EXTRA_CURRENCY_DATA = "vitalu.ua.gmail.com.homemoney.dialog.currency_data";
    public static final String CREATE_SOURCE = "vitalu.ua.gmail.com.homemoney.dialog.create_currency";

    public static final int CREATE_SOURCE_ACTIVITY = 1;
    public static final int CREATE_SOURCE_FRAGMENT = 2;

    private Currency currency;

    private TextInputLayout tilDialogCurrencyFullName;
    private TextInputLayout tilDialogCurrencyShortName;
    private EditText etDialogCurrencyFullName;
    private EditText etDialogCurrencyShortName;

    public interface AddingCurrencyListener{
        void onAdded(Currency currency);
    }

    private AddingCurrencyListener addingCurrencyListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int mSourceCreate = getArguments().getInt(CREATE_SOURCE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_currency, null);

        tilDialogCurrencyFullName = (TextInputLayout)view.findViewById(R.id.tilDialogCurrencyFullName);
        etDialogCurrencyFullName = tilDialogCurrencyFullName.getEditText();

        tilDialogCurrencyShortName = (TextInputLayout)view.findViewById(R.id.tilDialogCurrencyShortName);
        etDialogCurrencyShortName = tilDialogCurrencyShortName.getEditText();

        tilDialogCurrencyFullName.setHint(getResources().getString(R.string.dialog_currency_full_name));
        tilDialogCurrencyShortName.setHint(getResources().getString(R.string.dialog_currency_short_name));

        /////////////////////////////////////////////////////
        if(getArguments().getParcelable(EXTRA_CURRENCY_DATA) != null){
            builder.setTitle(R.string.dialog_title_update_currency);
            setUI();
        }else{
            builder.setTitle(R.string.dialog_title_currency);
            currency = new Currency();
        }

        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "Сохранение Валюты", Toast.LENGTH_SHORT).show();

                currency.setFullName(etDialogCurrencyFullName.getText().toString());
                currency.setShortName(etDialogCurrencyShortName.getText().toString());

               // sendResult(Activity.RESULT_OK);

                ////////////////////////////////////
                if(mSourceCreate == CREATE_SOURCE_ACTIVITY){//Если диалог запущен с activity

                    try {
                        addingCurrencyListener = (AddingCurrencyListener)getActivity();
                        addingCurrencyListener.onAdded(currency);// передача активити
                    } catch (ClassCastException e) {
                        throw new ClassCastException(getActivity().toString()
                                + " необходимо реализовать AddingStorageListener");
                    }
                }else{
                    sendResult(Activity.RESULT_OK);//вызов метода отправка результата фрагменту
                }

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
                if(etDialogCurrencyFullName.length() == 0 ||
                        etDialogCurrencyShortName.length() == 0){
                    positiveButton.setEnabled(false);
                    if(etDialogCurrencyFullName.length() == 0){
                        tilDialogCurrencyFullName.setError(getResources().getString(R.string.dialog_error_empty_title));
                    }

                    tilDialogCurrencyShortName.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etDialogCurrencyFullName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0 || etDialogCurrencyShortName.length() == 0){
                            positiveButton.setEnabled(false);
                            if(s.length() == 0){
                                tilDialogCurrencyFullName.setError(getResources().getString(R.string.dialog_error_empty_title));
                            }else{
                                tilDialogCurrencyFullName.setErrorEnabled(false);
                            }
                        }else{
                            positiveButton.setEnabled(true);
                            tilDialogCurrencyFullName.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                etDialogCurrencyShortName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0 || etDialogCurrencyFullName.length() == 0){
                            positiveButton.setEnabled(false);
                            if(s.length() == 0){
                                tilDialogCurrencyShortName.setError(getResources().getString(R.string.dialog_error_empty_title));
                            }else{
                                tilDialogCurrencyShortName.setErrorEnabled(false);
                            }
                        }else{
                            positiveButton.setEnabled(true);
                            tilDialogCurrencyShortName.setErrorEnabled(false);
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

    private void sendResult(int resultCode) {//отправка результата фрагменту
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_CURRENCY_DATA, currency);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static CreateCurrencyDialog newInstance(int sourceCreate) {//Создание диалога с источником
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        CreateCurrencyDialog fragment = new CreateCurrencyDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateCurrencyDialog newInstance(int sourceCreate, Currency currency) {//Для редактирования
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        args.putParcelable(EXTRA_CURRENCY_DATA, currency);
        CreateCurrencyDialog fragment = new CreateCurrencyDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void setUI(){

        currency = getArguments().getParcelable(EXTRA_CURRENCY_DATA);

        etDialogCurrencyFullName.setText(currency.getFullName());
        etDialogCurrencyShortName.setText(currency.getShortName());
    }
}
