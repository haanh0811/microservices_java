package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Sport")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sport {
    @Id
    private String _id;

    private String id;
    private String name;
    private Object site;

    private double latitudeMin;
    public  String get_id(){
        return _id;

    }

    public void set_id(String _id){
        this._id = _id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Object getSite(){
        return site;
    }

    public void setSite(Object site){
        this.site = site;
    }

    public boolean isSiteString(){
        return site instanceof String;
    }

    public boolean isSiteList(){
        return site instanceof List;
    }

    public List<String> getSiteList(){
        List<String> siteList = new ArrayList<>();
        List<String> genericList = (List<String>) site;
        for (String s : genericList) {
            siteList.add(s);
        }
        return siteList;
    }

    public double getLatitudeMin(){
        return latitudeMin;
    }

    public void setLatitudeMin(double latitudeMin){
        this.latitudeMin = latitudeMin;
    }
}
