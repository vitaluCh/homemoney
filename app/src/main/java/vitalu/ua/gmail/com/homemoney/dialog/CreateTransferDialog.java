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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.calc.dialog.DialogCalculator;
import vitalu.ua.gmail.com.homemoney.database.database_query.OperationQuery;
import vitalu.ua.gmail.com.homemoney.database.database_query.PatternQuery;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.database.database_query.StorageQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 10.02.2016.
 */
public class CreateTransferDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_OPERATION_DATA = "vitalu.ua.gmail.com.homemoney.dialog.operation_data";

    public static final  int REQUEST_CALC_ONE_DATA = 1;
    public static final  int REQUEST_CALC_TWO_DATA = 2;
    public static final int REQUEST_STORAGE_ONE_DATA = 3;
    public static final int REQUEST_STORAGE_TWO_DATA = 4;

    public static final int TEMPLATE_TRANSFER = 3;
    private static final String EXTRA_TEMPLATE_DATA = "vitalu.ua.gmail.com.homemoney.dialog.template_data";

    private Storage mStorageFrom;
    private Storage mStorageTo;
    private Operation mOperationFrom;
    private Operation mOperationTo;
    TransferOperation operation;
    private Calendar calendar;
    private Source source;

    private int mImageOneStorage = 0;
    private int mImageTwoStorage = 0;

    int template;

    private List<Storage> listStorage;

    EditText etDate;

    ImageView imOneStorage;;
    TextView tvOneStorageName;
    TextView tvOneStorageCurrency;
    ImageView imStorageChoice;

    ImageView imTwoStorage;
    TextView tvTwoStorageName;
    TextView tvTwoStorageCurrency;
    ImageView imStorageChoice2;

    TextView tvCurrency1;
    TextView tvCurrency2;

    EditText etMount;
    EditText etItem;

    TextInputLayout tilOperationName;
    EditText etNameOperation;

    TextView tvDialogOperationResult;
    TextView tvDialogOperationResultCurrency;

    Button positiveButton;

    Storage oldStorageFrom;
    double oldAmountFrom;
    Storage oldStorageTo;
    double oldAmountTo;

    public interface OnItemChanged{//для извещения activity
        void updateTransferOperation();
        void itemAdding(TransferOperation operation);
    }

    private OnItemChanged onItemChanged;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        template = getArguments().getInt(EXTRA_TEMPLATE_DATA, 0);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        source = SourceQuery.getSourceTransfer().get(0);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_create_transfer, null);

        tilOperationName =
                (TextInputLayout) container.findViewById(R.id.tilOperationName);
        etNameOperation = tilOperationName.getEditText();
        tilOperationName.setHint(getResources().getString(R.string.title_operation));

        TextInputLayout tilDate =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationDate);
        etDate = tilDate.getEditText();

        final TextInputLayout tilMount =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationMount);
        etMount = tilMount.getEditText();

        final TextInputLayout tilItem =
                (TextInputLayout) container.findViewById(R.id.tilItem);
        etItem = tilItem.getEditText();

        final TextInputLayout tilDescription =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationDescription);
        final EditText etDescription = tilDescription.getEditText();

        tvCurrency1 = (TextView)container.findViewById(R.id.tvCurrency1);//курс валют
        tvCurrency2 = (TextView)container.findViewById(R.id.tvCurrency2);

        tvDialogOperationResult = (TextView)container.findViewById(R.id.tvDialogOperationResult);
        tvDialogOperationResultCurrency =
                (TextView)container.findViewById(R.id.tvDialogOperationResultCurrency);

        //////////////////////////////////////
        imOneStorage = (ImageView)container.findViewById(R.id.imOneStorage);//Первый кошелек
        tvOneStorageName = (TextView)container.findViewById(R.id.tvOneStorageName);
        tvOneStorageCurrency = (TextView)container.findViewById(R.id.tvOneStorageCurrency);
        imStorageChoice = (ImageView)container.findViewById(R.id.imStorageChoice);

        imOneStorage.setOnClickListener(this);
        tvOneStorageName.setOnClickListener(this);
        tvOneStorageCurrency.setOnClickListener(this);
        imStorageChoice.setOnClickListener(this);
        //====================================================================
        imTwoStorage = (ImageView)container.findViewById(R.id.imTwoStorage);//Второй кошелек
        tvTwoStorageName = (TextView)container.findViewById(R.id.tvTwoStorageName);
        tvTwoStorageCurrency = (TextView)container.findViewById(R.id.tvTwoStorageCurrency);
        imStorageChoice2 = (ImageView)container.findViewById(R.id.imStorageChoice2);

        imTwoStorage.setOnClickListener(this);
        tvTwoStorageName.setOnClickListener(this);
        tvTwoStorageCurrency.setOnClickListener(this);
        imStorageChoice2.setOnClickListener(this);
        //////////////////////////////////////////
        ImageView imDialogAmoutCalc = (ImageView)container.findViewById(R.id.imDialogAmoutCalc);
        ImageView imDialogCurrencyCalc = (ImageView)container.findViewById(R.id.imDialogCurrencyCalc);

        imDialogAmoutCalc.setOnClickListener(this);
        imDialogCurrencyCalc.setOnClickListener(this);

        listStorage = StorageQuery.getVisibleStorages();
        tilDate.setHint(getResources().getString(R.string.dialog_date));
        tilDescription.setHint(getResources().getString(R.string.dialog_description));
        tilMount.setHint(getResources().getString(R.string.dialog_mount));
        tilItem.setHint(getResources().getString(R.string.till_cource));

        builder.setView(container);

