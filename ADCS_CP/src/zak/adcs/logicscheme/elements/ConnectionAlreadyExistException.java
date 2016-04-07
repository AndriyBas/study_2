package zak.adcs.logicscheme.elements;

public class ConnectionAlreadyExistException extends Exception {

    private static final String ENTER = "Вхід";
    private static final String EXIT = "Вихід";
    private static final String MESSAGE = " вже зайнятий іншим з’єднанням.";

    private boolean isEnter;

    public ConnectionAlreadyExistException(boolean isEnter) {
        this.isEnter = isEnter;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        if (isEnter) {
            builder.append(ENTER);
        } else {
            builder.append(EXIT);
        }
        builder.append(MESSAGE);
        return builder.toString();
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
