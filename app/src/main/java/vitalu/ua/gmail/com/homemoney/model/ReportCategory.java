package vitalu.ua.gmail.com.homemoney.model;

/**
 * Created by Виталий on 23.02.2016.
 */
public class ReportCategory {

    private long date;
    private double summ;
    private String categoryName;

    public long getDate() {
        return date;
    }

    public ReportCategory setDate(long date) {
        this.date = date;
        return this;
    }

    public double getSumm() {
        return summ;
    }

    public ReportCategory setSumm(double summ) {
        this.summ = summ;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ReportCategory setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
