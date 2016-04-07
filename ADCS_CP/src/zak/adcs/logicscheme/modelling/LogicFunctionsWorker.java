package zak.adcs.logicscheme.modelling;

public class LogicFunctionsWorker {

    public enum LogicFunction {AND, OR, NAND, NOR, XOR}

    private static final char ONE = '1';
    private static final char ZERO = '0';

    public static char getValue(LogicFunction function, String inputSet) {
        switch (function) {
            case AND: {
                return and(inputSet);
            }
            case OR: {
                return or(inputSet);
            }
            case NAND: {
                return nand(inputSet);
            }
            case NOR: {
                return nor(inputSet);
            }
            case XOR: {
                return xor(inputSet);
            }
            default: {
                return ZERO;
            }
        }
    }

    private static char and(String inputSet) {
        for (int i = 0; i < inputSet.length(); i++) {
            if (inputSet.charAt(i) == ZERO) {
                return ZERO;
            }
        }
        return ONE;
    }

    private static char or(String inputSet) {
        for (int i = 0; i < inputSet.length(); i++) {
            if (inputSet.charAt(i) == ONE) {
                return ONE;
            }
        }
        return ZERO;
    }

    private static char nand(String inputSet) {
        if (and(inputSet) == ONE) {
            return ZERO;
        }
        return ONE;
    }

    private static char nor(String inputSet) {
        if (or(inputSet) == ONE) {
            return ZERO;
        }
        return ONE;
    }

    private static char xor(String inputSet) {
        char result = xor2(inputSet.charAt(0), inputSet.charAt(1));
        for (int i = 2; i < inputSet.length(); i++) {
            result = xor2(result, inputSet.charAt(i));
        }
        return result;
    }

    private static char xor2(char ch1, char ch2) {
        if (ch1 == ch2) {
            return ZERO;
        }
        return ONE;
    }

}
