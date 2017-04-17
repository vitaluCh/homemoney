package vitalu.ua.gmail.com.homemoney.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.activity.CurrencyListActivity;
import vitalu.ua.gmail.com.homemoney.activity.DebtListActivity;
import vitalu.ua.gmail.com.homemoney.activity.MainActivity;
import vitalu.ua.gmail.com.homemoney.activity.OpeationsListActivity;
import vitalu.ua.gmail.com.homemoney.activity.ReviewActivity;
import vitalu.ua.gmail.com.homemoney.activity.SourceListActivity;
import vitalu.ua.gmail.com.homemoney.activity.StorageListActivity;
import vitalu.ua.gmail.com.homemoney.activity.TamplateTabActivity;
import vitalu.ua.gmail.com.homemoney.activity.report.ReportActivity;
import vitalu.ua.gmail.com.homemoney.model.content_main.ContentMain;
import vitalu.ua.gmail.com.homemoney.model.content_main.ContentsMain;


public class ContentMainFragment extends Fragment implements AdapterView.OnItemClickListener{


    //GridView gvMain;
    private MainContentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new MainContentAdapter(new ContentsMain(getActivity())
                .getContentsMain());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_grid_container, container, false);
        GridView gvMain = (GridView) v.findViewById(R.id.gridLayout);
        gvMain.setAdapter(adapter);
        gvMain.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        switch (position){
            case 0:
                Log.d(MainActivity.TAG, "мои щета");
                intent = new Intent(getActivity(), StorageListActivity.class);
                startActivity(intent);

                break;
            case 1:
                Log.d(MainActivity.TAG, "Доходы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.INCOM_OPERATION);
                startActivity(intent);
                break;
            case 2:
                Log.d(MainActivity.TAG, "Расходы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.OUTCOM_OPERATION);
                startActivity(intent);
                break;
            case 3:
                Log.d(MainActivity.TAG, "Переводы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 4:
                Log.d(MainActivity.TAG, "Управление долгами");
                intent = new Intent(getActivity(), DebtListActivity.class);
                //intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 9:
                Log.d(MainActivity.TAG, "Анализ цен");
                intent = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent);
                break;
            case 6:
                Log.d(MainActivity.TAG, "Отчеты");
                intent = new Intent(getActivity(), ReportActivity.class);

                startActivity(intent);
                break;
            case 10:
                Log.d(MainActivity.TAG, "Планирование");
                break;
            case 5:
                Log.d(MainActivity.TAG, "Шаблоны");
                intent = new Intent(getActivity(), TamplateTabActivity.class);
                //intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 11:
                Log.d(MainActivity.TAG, "Лимит");
                break;
            case 7:
                intent = new Intent(getActivity(), SourceListActivity.class);
                startActivity(intent);
                Log.d(MainActivity.TAG, "Управление категориями");
                break;
            case 8:
                intent = new Intent(getActivity(), CurrencyListActivity.class);
                startActivity(intent);
                Log.d(MainActivity.TAG, "Валюты");
                break;
        }
   /*     switch (position){
            case 0:
                Log.d(MainActivity.TAG, "мои щета");
                intent = new Intent(getActivity(), StorageListActivity.class);
                startActivity(intent);

                break;
            case 1:
                Log.d(MainActivity.TAG, "Доходы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.INCOM_OPERATION);
                startActivity(intent);
                break;
            case 2:
                Log.d(MainActivity.TAG, "Расходы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.OUTCOM_OPERATION);
                startActivity(intent);
                break;
            case 3:
                Log.d(MainActivity.TAG, "Переводы");
                intent = new Intent(getActivity(), OpeationsListActivity.class);
                intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 4:
                Log.d(MainActivity.TAG, "Управление долгами");
                intent = new Intent(getActivity(), DebtListActivity.class);
                //intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 5:
                Log.d(MainActivity.TAG, "Анализ цен");
                intent = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent);
                break;
            case 6:
                Log.d(MainActivity.TAG, "Отчеты");
                intent = new Intent(getActivity(), ReportActivity.class);

                startActivity(intent);
                break;
            case 7:
                Log.d(MainActivity.TAG, "Планирование");
                break;
            case 8:
                Log.d(MainActivity.TAG, "Шаблоны");
                intent = new Intent(getActivity(), TamplateTabActivity.class);
                //intent.putExtra(OpeationsListActivity.EXTRA_OPERATION, OpeationsListActivity.TRANSFER_OPERATION);
                startActivity(intent);
                break;
            case 9:
                Log.d(MainActivity.TAG, "Лимит");
                break;
            case 10:
                intent = new Intent(getActivity(), SourceListActivity.class);
                startActivity(intent);
                Log.d(MainActivity.TAG, "Управление категориями");
                break;
            case 11:
                intent = new Intent(getActivity(), CurrencyListActivity.class);
                startActivity(intent);
                Log.d(MainActivity.TAG, "Валюты");
                break;
        }*/
    }

    /**
     * Класс адаптер содержимого главной страницы
     */
    private class MainContentAdapter extends ArrayAdapter<ContentMain> {

        public MainContentAdapter(ArrayList<ContentMain> mainContent) {
            super(getActivity(), 0, mainContent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// Если мы не получили представление, заполняем его
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_content_main, null);
            }
// Настройка представления для объекта ContentMain
            ContentMain mainContent = getItem(position);

            TextView button = (TextView) convertView.findViewById(R.id.tv_main_content);
            button.setText(mainContent.getNameAction());

            ImageView imageView = (ImageView)convertView.findViewById(R.id.image_item_content_main);
            imageView.setImageResource(mainContent.getImage());

            return convertView;
        }

    }

}
