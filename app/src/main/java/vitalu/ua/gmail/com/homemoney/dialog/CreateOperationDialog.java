package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 01.01.2016.
 */
public class CreateOperationDialog extends DialogFragment
        implements View.OnClickListener {

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_OPERATION_DATA = "vitalu.ua.gmail.com.homemoney.dialog.operation_data";

    public static final int INCOM_OPERATION = 1;
    public static final int OUTCOM_OPERATION = 2;
    public static final int TRANSFER_OPERATION = 3;
    public static final int TO_BORROW = 4;//взять в долг
    public static final int TO_LEND = 5;//дать в долг
    public static final int TEMPLATE = 6;//шаблон
    public static final int INCOM_TEMPLATE = 7;//шаблон
    public static final int OUTCOME_TEMPLATE = 8;//шаблон


    public static final  int REQUEST_CALC_DATA = 1;
    public static final int REQUEST_STORAGE_DATA = 2;
    public static final int REQUEST_SOURCE_DATA = 3;

    final public static String KEY_ID_OPERATION= "id_operation";
    final public static String KEY_TITLE_OPERATION = "title_operation";
    final public static String KEY_NAME_OPERATION = "name_operation";
    public static final int DEBT_OPERATION = 4;
    public static final String KEY_TEMPLATE_OPERATION = "tamplate_operation";

    public interface OnItemChanged{//для извещения activity
        void itemChanged();
        void itemAdding(Operation operation);
    }

    private OnItemChanged onItemChanged;

/*    final private String KEY_OPERATION = "operation";
    final private String KEY_AMOUNT = "amount";
    final private String KEY_DESCRIPTION = "description";
    final private String KEY_DATE = "date";
    final private String KEY_FOTO = "foto";*/

    private EditText etNameOperation;
    private EditText etDate;
    private EditText etMount;
    private EditText etDescription;
    private TextView tvStorageName;
    private ImageView imListStorage;
    private TextView tvSourceName;
    TextInputLayout tilOperation;
    Spinner spOperationCategory;
    ImageView imSourceChoice;

    private List<Storage> listStorage;

    private int mOperationType;
    private int mImageStorage = 0;
    Button positiveButton;
    int operationId;
    int templateOperation = -1;//операция шаблона доход или расход

    int debt;

    Storage oldStorage;
    double oldAmount;

    Source source;
    Storage storage;
    Operation operation;
    Calendar calendar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        operationId = getArguments().getInt(KEY_ID_OPERATION, 1);
        templateOperation = getArguments().getInt(KEY_TEMPLATE_OPERATION, -1);
        String operationTitle = getArguments().getString(KEY_TITLE_OPERATION, getString(R.string.dialog_title_add_incom));
        String operationName = getArguments().getString(KEY_NAME_OPERATION, getString(R.string.db_incom));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(operationTitle);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_create_operation, null);

        tilOperation =
                (TextInputLayout) container.findViewById(R.id.tilOperationName);
        etNameOperation = tilOperation.getEditText();

        TextInputLayout tilDate =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationDate);
        etDate = tilDate.getEditText();

        final TextInputLayout tilMount =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationMount);
        etMount = tilMount.getEditText();

        final TextInputLayout tilDescription =
                (TextInputLayout) container.findViewById(R.id.tilDialogOperationDescription);
        etDescription = tilDescription.getEditText();

        tvStorageName = (TextView) container.findViewById(R.id.tvStorageName);
        tvSourceName = (TextView) container.findViewById(R.id.tvSourceName);
        imListStorage = (ImageView) container.findViewById(R.id.imOneStorage);
        imSourceChoice = (ImageView) container.findViewById(R.id.imSourceChoice);
        ImageView imageCalc = (ImageView) container.findViewById(R.id.imDialogAmoutCalc);
        ImageView imStorageChoice = (ImageView) container.findViewById(R.id.imStorageChoice);

        tvSourceName.setOnClickListener(this);
        imSourceChoice.setOnClickListener(this);
        imageCalc.setOnClickListener(this);
        imStorageChoice.setOnClickListener(this);
        imListStorage.setOnClickListener(this);
        tvStorageName.setOnClickListener(this);

        spOperationCategory = (Spinner)container.findViewById(R.id.spOperationCategory);

        listStorage = StorageQuery.getVisibleStorages();

        tilOperation.setHint(getResources().getString(R.string.title_operation));


