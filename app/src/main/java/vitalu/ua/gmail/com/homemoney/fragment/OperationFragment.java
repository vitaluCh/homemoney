package vitalu.ua.gmail.com.homemoney.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.model.database_model.Operation;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperationFragment extends BaseOperationFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Log.d(TAG, "------------onCreateView------------------------ " );
            View view = inflater.inflate(R.layout.fragment_operation, null);

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

            TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
            tvDate.setText(Utils.getDate(operation.getDate()));
            tvDate.setTextColor(getResources().getColor(R.color.colorAccent));

            TextView tvCategory = (TextView)view.findViewById(R.id.tvCategory);
            tvCategory.setText(operation.getSoyrce().getNameSours());

            TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
            tvDescription.setText(operation.getDescription());

            //ImageView imOperation = (ImageView)view.findViewById(R.id.imOperation);

            if(mIdOperation == INCOM_OPERATION){
                getActivity().setTitle(R.string.db_incom);
                tvOperationName.setTextColor(getResources().getColor(R.color.green));
                tvAmount.setTextColor(getResources().getColor(R.color.green));
                tvAmount.setText(String.valueOf(operation.getAmount()));
                tvCurrency.setTextColor(getResources().getColor(R.color.green));
            }else {
                getActivity().setTitle(R.string.db_outcom);
                tvOperationName.setTextColor(getResources().getColor(R.color.red));
                tvAmount.setTextColor(getResources().getColor(R.color.red));
                tvAmount.setText("- " + String.valueOf(operation.getAmount()));
                tvCurrency.setTextColor(getResources().getColor(R.color.red));
            }

            return view;
        }

    private void setTextColor(){

    }

   /* public static OperationFragment newInstance(int period, int position, int operation, long date) {//позиция в списке

        Bundle args = new Bundle();

        args.putInt(OperationActivity.EXTRA_PERIOD_OPERATION, period);
        args.putInt(OperationActivity.EXTRA_POSITION, position);
        args.putInt(OperationActivity.EXTRA_OPERATION, operation);
        args.putLong(OperationActivity.EXTRA_DAY, date);

        OperationFragment fragment = new OperationFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

   /* @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "---OperationFragment--------onResume " );

    }

   *//* @Override
    public void onRestart() {
        super.onRestart();

        Log.d(TAG, "------------onRestart ");
    }*//*

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "-----OperationFragment-------onStart " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        Log.d(TAG, "-----OperationFragment-------onDestroy ");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "-----OperationFragment-------onPause " );
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "-----OperationFragment-------onStop " );
    }
*/

}
