package es.dawgroup2.juding.users.refereeRange;

import org.springframework.stereotype.Component;

@Component
public class RefereeRangeService {
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