////////////////////////////////////////////////////////////////////////////////////////////////////
        if(getArguments().getParcelable(EXTRA_OPERATION_DATA) != null){//если редактирование
            builder.setTitle(R.string.dialog_title_edit_transfer);
            setUI();
        }else{
            builder.setTitle(R.string.dialog_title_add_transfer);

            mOperationFrom = new Operation();
            mOperationTo = new Operation();

            if (!listStorage.isEmpty()) {
                mStorageFrom = listStorage.get(0);
                setOneStorage();
            }

            etNameOperation.setText(R.string.operation_transfer);

           // etNameOperation.setSelection(0);
           // etNameOperation.selectAll();
            etDate.setText(Utils.getDate(System.currentTimeMillis()));
        }

        if(template > 0){
            etDate.setVisibility(View.INVISIBLE);
            tilDate.setVisibility(View.INVISIBLE);
        }

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
                //if()

                /*mOperationFrom = new Operation();
                mOperationTo = new Operation();*/
               // operation = new TransferOperation();



                mOperationFrom.setNameOperation(etNameOperation.getText().toString())
                        .setAmount(Utils.getTwoDouble(etMount.getText().toString()))
                        .setDate(calendar.getTimeInMillis())
                        .setSoyrce(source)
                        .setStorage(mStorageFrom);

                mOperationTo.setNameOperation(etNameOperation.getText().toString())
                        .setAmount(Utils.getTwoDouble(tvDialogOperationResult.getText().toString()))
                        .setDate(calendar.getTimeInMillis())
                        .setSoyrce(source)
                        .setStorage(mStorageTo)
                        .setDescription(etDescription.getText().toString());
                //.setImagePath()

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

                operation = new TransferOperation(mOperationFrom, mOperationTo);

                try {
                    onItemChanged = (OnItemChanged) getActivity();
                    /*onItemChanged.itemChanged();*/
                } catch (ClassCastException e) {
                    throw new ClassCastException(getActivity().toString()
                            + " необходимо реализовать OnItemChanged");
                }
                if(template > 0){
                    if (getArguments().getParcelable(EXTRA_OPERATION_DATA) != null) {
                        PatternQuery. updateTemplate(mOperationTo);
                        PatternQuery.updateTemplate(mOperationFrom);
                        onItemChanged.updateTransferOperation();
                    }else {
                        //сохранить в базу
                        operation.getInComeOperation().setDate(0);
                        PatternQuery.addTemplate(mOperationTo);
                        PatternQuery.addTemplate(mOperationFrom);
                        onItemChanged.itemAdding(operation);
                    }
                }else {
                    if (getArguments().getParcelable(EXTRA_OPERATION_DATA) != null) {
                        OperationQuery.updateTransferOperation(mOperationFrom, oldAmountFrom,
                                oldStorageFrom, OperationQuery.OPERATION_OUTCOM);
                        OperationQuery.updateTransferOperation(mOperationTo, oldAmountTo,
                                oldStorageTo, OperationQuery.OPERATION_INCOM);
                    /*OperationQuery.updateOperation(operation, oldAmount, oldStorage);*/
                        onItemChanged.updateTransferOperation();
                    } else {
                        OperationQuery.addOperationTransfer(mOperationFrom, OperationQuery.OPERATION_OUTCOM);
                        OperationQuery.addOperationTransfer(mOperationTo, OperationQuery.OPERATION_INCOM);
                        Toast.makeText(getActivity(), getString(R.string.add_operation), Toast.LENGTH_SHORT).show();
                        onItemChanged.itemAdding(operation);

                    }
                }
                //////////////////////////////////////////////////////////////////////



                /*OperationQuery.addOperationTransfer(mOperationFrom, OperationQuery.OPERATION_OUTCOM);
                OperationQuery.addOperationTransfer(mOperationTo, OperationQuery.OPERATION_INCOM);
                Toast.makeText(getActivity(), getString(R.string.add_operation), Toast.LENGTH_SHORT).show();*/

                //Toast.makeText(getActivity(), id1 + " " + id2, Toast.LENGTH_SHORT).show();
