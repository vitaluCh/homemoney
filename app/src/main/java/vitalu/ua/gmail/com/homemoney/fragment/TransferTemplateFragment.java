package vitalu.ua.gmail.com.homemoney.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.TransferOperation;

/**
 * Created by Виталий on 17.02.2016.
 */
public class TransferTemplateFragment extends BaseTemplateFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.template_transfer_fragment, null);
        getActivity().setTitle(R.string.template_transfer);
/*
        Log.d(TAG, String.valueOf(mOperations.size()));

        for(int i = 0; i<mOperations.size();i++){
            Log.d(TAG, mOperations.get(i).toString());
        }*/

        TransferOperation transfer = (TransferOperation)mOperations.get(mPosition);

        ImageView imStorageFrom = (ImageView)view.findViewById(R.id.imStorageFrom);
        imStorageFrom.setImageResource(transfer.getOutComeOperation().getStorage().getImageStorage());

        TextView tvStorageNameFrom = (TextView)view.findViewById(R.id.tvStorageNameFrom);
        tvStorageNameFrom.setText(transfer.getOutComeOperation().getStorage().getNameStorage());

        TextView tvStorageAmountNow = (TextView)view.findViewById(R.id.tvStorageAmountNow);
        tvStorageAmountNow.setText(String.valueOf(transfer.getOutComeOperation().getStorage().getAmountStorage()));

        TextView tvStorageCource = (TextView)view.findViewById(R.id.tvStorageCource);
        tvStorageCource.setText(String.valueOf(transfer.getOutComeOperation().getStorage().getCurency().getShortName()));

        TextView tvStorageAmountFrom = (TextView)view.findViewById(R.id.tvStorageAmountFrom);
        tvStorageAmountFrom.setText("- " + String.valueOf(transfer.getOutComeOperation().getAmount()));

        TextView tvStorageCourceFrom = (TextView)view.findViewById(R.id.tvStorageCourceFrom);
        tvStorageCourceFrom.setText(transfer.getOutComeOperation().getStorage().getCurency().getShortName());

        /*/////////////////////////////////////////////////////////////*/

        ImageView imStorageTo = (ImageView)view.findViewById(R.id.imStorageTo);
        imStorageTo.setImageResource(transfer.getInComeOperation().getStorage().getImageStorage());

        TextView tvStorageNameTo = (TextView)view.findViewById(R.id.tvStorageNameTo);
        tvStorageNameTo.setText(transfer.getInComeOperation().getStorage().getNameStorage());

        TextView tvStorageAmountTo = (TextView)view.findViewById(R.id.tvStorageAmountTo);
        tvStorageAmountTo.setText(String.valueOf(transfer.getInComeOperation().getStorage().getAmountStorage()));

        TextView tvStorageCourceTo = (TextView)view.findViewById(R.id.tvStorageCourceTo);
        tvStorageCourceTo.setText(String.valueOf(transfer.getInComeOperation().getStorage().getCurency().getShortName()));

        TextView tvStorageAmountFromTo = (TextView)view.findViewById(R.id.tvStorageAmountFromTo);
        tvStorageAmountFromTo.setText("+ " + String.valueOf(transfer.getInComeOperation().getAmount()));

        TextView tvStorageCourceFromTo = (TextView)view.findViewById(R.id.tvStorageCourceFromTo);
        tvStorageCourceFromTo.setText(transfer.getInComeOperation().getStorage().getCurency().getShortName());


        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
        tvDescription.setText(transfer.getInComeOperation().getDescription());

        return view;
    }
}
