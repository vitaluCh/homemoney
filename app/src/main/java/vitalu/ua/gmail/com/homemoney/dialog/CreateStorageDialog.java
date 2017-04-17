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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.CurrencyQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 06.01.2016.
 */
public class CreateStorageDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final int REQUST_IMAGE_STORAGE = 1;
    public static final int REQUST_CURRENCY_CHOICE = 2;

    public static final int CREATE_SOURCE_ACTIVITY = 1;
    public static final int CREATE_SOURCE_FRAGMENT = 2;

    public static final String CREATE_SOURCE = "vitalu.ua.gmail.com.homemoney.dialog.create_source";
    public static final String EXTRA_STORAGE = "vitalu.ua.gmail.com.homemoney.dialog.create_storage";

    private ImageView imStorage;
    private Storage storage;
    private Currency currency;
    private int imageResours = 0;
    //////////////////////////////////////////////
    private Button positiveButton;
    private TextInputLayout tilStorageName;
    private EditText etStorageName;
    private TextInputLayout tilStorageAmount;
    private EditText etStorageAmount;
    private TextView tvDialogStorageImage;
    private TextView tvFillName;
    private TextView tvShortName;

    List<Currency> currencyList;

    private AddingStorageListener addingStorageListener;

    public interface AddingStorageListener{
        void onAdded(Storage storage);
    }

    private CreateStorageDialog(){};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int mSourceCreate = getArguments().getInt(CREATE_SOURCE);
        //////////////////////////
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_storage, null);

        tilStorageName = (TextInputLayout)view.findViewById(R.id.tilDialogStorageName);
        etStorageName = tilStorageName.getEditText();

        tilStorageAmount = (TextInputLayout)view.findViewById(R.id.tilDialogStorageAmount);
        etStorageAmount = tilStorageAmount.getEditText();

        tvDialogStorageImage = (TextView)view.findViewById(R.id.tvDialogStorageImage);

        ImageView imCurrencyChoice = (ImageView)view.findViewById(R.id.imCurrencyChoice);
        tvFillName = (TextView)view.findViewById(R.id.tvFillName);
        tvShortName = (TextView)view.findViewById(R.id.tvShortName);

        imCurrencyChoice.setOnClickListener(this);
        tvFillName.setOnClickListener(this);
        tvShortName.setOnClickListener(this);

        tvDialogStorageImage.setOnClickListener(this);
        imStorage = (ImageView)view.findViewById(R.id.imDialogStorageImage);
        imStorage.setOnClickListener(this);

        tilStorageName.setHint(getResources().getString(R.string.dialog_storage_name));
        tilStorageAmount.setHint(getResources().getString(R.string.dialog_storage_amount));

        currencyList = CurrencyQuery.getCurrencys();
        if(getArguments().getParcelable(EXTRA_STORAGE) != null){
            builder.setTitle(R.string.dialog_storage_title_update);
            setUI();
        }else{
            builder.setTitle(R.string.dialog_storage_title);
            storage = new Storage();
            if(!currencyList.isEmpty()) {
                currency = currencyList.get(0);
                setTextCurrency(currencyList.get(0).getFullName(),
                        currencyList.get(0).getShortName());
            }
        }

        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.d(TAG, "setPositiveButton");

                storage.setNameStorage(etStorageName.getText().toString());
                storage.setAmountStorage(Utils.getTwoDouble(etStorageAmount.getText().toString()));
                storage.setCurency(currency);
                storage.setImageStorage(imageResours);

                Log.d(TAG, storage.getNameStorage() + " "
                        + storage.getAmountStorage() + " "
                        + storage.getCurency().getId() + " "
                        + storage.getCurency().getShortName() + " "
                        + storage.getCurency().getFullName());

                if(mSourceCreate == CREATE_SOURCE_ACTIVITY){//Если диалог запущен с activity

                    try {
                        addingStorageListener = (AddingStorageListener)getActivity();
                        addingStorageListener.onAdded(storage);
                    } catch (ClassCastException e) {
                        throw new ClassCastException(getActivity().toString()
                                + " необходимо реализовать AddingStorageListener");
                    }
                }else{
                    sendResult(Activity.RESULT_OK);
                }

                dialog.dismiss();
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
                positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if(etStorageName.length() == 0 ||
                        etStorageAmount.length() == 0 || etStorageAmount.getText().toString().equals(".") ||
                        imageResours == 0){
                    positiveButton.setEnabled(false);
                    if (etStorageName.length() == 0){
                        tilStorageName.setError(getResources()
                                .getString(R.string.dialog_error_empty_title));
                    }
                    tilStorageAmount.setError(getResources()
                            .getString(R.string.dialog_error_empty_title));
                    tvDialogStorageImage.setTextColor(getResources().getColor(R.color.red));
                }

                etStorageName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        textChangeDialog(tilStorageName, etStorageName);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                etStorageAmount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        textChangeDialog(tilStorageAmount, etStorageAmount);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
            }
        });

        return alertDialog;
    }

    @Override
    public void onClick(View v) {

        DialogFragment dialog;

        switch (v.getId()){
            case R.id.imCurrencyChoice:
            case R.id.tvFillName:
            case R.id.tvShortName:
                dialog = new ChoiceCurrencyDialog();
                dialog.setTargetFragment(CreateStorageDialog.this, REQUST_CURRENCY_CHOICE);
                dialog.show(getFragmentManager(), "ChoiceCurrencyDialog");
                break;
            case R.id.imDialogStorageImage:
            case R.id.tvDialogStorageImage:
                DialogFragment addingStorageImageFragment = new StorageImageDialog();
                addingStorageImageFragment
                        .setTargetFragment(CreateStorageDialog.this, REQUST_IMAGE_STORAGE);
                addingStorageImageFragment.show(getActivity()
                        .getFragmentManager(), "AddStorageImage");
                break;
        }
    }

    private void textChangeDialog(TextInputLayout inputLayout, EditText editText){
        if(etStorageName.length() == 0 || etStorageAmount.length() == 0
                ||etStorageAmount.getText().toString().equals(".") || imageResours == 0){
            positiveButton.setEnabled(false);
            if(imageResours != 0){
                setValidTextColor();
            }
            if(editText.length() == 0){
                inputLayout.setError(getResources().getString(R.string.dialog_error_empty_title));
            }else{
                inputLayout.setErrorEnabled(false);
            }
        }else{
            positiveButton.setEnabled(true);
            inputLayout.setErrorEnabled(false);
            setValidTextColor();
        }
    }

    private void setValidTextColor(){
        tvDialogStorageImage.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUST_IMAGE_STORAGE:
                int imageStorage = data.getIntExtra(StorageImageDialog.EXTRA_IMAGE_STORAGE, 0);
                imageResours = imageStorage;
                imStorage.setImageResource(imageStorage);

                textChangeDialog(tilStorageName, etStorageName);
                break;
            case REQUST_CURRENCY_CHOICE:
                currency = data.getParcelableExtra(ChoiceCurrencyDialog.EXTRA_CURRENCY_DATA);
                setTextCurrency(currency.getFullName(), currency.getShortName());

                break;
        }
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_STORAGE, storage);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static CreateStorageDialog newInstance(int sourceCreate) {
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        CreateStorageDialog fragment = new CreateStorageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateStorageDialog newInstance(int sourceCreate, Storage storage) {
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        args.putParcelable(EXTRA_STORAGE, storage);
        CreateStorageDialog fragment = new CreateStorageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void setUI(){

        storage = getArguments().getParcelable(EXTRA_STORAGE);
        currency = storage.getCurency();

        etStorageName.setText(storage.getNameStorage());
        etStorageAmount.setText(String.valueOf(storage.getAmountStorage()));

        imageResours = storage.getImageStorage();
        imStorage.setImageResource(imageResours);

        setTextCurrency(storage.getCurency().getFullName(), storage.getCurency().getShortName());
    }

    private void setTextCurrency(String fullName, String shortName){
        tvFillName.setText(fullName);
        tvShortName.setText(shortName);
    }
}