/*
                Log.d(TAG, mOperationFrom.getNameOperation());
                Log.d(TAG, String.valueOf(mOperationFrom.getId()));
                Log.d(TAG, String.valueOf(mOperationFrom.getAmount()));
                Log.d(TAG, String.valueOf(mOperationFrom.getDate()));
                Log.d(TAG, mOperationFrom.getStorage().getNameStorage());
                Log.d(TAG, String.valueOf(mOperationFrom.getStorage().getImageStorage()));
                Log.d(TAG, mOperationFrom.getSoyrce().getNameSours());

                Log.d(TAG, "===============================================");

                Log.d(TAG, mOperationTo.getNameOperation());
                Log.d(TAG, String.valueOf(mOperationTo.getId()));
                Log.d(TAG, String.valueOf(mOperationTo.getAmount()));
                Log.d(TAG, String.valueOf(mOperationTo.getDate()));
                Log.d(TAG, mOperationTo.getStorage().getNameStorage());
                Log.d(TAG, String.valueOf(mOperationTo.getStorage().getImageStorage()));
                Log.d(TAG, mOperationTo.getSoyrce().getNameSours());*/

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }

        );

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                positiveButton =
                        ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etNameOperation.length() == 0 || etMount.length() == 0 || mImageOneStorage == 0
                        || mImageTwoStorage == 0 || etItem.length() == 0) {
                    positiveButton.setEnabled(false);
                    if (etNameOperation.length() == 0){
                        tilOperationName.setError(getResources().getString(R.string.dialog_error_empty_title));
                    }
                    tilMount.setError(getResources().getString(R.string.dialog_error_title));
                    tilItem.setError(getResources().getString(R.string.dialog_error_empty));
                }

                etNameOperation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0 || etMount.length() == 0 || mImageOneStorage == 0
                                || mImageTwoStorage == 0 || etItem.length() == 0) {
                            positiveButton.setEnabled(false);
                            if (s.length() == 0)
                                tilOperationName.setError(getResources().getString(R.string.dialog_error_empty_title));
                            else
                                tilOperationName.setErrorEnabled(false);
                        } else {
                            positiveButton.setEnabled(true);
                            tilOperationName.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                etMount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0 || etNameOperation.length() == 0 || mImageOneStorage == 0
                                || mImageTwoStorage == 0 || etItem.length() == 0) {
                            positiveButton.setEnabled(false);
                            if (s.length() == 0)
                                tilMount.setError(getResources().getString(R.string.dialog_error_title));
                            else
                                tilMount.setErrorEnabled(false);
                        } else {
                            positiveButton.setEnabled(true);
                            tilMount.setErrorEnabled(false);
                        }

                        if(etItem.length() != 0 && s.length() != 0){
                            setResult();

                        }else{
                            tvDialogOperationResult.setText("0");
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                etItem.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0 || etNameOperation.length() == 0 || mImageOneStorage == 0
                                || mImageTwoStorage == 0 || etMount.length() == 0) {
                            positiveButton.setEnabled(false);
                            if (s.length() == 0)
                                tilItem.setError(getResources().getString(R.string.dialog_error_empty));
                            else
                                tilItem.setErrorEnabled(false);
                        } else {
                            positiveButton.setEnabled(true);
                            tilItem.setErrorEnabled(false);
                        }

                        if(etMount.length() != 0 && s.length() != 0){
                            setResult();
                        }else{
                            tvDialogOperationResult.setText("0");
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

    private void setUI() {
        TransferOperation transferOperation = getArguments().getParcelable(EXTRA_OPERATION_DATA);
        mOperationFrom = transferOperation.getOutComeOperation();
        mOperationTo = transferOperation.getInComeOperation();

        mStorageFrom = mOperationFrom.getStorage();
        mStorageTo = mOperationTo.getStorage();

        oldStorageFrom = mStorageFrom;
        oldAmountFrom = oldStorageFrom.getAmountStorage();
        oldStorageTo = mStorageTo;
        oldAmountTo = oldStorageTo.getAmountStorage();

        etDate.setText(Utils.getDate(mOperationTo.getDate()));
        etMount.setText(String.valueOf(mOperationFrom.getAmount()));
        tvDialogOperationResult.setText(String.valueOf(mOperationTo.getAmount()));
        etItem.setText(String.valueOf(mOperationTo.getAmount()/mOperationFrom.getAmount()));
        tvDialogOperationResultCurrency.setText(mOperationTo.getStorage().getCurency().getShortName());
        tvCurrency2.setText(mOperationTo.getStorage().getCurency().getShortName());
        etNameOperation.setText(mOperationTo.getNameOperation());


        setOneStorage();
        setTwoStorage();
    }

    public static CreateTransferDialog newInstance(TransferOperation operation) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_OPERATION_DATA, operation);
        CreateTransferDialog fragment = new CreateTransferDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateTransferDialog newInstance(int template) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_TEMPLATE_DATA, template);
        CreateTransferDialog fragment = new CreateTransferDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

        DialogFragment dialog;

        switch (v.getId()){
            case R.id.imOneStorage:
            case R.id.tvOneStorageName:
            case R.id.tvOneStorageCurrency:
            case R.id.imStorageChoice:
                dialog = new ChoiceStorageDialog();
                dialog.setTargetFragment(CreateTransferDialog.this, REQUEST_STORAGE_ONE_DATA);
                dialog.show(getFragmentManager(), "ChoiceStorageDialog");
                break;
            case R.id.imTwoStorage:
            case R.id.tvTwoStorageName:
            case R.id.tvTwoStorageCurrency:
            case R.id.imStorageChoice2:
                dialog = new ChoiceStorageDialog();
                dialog.setTargetFragment(CreateTransferDialog.this, REQUEST_STORAGE_TWO_DATA);
                dialog.show(getFragmentManager(), "ChoiceStorageDialog");
                break;
            case R.id.imDialogAmoutCalc:
                dialog = new DialogCalculator();
                dialog.setTargetFragment(CreateTransferDialog.this, REQUEST_CALC_ONE_DATA);
                dialog.show(getFragmentManager(), "DialogCalculator");
                break;
            case R.id.imDialogCurrencyCalc:
                dialog = new DialogCalculator();
                dialog.setTargetFragment(CreateTransferDialog.this, REQUEST_CALC_TWO_DATA);
                dialog.show(getFragmentManager(), "DialogCalculator");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d(TAG, "Получение результата");
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode){
            case REQUEST_STORAGE_ONE_DATA:
                mStorageFrom =
                        (Storage)data.getParcelableExtra(ChoiceStorageDialog.EXTRA_STORAGE_DATA);

                setOneStorage();
 /*               m1ImageStorage = storage.getImageStorage();
                if(etNameOperation.length()!=0 && etMount.length() !=0 && m1ImageStorage != 0){
                    positiveButton.setEnabled(true);
                }*/
                //imListStorage.setImageResource(storage.getImageStorage());
                break;
            case REQUEST_STORAGE_TWO_DATA:
                mStorageTo =
                        (Storage)data.getParcelableExtra(ChoiceStorageDialog.EXTRA_STORAGE_DATA);

                setTwoStorage();
                break;
            case REQUEST_CALC_ONE_DATA:
                String calcData = (String)data.getStringExtra(DialogCalculator.EXTRA_CALC_DATA);

                if(calcData.equals("0")){
                    etMount.setText("");
                }else if(Character.toLowerCase(calcData.charAt(0)) == Character.toLowerCase('-')){
                    etMount.setText(calcData.substring(1));
                }else{
                    etMount.setText(calcData);
                }
                break;
            case REQUEST_CALC_TWO_DATA:
                String calcItem = (String)data.getStringExtra(DialogCalculator.EXTRA_CALC_DATA);

                if(calcItem.equals("0")){
                    etItem.setText("");
                }else if(Character.toLowerCase(calcItem.charAt(0)) == Character.toLowerCase('-')){
                    etItem.setText(calcItem.substring(1));
                }else{
                    etItem.setText(calcItem);
                }
                break;
        }
    }

    private void setOneStorage(){
        mImageOneStorage = mStorageFrom.getImageStorage();
        tvOneStorageName.setText(mStorageFrom.getNameStorage());
        tvOneStorageName.setTextColor(getResources().getColor(R.color.black));
        imOneStorage.setImageResource(mStorageFrom.getImageStorage());

        tvOneStorageCurrency.setText(mStorageFrom.getCurency().getShortName());
        tvCurrency1.setText(mStorageFrom.getCurency().getShortName());
        if(positiveButton != null) {
            isImageEmpti();
        }
    }

    private void setTwoStorage(){
        mImageTwoStorage = mStorageTo.getImageStorage();
        tvTwoStorageName.setText(mStorageTo.getNameStorage());
        tvTwoStorageName.setTextColor(getResources().getColor(R.color.black));
        imTwoStorage.setImageResource(mStorageTo.getImageStorage());

        tvTwoStorageCurrency.setText(mStorageTo.getCurency().getShortName());
        tvCurrency2.setText(mStorageTo.getCurency().getShortName());
        tvDialogOperationResultCurrency.setText(mStorageTo.getCurency().getShortName());
        if(positiveButton != null) {
            isImageEmpti();
        }
    }

    private void isImageEmpti(){
        if (etNameOperation.length() == 0 || etMount.length() == 0 || mImageOneStorage == 0
                || mImageTwoStorage == 0 || etItem.length() == 0) {
            positiveButton.setEnabled(false);

        } else {
            positiveButton.setEnabled(true);
        }
    }

    private void setResult(){
        double a = Double.parseDouble(etMount.getText().toString());
        double b = Double.parseDouble(etItem.getText().toString());

        double res = a * b;
        tvDialogOperationResult
                .setText(String.valueOf(Utils.getTwoDouble(String.valueOf(res))));
    }
}

