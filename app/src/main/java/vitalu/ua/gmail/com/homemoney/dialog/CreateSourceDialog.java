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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.database.database_query.SourceQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Source;
import vitalu.ua.gmail.com.homemoney.model.database_model.TypeOperation;

/**
 * Created by Виталий on 29.01.2016.
 */
public class CreateSourceDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final int CREATE_SOURCE_ACTIVITY = 1;
    public static final int CREATE_SOURCE_FRAGMENT = 2;

    private static final int REQUST_PARENT_CHOICE = 1;
    private static final String CREATE_SOURCE = "vitalu.ua.gmail.com.homemoney.dialog.create_source";
    public static final String EXTRA_SOURCE_DATA = "vitalu.ua.gmail.com.homemoney.dialog.source_data";
    public static final String EXTRA_SOURCE_CATEGORY = "vitalu.ua.gmail.com.homemoney.dialog.source_category";
    private int sourceCategory = 1;
    private static final int EMPTY_PARENT = 0;
    private int idParent;
    private Source source;
    TextView tvChoiceSource;

    EditText etDialogCategoryName;
    Spinner spOperationCategory;

    public interface AddingSourceListener{
        void onAdded(Source source);
    }

    private AddingSourceListener addingSourceListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int mSourceCreate = getArguments().getInt(CREATE_SOURCE);

        idParent = EMPTY_PARENT;
        //source = new Source();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.create_category);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_category, null);

        final TextInputLayout tilDialogCategoryName = (TextInputLayout)view
                .findViewById(R.id.tilDialogCategoryName);
        tilDialogCategoryName.setHint(getResources().getString(R.string.dialog_category_name));
        etDialogCategoryName = tilDialogCategoryName.getEditText();

        tvChoiceSource = (TextView)view.findViewById(R.id.tvParentcategory);
        tvChoiceSource.setOnClickListener(this);
        ImageView imSourceChoice = (ImageView)view.findViewById(R.id.imSourceChoice);
        imSourceChoice.setOnClickListener(this);


        //Log.d(TAG, "idOperation = " + idOperation);

        spOperationCategory = (Spinner)view.findViewById(R.id.spOperationCategory);

        ArrayAdapter<String> operationAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{getString(R.string.db_incom), getString(R.string.db_outcom)});

        spOperationCategory.setAdapter(operationAdapter);

        int idOperation = getArguments().getInt(EXTRA_SOURCE_CATEGORY);
        if(idOperation > 0){
            spOperationCategory.setSelection(--idOperation);
        }

        spOperationCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceCategory = ++position;
               // tvChoiceSource.setText(R.string.parent_category);
                idParent = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(getArguments().getParcelable(EXTRA_SOURCE_DATA) != null){
            builder.setTitle(R.string.update_category);
            source = getArguments().getParcelable(EXTRA_SOURCE_DATA);
            setUI();
        }else{
            builder.setTitle(R.string.create_category);
            source = new Source();
        }

        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                source.setNameSours(etDialogCategoryName.getText().toString());
                source.setType(new TypeOperation().setId(sourceCategory));
                if(idParent != EMPTY_PARENT){
                    source.setParentId(idParent);
                }else{
                    source.setParentId(EMPTY_PARENT);
                }

                if(mSourceCreate == CREATE_SOURCE_ACTIVITY){//Если диалог запущен с activity

                    try {
                        addingSourceListener = (AddingSourceListener)getActivity();
                        addingSourceListener.onAdded(source);// передача активити
                    } catch (ClassCastException e) {
                        throw new ClassCastException(getActivity().toString()
                                + " необходимо реализовать AddingSourceListener");
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

                if(etDialogCategoryName.length() == 0){
                    positiveButton.setEnabled(false);
                    tilDialogCategoryName.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etDialogCategoryName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            positiveButton.setEnabled(false);
                            tilDialogCategoryName.setError(getResources().getString(R.string.dialog_error_empty_title));
                        }else{
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
                DialogFragment dialogFragment = ChoiceParentCategory.newInstance(sourceCategory);
                dialogFragment.setTargetFragment(CreateSourceDialog.this, REQUST_PARENT_CHOICE);
                dialogFragment.show(getFragmentManager(), "ChoiceParentCategory");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//получаем родительскую категорию

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUST_PARENT_CHOICE:
                idParent = data.getIntExtra(ChoiceParentCategory.EXTRA_PARENT_DATA, 0);
               // positionList = data.getIntExtra(ChoiceParentCategory.ETRA_ITEM_LIST, 0);
                if(idParent == EMPTY_PARENT){
                    tvChoiceSource.setText(R.string.parent_category);
                }else {
                    tvChoiceSource.setText(SourceQuery.getVisibleSourceParent(idParent).get(0).getNameSours());
                }
                //Toast.makeText(getActivity(), "id = " + idParent, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static CreateSourceDialog newInstance(int sourceCreate) {//Создание диалога с источником
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        CreateSourceDialog fragment = new CreateSourceDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateSourceDialog newInstance(int sourceCreate, int idOperation) {//Создание диалога с источником
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        args.putInt(EXTRA_SOURCE_CATEGORY, idOperation);
        CreateSourceDialog fragment = new CreateSourceDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CreateSourceDialog newInstance(Source source, int sourceCreate) {//Создание диалога с источником
        Bundle args = new Bundle();
        args.putInt(CREATE_SOURCE, sourceCreate);
        args.putParcelable(EXTRA_SOURCE_DATA, source);
        CreateSourceDialog fragment = new CreateSourceDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {//отправка результата фрагменту
        if (getTargetFragment() == null)
            return;

        Log.d(TAG, "/--------CreateSourceDialog-----sendResult-----------------------/");
        Log.d(TAG, source.getNameSours());
        Log.d(TAG, String.valueOf(source.getId()));
        Log.d(TAG, String.valueOf(source.getParentId()));
        Log.d(TAG, "/-----------------sendResult-------------------/");

        Intent i = new Intent();
        i.putExtra(EXTRA_SOURCE_DATA, source);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    private void setUI(){
        etDialogCategoryName.setText(source.getNameSours());
        spOperationCategory.setSelection(source.getType().getId() - 1);

       // Toast.makeText(getActivity(), SourceQuery.getVisibleSourceParent(source.getParentId()).get(0).getNameSours(), Toast.LENGTH_SHORT).show();
        if(source.getParentId() > 0) {
            idParent = source.getParentId();
            //Toast.makeText(getActivity(), SourceQuery.getVisibleSourceParent(source.getParentId()).get(0).getNameSours(), Toast.LENGTH_SHORT).show();
            tvChoiceSource.setText(SourceQuery.getVisibleSourceParent(idParent).get(0).getNameSours());
        }





        //getString(R.string.db_incom), getString(R.string.db_outcom)

    }
}
