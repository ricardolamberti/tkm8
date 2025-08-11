package pss.common.pki.appletSign;

/**
 * Former Swing-based signing applet placeholder.
 * This class now holds simple properties without any UI dependencies.
 */
public class SignForm {

    private String filename;
    private String outputFilename;
    private String outputProgreso;
    private String scriptFromApplet;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public String getOutputProgreso() {
        return outputProgreso;
    }

    public void setOutputProgreso(String outputProgreso) {
        this.outputProgreso = outputProgreso;
    }

    public String getScriptFromApplet() {
        return scriptFromApplet;
    }

    public void setScriptFromApplet(String scriptFromApplet) {
        this.scriptFromApplet = scriptFromApplet;
    }
}
