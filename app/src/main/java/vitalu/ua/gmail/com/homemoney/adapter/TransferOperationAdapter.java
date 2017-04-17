package vitalu.ua.gmail.com.homemoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 11.02.2016.
 */
public class TransferOperationAdapter extends ArrayAdapter<TransferOperation> {

   // private static final int INCOM = 1;
    private Context context;


    public TransferOperationAdapter(Context context, List<TransferOperation> operations) {

        super(context, 0, operations);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Если мы не получили представление, заполняем его

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_list_transfer_operation, parent, false);
        }

        TransferOperation operation = getItem(position);

        ImageView imOperationStorageFrom =
                (ImageView)convertView.findViewById(R.id.imOperationStorageFrom);
        imOperationStorageFrom.setImageResource(operation.getOutComeOperation().getStorage().getImageStorage());

        ImageView imOperationStorageTo =
                (ImageView)convertView.findViewById(R.id.imOperationStorageTo);
        imOperationStorageTo.setImageResource(operation.getInComeOperation().getStorage().getImageStorage());

        TextView tvOperationName =
                (TextView)convertView.findViewById(R.id.tvOperationName);
        tvOperationName.setText(operation.getInComeOperation().getNameOperation());

        TextView tvOperationAmountFrom =
                (TextView)convertView.findViewById(R.id.tvOperationAmountFrom);
        tvOperationAmountFrom.setText("- " + String.valueOf(operation.getOutComeOperation().getAmount()));
        tvOperationAmountFrom.setTextColor(convertView.getResources().getColor(R.color.red));

        TextView tvOperationCurrencyFrom =
                (TextView)convertView.findViewById(R.id.tvOperationCurrencyFrom);
        tvOperationCurrencyFrom.setText(String.valueOf(operation.getOutComeOperation().getStorage().getCurency().getShortName()));
        tvOperationCurrencyFrom.setTextColor(convertView.getResources().getColor(R.color.red));

        TextView tvOperationAmountTo =
                (TextView)convertView.findViewById(R.id.tvOperationAmountTo);
        tvOperationAmountTo.setText(String.valueOf(operation.getInComeOperation().getAmount()));
        tvOperationAmountTo.setTextColor(convertView.getResources().getColor(R.color.green));

        TextView tvOperationCurrencyTo =
                (TextView)convertView.findViewById(R.id.tvOperationCurrencyTo);
        tvOperationCurrencyTo.setText(String.valueOf(operation.getInComeOperation().getStorage().getCurency().getShortName()));
        tvOperationCurrencyTo.setTextColor(convertView.getResources().getColor(R.color.green));

        TextView tvDateOperation =
                (TextView)convertView.findViewById(R.id.tvDateOperation);
        if(operation.getInComeOperation().getDate()>0){
            tvDateOperation.setText(String.valueOf(Utils.getDate(operation.getInComeOperation().getDate())));
        }
        //tvDateOperation.setText(String.valueOf(Utils.getDate(operation.getInComeOperation().getDate())));
        tvDateOperation.setTextColor(convertView.getResources().getColor(R.color.colorAccent));

        return convertView;
    }

/*    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }*/
}