package vitalu.ua.gmail.com.homemoney.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vitalu.ua.gmail.com.homemoney.fragment.InComListTemplateFragment;
import vitalu.ua.gmail.com.homemoney.fragment.OutComeListTemplateFragment;
import vitalu.ua.gmail.com.homemoney.fragment.TransferListTemplateFragment;

/**
 * Created by Виталий on 01.01.2016.
 */
public class TabsPagerFragmentAdapter extends FragmentStatePagerAdapter{

    int counTitle;

    public TabsPagerFragmentAdapter(FragmentManager fm, int counTitle) {
        super(fm);
        this.counTitle = counTitle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position){
            case 0:
               fragment = new InComListTemplateFragment();
                break;
            case 1:
                fragment = new OutComeListTemplateFragment();
                break;
            case 2:
                fragment = new TransferListTemplateFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return counTitle;
    }
}
