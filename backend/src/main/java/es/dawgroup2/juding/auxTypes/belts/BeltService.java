package es.dawgroup2.juding.auxTypes.belts;

import org.springframework.stereotype.Component;

@Component
public class BeltService {

    /**
     * Finds a {@link Belt} attending to its stringified shortname (e. g. "BAm" returns {@code Belt.BAm}).
     *
     * @param id Stringified shortname.
     * @return Belt object.
     */
    public Belt findBeltById(String id) {
        for (Belt b : Belt.values()) {
            if (b.name().equals(id))
                return b;
        }
        return null;
    }

    /**
     * Returns a String with HTML <option> elements, one per existing belt.
     *
     * @param id        Default selected belt (null if not necessary)
     * @param onlyBlack True if the String must contain only black belts.
     * @return String built with all the available options attending to the desired parameters.
     */
    public String getSelectField(Belt id, boolean onlyBlack) {
        StringBuilder sb = new StringBuilder();
        for (Belt b : Belt.values()) {
            if (!onlyBlack || b.isBlack()) {
                sb.append("<option ");
                if (b.equals(id)) sb.append(" selected ");
                sb.append("value=\"").append(b.name()).append("\">").append(b.getLongName()).append("</option>\n");
            }
        }
        return sb.toString();
    }
}
