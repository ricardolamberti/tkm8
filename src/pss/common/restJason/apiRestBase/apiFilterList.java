package pss.common.restJason.apiRestBase;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement (name="apiListFilter")
public class apiFilterList implements Serializable {
    private Map<String,String> userData;

    
    public Map<String,String> getUserData() {
        return userData;
    }
    public void setUserData(Map<String,String> userData) {
        this.userData = userData;
    }

	
}