package vitalu.ua.gmail.com.homemoney.calc.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.EnumMap;

import vitalu.ua.gmail.com.homemoney.R;
import vitalu.ua.gmail.com.homemoney.calc.CalcOperations;
import vitalu.ua.gmail.com.homemoney.calc.enums.ActionType;
import vitalu.ua.gmail.com.homemoney.calc.enums.OperationType;
import vitalu.ua.gmail.com.homemoney.calc.enums.Symbol;
import vitalu.ua.gmail.com.homemoney.calc.exceptions.DivisionByZeroException;
import vitalu.ua.gmail.com.homemoney.utils.Utils;

/**
 * Created by Виталий on 06.01.2016.
 */
public class DialogCalculator extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "myLogMainActivity";

    public static final String EXTRA_CALC_DATA = "vitalu.ua.gmail.com.homemoney.calc.dialog.result_calc";

    Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8,
            mButton9, mButton0, mButtonC, mButtonBack, mButtonAdd, mButtonMult, mButtonDiv,
            mButtonSub, mButtonResul, mButtonDot;

    EditText mTextCalcul;

    private View view;

    private OperationType operType;

    private EnumMap<Symbol, Object> commands = new EnumMap<Symbol, Object>(
            Symbol.class); // хранит все введенные данные пользователя

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_calculator, null);

        setUI();

        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "Отправка результата");
                sendResult(Activity.RESULT_OK);
            }
        });

        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
