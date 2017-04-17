package vitalu.ua.gmail.com.homemoney.model.content_main;

import android.content.Context;

import java.util.ArrayList;

import vitalu.ua.gmail.com.homemoney.R;

/**
 * Created by Виталий on 29.12.2015.
 */
public class ContentsMain {

    ArrayList<ContentMain> mMainContents;
    public ContentsMain(Context context) {
        mMainContents = new ArrayList<>();
        mMainContents.add(new ContentMain(R.drawable.coin, context.getString(R.string.main_stirage)));
        mMainContents.add(new ContentMain(R.drawable.coinadd, context.getString(R.string.main_incom)));
        mMainContents.add(new ContentMain(R.drawable.coindelete, context.getString(R.string.main_outcom)));
        mMainContents.add(new ContentMain(R.drawable.exchange, context.getString(R.string.main_transfer)));
        mMainContents.add(new ContentMain(R.drawable.debt, context.getString(R.string.main_debt)));
        /*mMainContents.add(new ContentMain(R.drawable.balance, context.getString(R.string.main_review)));*/
        mMainContents.add(new ContentMain(R.drawable.pattern, context.getString(R.string.main_pattern)));
        mMainContents.add(new ContentMain(R.drawable.chartsearch, context.getString(R.string.main_report)));
        /*mMainContents.add(new ContentMain(R.drawable.cash, context.getString(R.string.main_task)));*/
       /* mMainContents.add(new ContentMain(R.drawable.pattern, context.getString(R.string.main_pattern)));*/
        /*mMainContents.add(new ContentMain(R.drawable.limit, context.getString(R.string.main_limit)));*/
        mMainContents.add(new ContentMain(R.drawable.paperbox, context.getString(R.string.main_manage_categories)));
        mMainContents.add(new ContentMain(R.drawable.money, context.getString(R.string.main_currency)));

        mMainContents.add(new ContentMain(R.drawable.balance, context.getString(R.string.main_review)));
        mMainContents.add(new ContentMain(R.drawable.cash, context.getString(R.string.main_task)));
        mMainContents.add(new ContentMain(R.drawable.limit, context.getString(R.string.main_limit)));

   /*     mMainContents = new ArrayList<>();
        mMainContents.add(new ContentMain(R.drawable.coin, context.getString(R.string.main_stirage)));
        mMainContents.add(new ContentMain(R.drawable.coinadd, context.getString(R.string.main_incom)));
        mMainContents.add(new ContentMain(R.drawable.coindelete, context.getString(R.string.main_outcom)));
        mMainContents.add(new ContentMain(R.drawable.exchange, context.getString(R.string.main_transfer)));
        mMainContents.add(new ContentMain(R.drawable.debt, context.getString(R.string.main_debt)));
        mMainContents.add(new ContentMain(R.drawable.balance, context.getString(R.string.main_review)));
        mMainContents.add(new ContentMain(R.drawable.chartsearch, context.getString(R.string.main_report)));
        mMainContents.add(new ContentMain(R.drawable.cash, context.getString(R.string.main_task)));
        mMainContents.add(new ContentMain(R.drawable.pattern, context.getString(R.string.main_pattern)));
        mMainContents.add(new ContentMain(R.drawable.limit, context.getString(R.string.main_limit)));
        mMainContents.add(new ContentMain(R.drawable.paperbox, context.getString(R.string.main_manage_categories)));
        mMainContents.add(new ContentMain(R.drawable.money, context.getString(R.string.main_currency)));*/
    }

    public ArrayList<ContentMain> getContentsMain(){
        return mMainContents;
    }
}
