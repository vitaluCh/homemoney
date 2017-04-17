package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.adapter.CurrencyAdapter;
import vitalu.ua.gmail.com.homemoney.database.database_query.CurrencyQuery;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

/**
 * Created by Виталий on 21.01.2016.
 */
public class ChoiceCurrencyDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String EXTRA_CURRENCY_DATA = "vitalu.ua.gmail.com.homemoney.dialog.currency_data";
    private static final int REQUEST_CURRENCY_DATA = 1;

    private List<Currency> currencyList;
    private CurrencyAdapter currencyAdapter;
    private Currency currency;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        currencyList = CurrencyQuery.getCurrencys();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choice_currency);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_fragment, null);

        Button addButtonChoice = (Button)view.findViewById(R.id.addButtonChoice);
        addButtonChoice.setText(R.string.add_currency);
        addButtonChoice.setOnClickListener(this);

        ListView listView = (ListView)view.findViewById(R.id.listChoiceFragment);
        currencyAdapter = new CurrencyAdapter(getActivity(), currencyList);
        listView.setAdapter(currencyAdapter);
        listView.setOnItemClickListener(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addButtonChoice:
                //Вызвать диалог для создания новой валюты
                /*DialogFragment dialog = new CreateCurrencyDialog();
                dialog.setTargetFragment(ChoiceCurrencyDialog.this, REQUEST_CURRENCY_DATA);
                dialog.show(getFragmentManager(), "CreateCurrencyDialog");*/

                DialogFragment addingStorageDialogFragment = CreateCurrencyDialog
                        .newInstance(CreateCurrencyDialog.CREATE_SOURCE_FRAGMENT);//источник создания диалог или activity
                addingStorageDialogFragment.setTargetFragment(ChoiceCurrencyDialog.this, REQUEST_CURRENCY_DATA);
                addingStorageDialogFragment.show(getFragmentManager(), "CreateStorageDialog");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        currency = currencyList.get(position);
        sendResult(Activity.RESULT_OK);//отправить выбраную валюту
        dismiss();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_CURRENCY_DATA, currency);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CURRENCY_DATA) {
            Currency currency =
                    (Currency)data.getParcelableExtra(CreateCurrencyDialog.EXTRA_CURRENCY_DATA);

            long idNewCurrency = CurrencyQuery.addCurrency(currency);
            currency.setId((int) idNewCurrency);

            currencyList.add(currency);

            currencyAdapter.notifyDataSetChanged();
        }
    }
}
