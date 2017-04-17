package vitalu.ua.gmail.com.homemoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 15.02.2016.
 */
public class DebtAdapter extends ArrayAdapter<Operation> {

    private static final int TO_BORROW = 4;//взять в долг
    private static final int TO_LEND = 5;//дать в долг
    private Context context;

    public DebtAdapter(Context context, List<Operation> operations) {
        super(context, 0, operations);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Если мы не получили представление, заполняем его

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_list_debt, parent, false);
        }

        Operation operation = getItem(position);

        TextView tvOperationName =
                (TextView)convertView.findViewById(R.id.tvOperationName);
        tvOperationName.setText(operation.getNameOperation());


        TextView tvSourceName =
                (TextView)convertView.findViewById(R.id.tvSourceName);
        //tvSourceName.setText(String.valueOf(operation.getSoyrce().getNameSours()));

        TextView tvOperationAmount =
                (TextView)convertView.findViewById(R.id.tvOperationAmount);
        //  tvOperationAmount.setText(String.valueOf(operation.getAmount()));

        TextView tvOperationCurrency =
                (TextView)convertView.findViewById(R.id.tvOperationCurrency);
        tvOperationCurrency.setText(operation.getStorage().getCurency().getShortName());


        TextView tvDateOperation =
                (TextView)convertView.findViewById(R.id.tvDateOperation);
        tvDateOperation.setText(String.valueOf(Utils.getDate(operation.getDate())));

        if(operation.getSoyrce().getType().getId() == TO_LEND){
           // tvSourceName.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationAmount.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationCurrency.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationAmount.setText(String.valueOf(operation.getAmount()));
            tvSourceName.setText(R.string.you_hould_in);
        }else if(operation.getSoyrce().getType().getId() == TO_BORROW){
            //tvSourceName.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationAmount.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationCurrency.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationAmount.setText(String.valueOf(operation.getAmount()));
            tvSourceName.setText(R.string.you_hould_out);
        }

        if(operation.getAmount() == 0){
            //tvSourceName.setTextColor(convertView.getResources().getColor(R.color.black));
            tvOperationAmount.setTextColor(convertView.getResources().getColor(R.color.black));
            tvOperationCurrency.setTextColor(convertView.getResources().getColor(R.color.black));
            tvOperationAmount.setText(String.valueOf(operation.getAmount()));
            tvSourceName.setText("");
        }
        tvDateOperation.setTextColor(convertView.getResources().getColor(R.color.colorAccent));

        return convertView;
    }

/*    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }*/
}