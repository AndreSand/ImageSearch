package andres_sjsu.imagesearch.models;
import java.io.Serializable;
/**
 * Created by andres on 9/27/15.
 */
public class Settings implements Serializable {

    private static final long serialVersionUID = 9074924008331646396L;
    private String size = "small";
    private String color = "blue";
    private String type = "photo";
    private String site = "espn.com";

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }

}