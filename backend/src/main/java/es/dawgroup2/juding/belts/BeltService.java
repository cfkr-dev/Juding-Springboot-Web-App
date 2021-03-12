package es.dawgroup2.juding.belts;

import org.springframework.stereotype.Component;

@Component
public class BeltService {
    public Belt findBeltById(String id) {
        for (Belt b : Belt.values()) {
            if (b.name().equals(id))
                return b;
        }
        return null;
    }

    public String getSelectField(String id) {
        StringBuilder sb = new StringBuilder();
        for (Belt b : Belt.values()) {
            sb.append("<option ");
            if (b.name().equals(id)) sb.append(" selected ");
            sb.append("value=\"").append(b.name()).append("\">").append(b.getLongName()).append("</option>\n");
        }
        return sb.toString();
    }
}