/*        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });*/

        return alertDialog;
    }

    private void setUI(){

        mTextCalcul = (EditText)view.findViewById(R.id.editTextCalcul);

        mButton1 = (Button)view.findViewById(R.id.button1);
        mButton2 = (Button)view.findViewById(R.id.button2);
        mButton3 = (Button)view.findViewById(R.id.button3);
        mButton4 = (Button)view.findViewById(R.id.button4);
        mButton5 = (Button)view.findViewById(R.id.button5);
        mButton6 = (Button)view.findViewById(R.id.button6);
        mButton7 = (Button)view.findViewById(R.id.button7);
        mButton8 = (Button)view.findViewById(R.id.button8);
        mButton9 = (Button)view.findViewById(R.id.button9);
        mButton0 = (Button)view.findViewById(R.id.button0);

        mButtonC = (Button)view.findViewById(R.id.buttonC);
        mButtonBack = (Button)view.findViewById(R.id.buttonBack);

        mButtonAdd = (Button)view.findViewById(R.id.buttonAdd);
        mButtonMult = (Button)view.findViewById(R.id.buttonMult);
        mButtonDiv = (Button)view.findViewById(R.id.buttonDiv);
        mButtonSub = (Button)view.findViewById(R.id.buttonSub);

        mButtonResul = (Button)view.findViewById(R.id.buttonResult);

        mButtonDot = (Button)view.findViewById(R.id.buttonDot);

        ////////////////////////////////////////
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);

        mButtonC.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);

        mButtonAdd.setOnClickListener(this);
        mButtonMult.setOnClickListener(this);
        mButtonDiv.setOnClickListener(this);
        mButtonSub.setOnClickListener(this);

        mButtonResul.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);
        /////////////////////////////

        mTextCalcul.setText("0");

        // к каждой кнопке добавить тип операции
        mButtonAdd.setTag(OperationType.ADD);
        mButtonDiv.setTag(OperationType.DIVIDE);
        mButtonMult.setTag(OperationType.MULTIPLY);
        mButtonSub.setTag(OperationType.SUBTRACT);

    }

    private void showToastMessage(int messageId) {
        Toast toastMessage = Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG);
        toastMessage.setGravity(Gravity.TOP, 0, 100);
        toastMessage.show();
    }

    private ActionType lastAction;

    @Override
    public void onClick(View v) {

        Button btn = (Button)v;

        int id = v.getId();

        switch (id){
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                // все остальные кнопки (цифры)

                if (mTextCalcul.getText().toString().equals("0") ||                      // если вводится второе
                        (commands.containsKey(Symbol.FIRST_DIGIT) && getDouble(mTextCalcul// число - то нужно
                        .getText()) == getDouble(commands.get(Symbol.FIRST_DIGIT))) ||   // сбросить текстовое
                        (lastAction == ActionType.CALCULATION)) {                             // поле

                    mTextCalcul.setText(btn.getText().toString());
                } else {
                    mTextCalcul.setText(mTextCalcul.getText()
                            + btn.getText().toString());
                }

                lastAction = ActionType.DIGIT;
                break;
            case R.id.buttonAdd:
            case R.id.buttonSub:
            case R.id.buttonDiv:
            case R.id.buttonMult:
                // кнопка - одна из операций

                operType = (OperationType) btn.getTag();// получить тип операции из кнопки

                if (lastAction == ActionType.OPERAION) {
                    commands.put(Symbol.OPERATION, operType);
                    return;
                }

                if (!commands.containsKey(Symbol.OPERATION)) {

                    if (!commands.containsKey(Symbol.FIRST_DIGIT)) {
                        commands.put(Symbol.FIRST_DIGIT, mTextCalcul.getText());
                    }

                    commands.put(Symbol.OPERATION, operType);
                } else if (!commands.containsKey(Symbol.SECOND_DIGIT)) {
                    commands.put(Symbol.SECOND_DIGIT, mTextCalcul.getText());
                    doCalc();
                    commands.put(Symbol.OPERATION, operType);
                    commands.remove(Symbol.SECOND_DIGIT);
                }

                lastAction = ActionType.OPERAION;
                break;
            case R.id.buttonDot:
                // кнопка для ввода десятичного числа

                if (commands.containsKey(Symbol.FIRST_DIGIT)
                        && getDouble(mTextCalcul.getText().toString()) == getDouble(commands
                        .get(Symbol.FIRST_DIGIT).toString())) {

                    mTextCalcul.setText("0" + btn.getText().toString());
                }

                if (!mTextCalcul.getText().toString().contains(".")) {
                    mTextCalcul.setText(mTextCalcul.getText() + ".");
                }

                lastAction = ActionType.COMMA;

                break;
            case R.id.buttonC:
                // кнопка очистить
                mTextCalcul.setText("0");
                commands.clear();// стереть все введенные команды
                lastAction = ActionType.CLEAR;
                break;
            case R.id.buttonBack:
                // кнопка удаления последнего символа
                mTextCalcul.setText(mTextCalcul.getText().delete(
                        mTextCalcul.getText().length() - 1,
                        mTextCalcul.getText().length()));

                if (mTextCalcul.getText().toString().trim().length() == 0) {
                    mTextCalcul.setText("0");
                }

                lastAction = ActionType.DELETE;

                break;
            case R.id.buttonResult:
                // кнопка посчитать результат

                if (lastAction == ActionType.CALCULATION) return;

                if (commands.containsKey(Symbol.FIRST_DIGIT)        // если ввели число, нажали
                        && commands.containsKey(Symbol.OPERATION)) {// знак операции и ввели
                                                                    // второе число
                    commands.put(Symbol.SECOND_DIGIT, mTextCalcul.getText());

                    doCalc(); // посчитать

                    commands.clear();
                }

                lastAction = ActionType.CALCULATION;

                break;
        }
    }

    private double getDouble(Object value) {
        double result = 0;
        try {
            result = Double.valueOf(value.toString().replace(',', '.'))
                    .doubleValue();// замена запятой на точку
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    private void doCalc() {

        OperationType operTypeTmp = (OperationType) commands.get(Symbol.OPERATION);

        double result = 0;

        try {
            result = calc(operTypeTmp,
                    getDouble(commands.get(Symbol.FIRST_DIGIT)),
                    getDouble(commands.get(Symbol.SECOND_DIGIT)));

        } catch (DivisionByZeroException e) {
            showToastMessage(R.string.division_zero);
            return;
        }

        if (result % 1 == 0) {
            mTextCalcul.setText(String.valueOf((int) result));// отсекать нули после запятой

        } else {
            mTextCalcul.setText(String.valueOf(result));// отсекать нули после запятой
        }

        commands.put(Symbol.FIRST_DIGIT, result);// записать полученный результат в первое число,
                                                // чтобы можно было сразу выполнять новые операции

    }

    private Double calc(OperationType operType, double a, double b) {
        switch (operType) {
            case ADD: {
                return CalcOperations.add(a, b);
            }
            case DIVIDE: {
                return CalcOperations.divide(a, b);
            }
            case MULTIPLY: {
                return CalcOperations.multiply(a, b);
            }
            case SUBTRACT: {
                return CalcOperations.subtract(a, b);
            }
        }

        return null;
    }

    private void sendResult(int resultCode) {

        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_CALC_DATA, String.valueOf(Utils.getTwoDouble(mTextCalcul.getText().toString())));
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}