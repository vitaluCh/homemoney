package vitalu.ua.gmail.com.homemoney.model;

import android.content.Context;

import java.util.ArrayList;

import vitalu.ua.gmail.com.homemoney.R;

/**
 * Created by Виталий on 07.01.2016.
 */
public class StorageListImage {

    private ArrayList<Integer> mStorageImage;
    private static StorageListImage sStorageListImage;
    private Context context;

    public StorageListImage(Context context) {
        this.context = context;
        mStorageImage = new ArrayList<>();

        mStorageImage.add(R.drawable.storage1);
        mStorageImage.add(R.drawable.storage2);
        mStorageImage.add(R.drawable.storage3);
        mStorageImage.add(R.drawable.storage4);
        mStorageImage.add(R.drawable.storage5);
        mStorageImage.add(R.drawable.storage6);
        mStorageImage.add(R.drawable.storage7);
        mStorageImage.add(R.drawable.storage8);
        mStorageImage.add(R.drawable.storage9);
        mStorageImage.add(R.drawable.storage10);
        mStorageImage.add(R.drawable.storage11);
        mStorageImage.add(R.drawable.storage12);
        mStorageImage.add(R.drawable.storage13);
        mStorageImage.add(R.drawable.storage14);
        mStorageImage.add(R.drawable.storage15);
        mStorageImage.add(R.drawable.storage16);
        mStorageImage.add(R.drawable.storage17);
        mStorageImage.add(R.drawable.storage18);
        mStorageImage.add(R.drawable.storage19);
        mStorageImage.add(R.drawable.storage20);
        mStorageImage.add(R.drawable.storage21);
        mStorageImage.add(R.drawable.storage22);
        mStorageImage.add(R.drawable.storage23);
        mStorageImage.add(R.drawable.storage24);
        mStorageImage.add(R.drawable.storage25);
        mStorageImage.add(R.drawable.storage26);
        mStorageImage.add(R.drawable.storage27);
        mStorageImage.add(R.drawable.storage28);
        mStorageImage.add(R.drawable.storage29);
        mStorageImage.add(R.drawable.storage30);
        mStorageImage.add(R.drawable.storage31);
        mStorageImage.add(R.drawable.storage32);
        mStorageImage.add(R.drawable.storage33);
        mStorageImage.add(R.drawable.storage34);
        mStorageImage.add(R.drawable.storage35);
        mStorageImage.add(R.drawable.storage36);
        mStorageImage.add(R.drawable.storage37);
        mStorageImage.add(R.drawable.storage38);
        mStorageImage.add(R.drawable.storage39);
        mStorageImage.add(R.drawable.storage40);
        mStorageImage.add(R.drawable.storage41);
    }

    public static StorageListImage get(Context c){
        if(sStorageListImage == null){
            sStorageListImage = new StorageListImage(c.getApplicationContext());
        }
        return sStorageListImage;
    }

    public ArrayList<Integer> getStorageListImage(){
        return mStorageImage;
    }

    public Integer getStorageImage(int index){
        return mStorageImage.get(index);
    }
}
