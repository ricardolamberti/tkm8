package pss.common.customList.config.customlist.importJSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Report {
    @JsonProperty("type")
    private String type;

    @JsonProperty("list_command")
    private List<Command> listCommand;

    // Getters and setters
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Command> getListCommand() {
        return listCommand;
    }

    public void setListCommand(List<Command> listCommand) {
        this.listCommand = listCommand;
    }
}
