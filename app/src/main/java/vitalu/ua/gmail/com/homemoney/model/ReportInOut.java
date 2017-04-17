package vitalu.ua.gmail.com.homemoney.model;

/**
 * Created by Виталий on 22.02.2016.
 */
public class ReportInOut {

    long date;
    double sumInCome;
    double sumOutCome;

    public long getDate() {
        return date;
    }

    public ReportInOut setDate(long date) {
        this.date = date;
        return this;
    }

    public double getSumInCome() {
        return sumInCome;
    }

    public ReportInOut setSumInCome(double sumInCome) {
        this.sumInCome = sumInCome;
        return this;
    }

    public double getSumOutCome() {
        return sumOutCome;
    }

    public ReportInOut setSumOutCome(double sumOutCome) {
        this.sumOutCome = sumOutCome;
        return this;
    }
}
