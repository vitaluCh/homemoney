package vitalu.ua.gmail.com.homemoney.model.factory_method.period;

/**
 * Created by Виталий on 12.02.2016.
 */
public class OperationsSelector {

    private final int INCOM_OPERATION = 1;
    private final int OUTCOM_OPERATION = 2;
    private final int TRANSFER_OPERATION = 3;

    public OperationsOfPeriod getOperation(int IdOperation){

        OperationsOfPeriod eachOfPPeriods = null;
        switch (IdOperation){
            case INCOM_OPERATION:
            case OUTCOM_OPERATION:
                eachOfPPeriods = new OperationInAndOutCome();
                break;
            case TRANSFER_OPERATION:
                eachOfPPeriods = new OperationTransfer();
                break;
        }

        return eachOfPPeriods;
    }

}
