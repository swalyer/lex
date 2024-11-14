import java.util.Scanner;

public class StateMachineCalculator {
    public enum State {
        START,
        OPERAND,
        OPERATOR,
        ERROR
    }

    public static double calculate(String expression) {
        State currentState = State.START;
        int operand = 0;
        char operator = ' ';
        int i = 0;

        while (i < expression.length()) {
            char currentChar = expression.charAt(i);
            switch (currentState) {
                case START:
                    if (Character.isDigit(currentChar)) {
                        operand = currentChar - '0';
                        currentState = State.OPERAND;
                    } else {
                        currentState = State.ERROR;
                    }
                    break;
                case OPERAND:
                    if (Character.isDigit(currentChar)) {
                        operand = operand * 10 + (currentChar - '0');
                    } else if (currentChar == '+' || currentChar == '-' ||
                            currentChar == '*' || currentChar == '/') {
                        operator = currentChar;
                        currentState = State.OPERATOR;
                    } else {
                        currentState = State.ERROR;
                    }
                    break;
                case OPERATOR:
                    if (Character.isDigit(currentChar)) {
                        int operand2 = currentChar - '0';
                        switch (operator) {
                            case '+':
                                operand += operand2;
                                break;
                            case '-':
                                operand -= operand2;
                                break;
                            case '*':
                                operand *= operand2;
                                break;
                            case '/':
                                if (operand2 != 0) {
                                    operand /= operand2;
                                } else {
                                    currentState = State.ERROR;
                                }
                                break;
                        }
                        currentState = State.OPERAND;
                    } else {
                        currentState = State.ERROR;
                    }
                    break;
                case ERROR:
                    break;
            }
            i++;
        }

        if (currentState != State.OPERAND) {
            currentState = State.ERROR;
        }

        if (currentState == State.ERROR) {
            System.out.println("Error: Invalid expression.");
            return Double.NaN;
        } else {
            return operand;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the arithmetic expression: ");
        String expression = scanner.nextLine();

        double result = calculate(expression);
        if (!Double.isNaN(result)) {
            System.out.println("Result: " + result);
        }
    }
}
/* Start — '1' —> Number — '+' —> Operator — '1' —> Number
|                       |           |                        |
|                       |           '--'-' —> Operator       |
|                       |           |                        |
'------— not '1' —------'           '------— not '1' —-------'*/