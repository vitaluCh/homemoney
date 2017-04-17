package vitalu.ua.gmail.com.homemoney.model.database_model;

/**
 * Created by Виталий on 28.12.2015.
 */
public class ModelTask {

    int mId;
    String mTitle;
    long mDateTask;
    int mPriority;
    int mStatus;
    long mTime;

    public ModelTask() {
    }

    public ModelTask(int mId, String mTitle, long mDateTask,
                     int mPriority, int mStatus, long mTime) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDateTask = mDateTask;
        this.mPriority = mPriority;
        this.mStatus = mStatus;
        this.mTime = mTime;
    }

    public int getId() {
        return mId;
    }

    public ModelTask setId(int mId) {
        this.mId = mId;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public ModelTask setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public long getDateTask() {
        return mDateTask;
    }

    public ModelTask setDateTask(long mDateTask) {
        this.mDateTask = mDateTask;
        return this;
    }

    public int getPriority() {
        return mPriority;
    }

    public ModelTask setPriority(int mPriority) {
        this.mPriority = mPriority;
        return this;
    }

    public int getStatus() {
        return mStatus;
    }

    public ModelTask setStatus(int mStatus) {
        this.mStatus = mStatus;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public ModelTask setTime(long mTime) {
        this.mTime = mTime;
        return this;
    }
}
