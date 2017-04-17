package vitalu.ua.gmail.com.homemoney.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import vitalu.ua.gmail.com.homemoney.activity.TemplateActivity;
import vitalu.ua.gmail.com.homemoney.database.database_query.PatternQuery;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsOfPeriod;
import vitalu.ua.gmail.com.homemoney.model.factory_method.period.OperationsSelector;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTemplateFragment extends Fragment {

    public static final int INCOM_OPERATION = 1;
    private static final int TRANSFER_OPERATION = 3;
    private static final int OUTCOM_OPERATION = 2;

    int mPosition;//позиция в списке
    int mIdOperation;//операция доход, расход

    OperationsSelector selector;
    OperationsOfPeriod mOperations;


    public BaseTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(TemplateActivity.EXTRA_POSITION);
        mIdOperation = getArguments().getInt(TemplateActivity.EXTRA_OPERATION);

        selector = new OperationsSelector();
        mOperations = selector.getOperation(mIdOperation);

        mOperations.addAll(PatternQuery.getTemplate(mIdOperation));

    }


/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }*/

/*    @Override
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

  *//*      TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvDate.setText(Utils.getDate(operation.getDate()));
        tvDate.setTextColor(getResources().getColor(R.color.colorAccent));*//*

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
    }*/


    public static BaseTemplateFragment newInstance(int position, int operation) {//позиция в списке

        BaseTemplateFragment fragment = null;

        Bundle args = new Bundle();
        args.putInt(TemplateActivity.EXTRA_POSITION, position);
        args.putInt(TemplateActivity.EXTRA_OPERATION, operation);
        //BaseTemplateFragment fragment = new BaseTemplateFragment();

        //fragment.setArguments(args);

        if(operation == TRANSFER_OPERATION){
            fragment = new TransferTemplateFragment();
        }else if(operation == INCOM_OPERATION || operation == OUTCOM_OPERATION){
            fragment = new InOutTemplateFragment();
        }
        //TransferOperationFragment fragment = new TransferOperationFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