////////////////////////////////////////////////////////////////////////////////////////////////////
        if(getArguments().getParcelable(EXTRA_OPERATION_DATA) != null){//если редактирование
           // builder.setTitle(R.string.dialog_title_update_currency);


            setUI();
        }else{
           // builder.setTitle(R.string.dialog_title_currency);


            /////////////////////////////////////////////////////////////////////////////////////////////
            if (!listStorage.isEmpty()) {
                mImageStorage = listStorage.get(0).getImageStorage();
                tvStorageName.setText(listStorage.get(0).getNameStorage());
                tvStorageName.setTextColor(getResources().getColor(R.color.black));
                imListStorage.setImageResource(listStorage.get(0).getImageStorage());
                storage = listStorage.get(0);
            }

            etNameOperation.setText(operationName);
           // etDate.setText(Utils.getDate(System.currentTimeMillis()));

            if(operationId == INCOM_OPERATION){
                etNameOperation.setTextColor(getResources().getColor(R.color.green));
                //tilOperation.setHint(getResources().getString(R.string.db_incom));
                mOperationType = ChoiceSourceDialog.INCOM_OPERATION;
                tvSourceName.setText(SourceQuery.getVisibleInComeParentData()
                        .get(0).get(SourceQuery.GROUP_NAME).getNameSours());
                source = SourceQuery.getVisibleInComeParentData()
                        .get(0).get(SourceQuery.GROUP_NAME);
            }else if(operationId == OUTCOM_OPERATION){
                etNameOperation.setTextColor(getResources().getColor(R.color.red));
               // tilOperation.setHint(getResources().getString(R.string.db_outcom));
                mOperationType = ChoiceSourceDialog.OUTCOM_OPERATION;
                tvSourceName.setText(SourceQuery.getVisibleOutComeParentData()
                        .get(0).get(SourceQuery.GROUP_NAME).getNameSours());
                source = SourceQuery.getVisibleOutComeParentData()
                        .get(0).get(SourceQuery.GROUP_NAME);
            }else if(operationId == DEBT_OPERATION) {
                etNameOperation.setTextColor(getResources().getColor(R.color.black));
                // tilOperation.setHint(getResources().getString(R.string.db_outcom));
                mOperationType = ChoiceSourceDialog.OUTCOM_OPERATION;
               /* tvSourceName.setText(SourceQuery.getSourceDebt()
                        .get(0).getNameSours());*/
                source = SourceQuery.getSourceDebt()
                        .get(0);
                etNameOperation.setText("");

                tilOperation.setHint(getResources().getString(R.string.title_name));

                imSourceChoice.setVisibility(View.INVISIBLE);
                tvSourceName.setVisibility(View.INVISIBLE);
                spOperationCategory.setVisibility(View.VISIBLE);

                /////////////////////////////////////////////
                ArrayAdapter<Source> operationAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        SourceQuery.getSourceDebt());

                spOperationCategory.setAdapter(operationAdapter);

                /*int idOperation = getArguments().getInt(EXTRA_SOURCE_CATEGORY);
                if(idOperation > 0){*/
                    //spOperationCategory.setSelection(--idOperation);
               // }
                spOperationCategory.setSelection(0);
                spOperationCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        source = SourceQuery.getSourceDebt()
                                .get(position);
                        if(position == 0) {
                            debt = TO_BORROW;
                        }else{
                            debt = TO_LEND;
                        }
                        //Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ///////////////////////////

                //Wiev relativeLayout2 = (Layout)container.findViewById(R.layout.relativeLayout2);
            }

            /*etNameOperation.setText(operationName);*/
            etDate.setText(Utils.getDate(System.currentTimeMillis()));

            operation = new Operation();
        }

        tilDate.setHint(getResources().getString(R.string.dialog_date));
        tilDescription.setHint(getResources().getString(R.string.dialog_description));
        tilMount.setHint(getResources().getString(R.string.dialog_mount));

        builder.setView(container);

 /*       if (savedInstanceState != null) {

            etNameOperation.setText(savedInstanceState.getString(KEY_OPERATION));
            etMount.setText(savedInstanceState.getString(KEY_AMOUNT));
            etDate.setText(savedInstanceState.getString(KEY_DATE));
            etDescription.setText(savedInstanceState.getString(KEY_DESCRIPTION));
        }*/

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
        }

        );
        /////////////////////////////////////////////////////
        if(templateOperation == TEMPLATE){

            tilDate.setVisibility(View.INVISIBLE);
            etDate.setVisibility(View.INVISIBLE);
        }

