package vitalu.ua.gmail.com.homemoney.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;

/**
 * Created by Виталий on 17.02.2016.
 */
public class InOutTemplateFragment extends BaseTemplateFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d(TAG, "------------onCreateView------------------------ ");
        View view = inflater.inflate(R.layout.fragment_in_out_template, null);

        Operation operation = (Operation)mOperations.get(mPosition);

        ImageView imStorage = (ImageView)view.findViewById(R.id.imStorage);
        imStorage.setImageResource(operation.getStorage().getImageStorage());

        TextView tvStorageName = (TextView)view.findViewById(R.id.tvStorageName);
        tvStorageName.setText(operation.getStorage().getNameStorage());

        TextView tvStorageAmount = (TextView)view.findViewById(R.id.tvStorageAmount);
        tvStorageAmount.setText(String.valueOf(operation.getStorage().getAmountStorage()));

        TextView tvStorageCource = (TextView)view.findViewById(R.id.tvStorageCource);
        tvStorageCource.setText(String.valueOf(operation.getStorage().getCurency().getShortName()));

        TextView tvOperationName = (TextView)view.findViewById(R.id.tvOperationName);
        tvOperationName.setText(operation.getNameOperation());

        TextView tvAmount = (TextView)view.findViewById(R.id.tvAmount);


        TextView tvCurrency = (TextView)view.findViewById(R.id.tvCurrency);
        tvCurrency.setText(operation.getStorage().getCurency().getShortName());

  /*      TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvDate.setText(Utils.getDate(operation.getDate()));
        tvDate.setTextColor(getResources().getColor(R.color.colorAccent));*/

        TextView tvCategory = (TextView)view.findViewById(R.id.tvCategory);
        tvCategory.setText(operation.getSoyrce().getNameSours());

        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
        tvDescription.setText(operation.getDescription());

        //ImageView imOperation = (ImageView)view.findViewById(R.id.imOperation);

        if(mIdOperation == INCOM_OPERATION){
            getActivity().setTitle(R.string.template_incom);
            tvOperationName.setTextColor(getResources().getColor(R.color.green));
            tvAmount.setTextColor(getResources().getColor(R.color.green));
            tvAmount.setText(String.valueOf(operation.getAmount()));
            tvCurrency.setTextColor(getResources().getColor(R.color.green));
        }else {
            getActivity().setTitle(R.string.template_outcom);
            tvOperationName.setTextColor(getResources().getColor(R.color.red));
            tvAmount.setTextColor(getResources().getColor(R.color.red));
            tvAmount.setText("- " + String.valueOf(operation.getAmount()));
            tvCurrency.setTextColor(getResources().getColor(R.color.red));
        }

        return view;
    }
}
