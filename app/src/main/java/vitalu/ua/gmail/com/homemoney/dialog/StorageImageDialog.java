package vitalu.ua.gmail.com.homemoney.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.StorageListImage;

/**
 * Created by Виталий on 07.01.2016.
 */
public class StorageImageDialog extends DialogFragment implements AdapterView.OnItemClickListener{

    public static final String EXTRA_IMAGE_STORAGE = "vitalu.ua.gmail.com.homemoney.dialog.image_stprage";

    //private GridView gridView;
   // private StorageImageAdapter adapter;
    private int mImage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        StorageImageAdapter adapter = new StorageImageAdapter();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choise_storage);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_grid_container, null);
        GridView gridView = (GridView)view.findViewById(R.id.gridLayout);
        gridView.setNumColumns(3);
        gridView.setColumnWidth(100);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_IMAGE_STORAGE, mImage);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mImage = StorageListImage.get(getActivity()).getStorageImage(position);
        sendResult(Activity.RESULT_OK);
        dismiss();
    }

    class StorageImageAdapter extends ArrayAdapter<Integer>{

        ArrayList<Integer> listImage;

        public StorageImageAdapter() {
            super(getActivity(), 0, StorageListImage.get(getActivity()).getStorageListImage());
            listImage = StorageListImage.get(getActivity()).getStorageListImage();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Если мы не получили представление, заполняем его
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_image_storage, null);
            }
// Настройка представления
            int item = listImage.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imItemStorage);
            imageView.setImageResource(item);

            return convertView;
        }
    }
}
