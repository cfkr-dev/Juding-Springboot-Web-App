package es.dawgroup2.juding.auxTypes.refereeRange;

import org.springframework.stereotype.Component;

@Component
public class RefereeRangeService {

    /**
     * Returns a RefereeRange enumeration value attending to its stringified identificator.
     *
     * @param id Range identificator
     * @return Range enumeration value
     */
    public RefereeRange findRefereeRangeById(String id) {
        for (RefereeRange rr : RefereeRange.values()) {
            if (rr.name().equals(id))
                return rr;
        }
        return null;
    }

    /**
     * Returns a String with HTML <option> elements, one per existing range.
     *
     * @param defaultValue Default selected range (null if not necessary)
     * @param onlyActive   True if "solicitant" range should not be included.
     * @return String built with all the available options attending to the desired parameters.
     */
    public String generateActiveRangesSelect(RefereeRange defaultValue, boolean onlyActive) {
        StringBuilder sb = new StringBuilder();
        for (RefereeRange rr : RefereeRange.values()) {
            if (!(onlyActive && !rr.isActiveReferee())) {
                sb.append("<option ");
                if (rr == defaultValue) sb.append("selected ");
                sb.append("value=\"")
                        .append(rr.name())
                        .append("\">")
                        .append(rr.getDescription())
                        .append("</option>");
            }
        }
        return sb.toString();
    }
}