////////////////////////////////////////////////////////////////////////////////
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                operation.setNameOperation(etNameOperation.getText().toString())
                        .setAmount(Utils.getTwoDouble(etMount.getText().toString()))
                        .setDate(calendar.getTimeInMillis())
                        .setSoyrce(source)
                        .setStorage(storage)
                        .setDescription(etDescription.getText().toString());
                        //.setImagePath()

                try {
                    onItemChanged = (OnItemChanged) getActivity();
                    /*onItemChanged.itemChanged();*/
                } catch (ClassCastException e) {
                    throw new ClassCastException(getActivity().toString()
                            + " необходимо реализовать OnItemChanged");
                }

                if(templateOperation == TEMPLATE){
                    if (getArguments().getParcelable(EXTRA_OPERATION_DATA) != null) {
                        Log.d(TAG, "---------------------");
                        PatternQuery.updateTemplate(operation);
                        onItemChanged.itemChanged();
                    } else {
                        operation.setDate(0);
                        PatternQuery.addTemplate(operation);
                        onItemChanged.itemAdding(operation);
                    }
                }else {
                    if (getArguments().getParcelable(EXTRA_OPERATION_DATA) != null) {
                        Log.d(TAG, "---------------------");

                        if (operationId == DEBT_OPERATION) {
                            Log.d(TAG, operation.getSoyrce().getNameSours());
                            Log.d(TAG, String.valueOf(operation.getSoyrce().getId()));

                            OperationQuery.updateDebt(operation, oldAmount, oldStorage);
                            sendResult(Activity.RESULT_OK);
                        } else {
                            OperationQuery.updateOperation(operation, oldAmount, oldStorage);
                        }
                        onItemChanged.itemChanged();

                    } else {
                        if (operationId == DEBT_OPERATION) {

                       /* Log.d(TAG, operation.getSoyrce().getNameSours());
                        Log.d(TAG, String.valueOf(operation.getSoyrce().getId()));*/

                            operation.setId((int) OperationQuery.addOperationDebt(operation, debt));
                            onItemChanged.itemAdding(operation);
                            Toast.makeText(getActivity(), getString(R.string.add_operation), Toast.LENGTH_SHORT).show();
                        } else {
                            OperationQuery.addOperation(operation);
                            onItemChanged.itemAdding(operation);
                            Toast.makeText(getActivity(), getString(R.string.add_operation), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                dialog.dismiss();
            }

        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener()

                {
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
                if (etNameOperation.length() == 0 || etMount.length() == 0 || mImageStorage == 0) {
                    positiveButton.setEnabled(false);
                        if (etNameOperation.length() == 0){
                            tilOperation.setError(getResources().getString(R.string.dialog_error_empty_title));
                        }
                            tilMount.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etNameOperation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.length() == 0 || etMount.length() == 0 || mImageStorage == 0) {
                            positiveButton.setEnabled(false);
                            if (s.length() == 0)
                                tilOperation.setError(getResources().getString(R.string.dialog_error_empty_title));
                            else
                                tilOperation.setErrorEnabled(false);
                        } else {
                            positiveButton.setEnabled(true);
                            tilOperation.setErrorEnabled(false);
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
                        if (s.length() == 0 || etNameOperation.length() == 0 || mImageStorage == 0) {
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

    private void setUI() {
        operation = getArguments().getParcelable(EXTRA_OPERATION_DATA);
        oldAmount = operation.getAmount();
        oldStorage = operation.getStorage();

       /* Log.d(TAG, "oldAmount = " + oldAmount);
        Log.d(TAG, "oldStorage = " + oldStorage.getAmountStorage());*/

        mImageStorage = operation.getStorage().getImageStorage();
        tvStorageName.setText(operation.getStorage().getNameStorage());
        tvStorageName.setTextColor(getResources().getColor(R.color.black));
        imListStorage.setImageResource(operation.getStorage().getImageStorage());
        storage = operation.getStorage();

        if(operationId == INCOM_OPERATION){
            etNameOperation.setTextColor(getResources().getColor(R.color.green));
            //tilOperation.setHint(getResources().getString(R.string.db_incom));
            mOperationType = ChoiceSourceDialog.INCOM_OPERATION;
            tvSourceName.setText(operation.getSoyrce().getNameSours());
            source = operation.getSoyrce();
            //tilOperation.setHint(getResources().getString(R.string.db_incom));
        }else if(operationId == DEBT_OPERATION){
            imSourceChoice.setVisibility(View.INVISIBLE);
            tvSourceName.setVisibility(View.INVISIBLE);
            spOperationCategory.setVisibility(View.VISIBLE);

            etNameOperation.setTextColor(getResources().getColor(R.color.black));
            tilOperation.setHint(getResources().getString(R.string.title_name));
            source = operation.getSoyrce();



            /////////////////////////////////////////////
            ArrayAdapter<Source> operationAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    SourceQuery.getSourceDebt());

            spOperationCategory.setAdapter(operationAdapter);

                /*int idOperation = getArguments().getInt(EXTRA_SOURCE_CATEGORY);
                if(idOperation > 0){*/
            //spOperationCategory.setSelection(--idOperation);
            // }
            if(SourceQuery.getSourceDebt().get(0).getType().getId()==operation.getSoyrce().getType().getId()) {
                spOperationCategory.setSelection(0);
                //spOperationCategory.setSelection(SourceQuery.getSourceDebt().indexOf(operation.getSoyrce()));
            }else {
                spOperationCategory.setSelection(1);
            }
            spOperationCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    source = SourceQuery.getSourceDebt()
                            .get(position);
                    if (position == 0) {
                        debt = TO_BORROW;
                    } else {
                        debt = TO_LEND;
                    }
                    //Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            etNameOperation.setTextColor(getResources().getColor(R.color.red));
            //tilOperation.setHint(getResources().getString(R.string.db_outcom));
            mOperationType = ChoiceSourceDialog.OUTCOM_OPERATION;
            tvSourceName.setText(operation.getSoyrce().getNameSours());
            source = operation.getSoyrce();
           // tilOperation.setHint(getResources().getString(R.string.db_outcom));
        }

        etNameOperation.setText(operation.getNameOperation());
        etDate.setText(Utils.getDate(operation.getDate()));
        etMount.setText(String.valueOf(operation.getAmount()));
        etDescription.setText(operation.getDescription());
        calendar.setTimeInMillis(operation.getDate());
       // tilDate.setHint(getResources().getString(R.string.dialog_date));
        //tilDescription.setHint(getResources().getString(R.string.dialog_description));
        //tilMount.setHint(getResources().getString(R.string.dialog_mount));

    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_OPERATION, etNameOperation.getText().toString());
        outState.putString(KEY_AMOUNT, etMount.getText().toString());
        outState.putString(KEY_DATE, etDate.getText().toString());
        outState.putString(KEY_DESCRIPTION, etDescription.getText().toString());
    }*/


    @Override
    public void onClick(View v) {

        DialogFragment dialog;
        switch (v.getId()) {
            case R.id.imDialogAmoutCalc:
               // Log.d(TAG, "Запуск калькулятора");
                dialog = new DialogCalculator();
                dialog.setTargetFragment(CreateOperationDialog.this, REQUEST_CALC_DATA);
                dialog.show(getFragmentManager(), "DialogCalculator");
                break;
            case R.id.imStorageChoice:
            case R.id.imOneStorage:
            case R.id.tvStorageName:
                dialog = new ChoiceStorageDialog();
                dialog.setTargetFragment(CreateOperationDialog.this, REQUEST_STORAGE_DATA);
                dialog.show(getFragmentManager(), "ChoiceStorageDialog");
                break;
            case R.id.imSourceChoice:
            case R.id.tvSourceName:
                dialog = ChoiceSourceDialog.newInstance(mOperationType);
                dialog.setTargetFragment(CreateOperationDialog.this, REQUEST_SOURCE_DATA);
                dialog.show(getFragmentManager(), "ChoiceSourceDialog");
                break;
        }
    }

    public static CreateOperationDialog newInstance(int operationId,
                                                    String titleOperation, String nameOperation) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_OPERATION, operationId);
        args.putString(KEY_TITLE_OPERATION, titleOperation);
        args.putString(KEY_NAME_OPERATION, nameOperation);
        CreateOperationDialog fragment = new CreateOperationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateOperationDialog newInstance(int operationId,
                                                    String titleOperation,
                                                    String nameOperation,
                                                    int templateOPeration) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_OPERATION, operationId);
        args.putString(KEY_TITLE_OPERATION, titleOperation);
        args.putString(KEY_NAME_OPERATION, nameOperation);
        args.putInt(KEY_TEMPLATE_OPERATION, templateOPeration);
        CreateOperationDialog fragment = new CreateOperationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateOperationDialog newInstance(int operationId,
                                                        String titleOperation,
                                                        String nameOperation,
                                                        Operation operation) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_OPERATION, operationId);
        args.putString(KEY_TITLE_OPERATION, titleOperation);
        args.putString(KEY_NAME_OPERATION, nameOperation);
        args.putParcelable(EXTRA_OPERATION_DATA, operation);
        CreateOperationDialog fragment = new CreateOperationDialog();
        fragment.setArguments(args);
        return fragment;
    }
    ///////////////////////////////////////
    public static CreateOperationDialog newInstance(int operationId,
                                                    String titleOperation,
                                                    String nameOperation,
                                                    Operation operation,
                                                    int templateOPeration) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_OPERATION, operationId);
        args.putString(KEY_TITLE_OPERATION, titleOperation);
        args.putString(KEY_NAME_OPERATION, nameOperation);
        args.putParcelable(EXTRA_OPERATION_DATA, operation);
        args.putInt(KEY_TEMPLATE_OPERATION, templateOPeration);
        CreateOperationDialog fragment = new CreateOperationDialog();
        fragment.setArguments(args);
        return fragment;
    }
    //////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d(TAG, "Получение результата");
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode){
            case REQUEST_CALC_DATA:
                String calcData = (String)data.getStringExtra(DialogCalculator.EXTRA_CALC_DATA);

                //Log.d(TAG, "Получение результата REQUEST_CALC_DATA " + calcData);

                if(calcData.equals("0")){
                    etMount.setText("");
                }else if(Character.toLowerCase(calcData.charAt(0)) == Character.toLowerCase('-')){
                    etMount.setText(calcData.substring(1));
                }else{
                    etMount.setText(calcData);
                }
                break;
            case REQUEST_STORAGE_DATA:
                storage =
                        (Storage)data.getParcelableExtra(ChoiceStorageDialog.EXTRA_STORAGE_DATA);

               // Log.d(TAG, "Получение результата REQUEST_CALC_DATA ");
                tvStorageName.setText(storage.getNameStorage());
                tvStorageName.setTextColor(getResources().getColor(R.color.black));
                mImageStorage = storage.getImageStorage();
                if(etNameOperation.length()!=0 && etMount.length() !=0 && mImageStorage != 0){
                    positiveButton.setEnabled(true);
                }
                imListStorage.setImageResource(storage.getImageStorage());
                break;
            case REQUEST_SOURCE_DATA:
                source =
                        (Source)data.getParcelableExtra(ChoiceSourceDialog.EXTRA_SOURCE_DATA);

                tvSourceName.setText(source.getNameSours());
                break;
        }
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}


