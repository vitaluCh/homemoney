package vitalu.ua.gmail.com.homemoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.Currency;

/**
 * Created by Виталий on 21.01.2016.
 */
public class CurrencyAdapter  extends ArrayAdapter<Currency> {

    Context context;

    public CurrencyAdapter(Context context, List<Currency> currencies) {
        super(context, 0, currencies);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater =
                    (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_currency, parent, false);
        }

        Currency c = getItem(position);

        TextView fullName = (TextView)convertView.findViewById(R.id.textView2);
        TextView shortName = (TextView)convertView.findViewById(R.id.textView3);

        fullName.setText(c.getFullName());
        shortName.setText(c.getShortName());

        return convertView;
    }
}
