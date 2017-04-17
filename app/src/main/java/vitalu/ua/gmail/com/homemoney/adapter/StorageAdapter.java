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
import vitalu.ua.gmail.com.homemoney.model.database_model.Storage;

/**
 * Created by Виталий on 15.01.2016.
 */
public class StorageAdapter extends ArrayAdapter<Storage> {

    //List<Storage> listStorage;
    private Context context;
    public StorageAdapter(Context context, List<Storage> storages) {
        super(context, 0, storages);
        this.context = context;
        //listStorage = storages;
    }
/*

    public StorageAdapter(Context context, int idResours, List<Storage> storages) {
        super(context, idResours,0, storages);
        this.context = context;
        //listStorage = storages;
    }
*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Если мы не получили представление, заполняем его

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_fragment_storage, parent, false);
        }

        Storage storage = getItem(position);
        ImageView imStorage =
                (ImageView)convertView.findViewById(R.id.imOneStorage);
        imStorage.setImageResource(storage.getImageStorage());
        TextView nameTextView =
                (TextView)convertView.findViewById(R.id.tvStorageName);
        nameTextView.setText(storage.getNameStorage());
        TextView amountTextView =
                (TextView)convertView.findViewById(R.id.tvStorageAmount);
        amountTextView.setText(String.valueOf(storage.getAmountStorage()));
        TextView currencyTextView =
                (TextView)convertView.findViewById(R.id.tvStorageCource);
        currencyTextView.setText(storage.getCurency().getShortName());

        return convertView;
    }

/*    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }*/
}