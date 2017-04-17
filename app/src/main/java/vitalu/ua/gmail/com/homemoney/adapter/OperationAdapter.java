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
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 04.02.2016.
 */
public class OperationAdapter extends ArrayAdapter<Operation> {

    public static final String TAG = "myLogMainActivity";

    private static final int INCOM = 1;
    private Context context;

    public OperationAdapter(Context context, List<Operation> operations) {
        super(context, 0, operations);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Если мы не получили представление, заполняем его

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_list_operation, parent, false);
        }

        Operation operation = getItem(position);
        ImageView imOperationStorage =
                (ImageView)convertView.findViewById(R.id.imOperationStorage);
        imOperationStorage.setImageResource(operation.getStorage().getImageStorage());

        TextView tvOperationName =
                (TextView)convertView.findViewById(R.id.tvOperationName);
        tvOperationName.setText(operation.getNameOperation());

        TextView tvSourceName =
                (TextView)convertView.findViewById(R.id.tvSourceName);
        tvSourceName.setText(String.valueOf(operation.getSoyrce().getNameSours()));

        TextView tvOperationAmount =
                (TextView)convertView.findViewById(R.id.tvOperationAmount);
      //  tvOperationAmount.setText(String.valueOf(operation.getAmount()));

        TextView tvOperationCurrency =
                (TextView)convertView.findViewById(R.id.tvOperationCurrency);
        tvOperationCurrency.setText(operation.getStorage().getCurency().getShortName());

        TextView tvDateOperation =
                (TextView)convertView.findViewById(R.id.tvDateOperation);
        if(operation.getDate()>0){
            tvDateOperation.setText(String.valueOf(Utils.getDate(operation.getDate())));
        }

        if(operation.getSoyrce().getType().getId() == INCOM){
            tvSourceName.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationAmount.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationCurrency.setTextColor(convertView.getResources().getColor(R.color.green));
            tvOperationAmount.setText(String.valueOf(operation.getAmount()));
        }else {
            tvSourceName.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationAmount.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationCurrency.setTextColor(convertView.getResources().getColor(R.color.red));
            tvOperationAmount.setText("- " + String.valueOf(operation.getAmount()));
        }
        tvDateOperation.setTextColor(convertView.getResources().getColor(R.color.colorAccent));

        return convertView;
    }

/*    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }*/
}