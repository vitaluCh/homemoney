package vitalu.ua.gmail.com.homemoney.calc.exceptions;

/**
 * Created by Виталий on 19.01.2016.
 */
public class DivisionByZeroException extends ArithmeticException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DivisionByZeroException() {
    }

    public DivisionByZeroException(String message) {
        super(message);
    }


}
