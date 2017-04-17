package vitalu.ua.gmail.com.homemoney.model.content_main;

/**
 * Created by Виталий on 29.12.2015.
 */
public class ContentMain {

    int mImage;
    String mNameAction;

    public ContentMain(int mImage, String mNameAction) {
        this.mImage = mImage;
        this.mNameAction = mNameAction;
    }

    public int getImage() {
        return mImage;
    }

    public ContentMain setImage(int mImage) {
        this.mImage = mImage;
        return this;
    }

    public String getNameAction() {
        return mNameAction;
    }

    public ContentMain setNameAction(String mNameAction) {
        this.mNameAction = mNameAction;
        return this;
    }
}
