package vitalu.ua.gmail.com.homemoney.model.factory_method.all_period;

/**
 * Created by Виталий on 12.02.2016.
 */
public class OperationEachOfPeriodSelector {


    private final int INCOM_OPERATION = 1;
    private final int OUTCOM_OPERATION = 2;
    private final int TRANSFER_OPERATION = 3;

    public OperationsOfEachOfPeriods getOperations(int idOperatio){

        OperationsOfEachOfPeriods eachOfPPeriods = null;
        switch (idOperatio){
            case INCOM_OPERATION:
            case OUTCOM_OPERATION:
                eachOfPPeriods = new OperationsInOutOfEachPeriod();
                break;
            case TRANSFER_OPERATION:
                eachOfPPeriods = new TransferOperationsOfEachOfPeriod();
                break;
        }

        return eachOfPPeriods;
    }
}
