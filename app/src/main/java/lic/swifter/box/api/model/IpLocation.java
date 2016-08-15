package lic.swifter.box.api.model;

/**
 * Created by lic on 16-8-1.
 */
public class IpLocation {

    public String searchText;

    public String area;
    public String location;

    @Override
    public String toString() {
        return "IpLocation{" +
                "area='" + area + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
