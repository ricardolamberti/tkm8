package pss.common.pki.appletSign;

/**
 * Lightweight error dialog placeholder without UI dependencies.
 */
public class ErrorDialog {

    private String message;

    public ErrorDialog(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
