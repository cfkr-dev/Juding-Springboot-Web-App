package es.dawgroup2.juding.users.gender;

import org.springframework.stereotype.Component;

@Component
public class GenderService {
    public Gender findGenderById(String id) {
        for (Gender g : Gender.values()) {
            if (g.name().equals(id))
                return g;
        }
        return null;
    }

    public String getRadioField(Gender gender) {
        StringBuilder sb = new StringBuilder();
        Gender[] values = Gender.values();
        int length = values.length;
        for (int i = 0; i < length; i++) {
            sb.append("<div class=\"form-check\">\n" + "    <input ");
            if (gender != null)
                if (gender == values[i])
                    sb.append("checked ");
            sb.append("class=\"form-check-input\" id=\"gender")
                    .append(values[i].name())
                    .append("\" name=\"gender\" required type=\"radio\" value=\"")
                    .append(values[i].name())
                    .append("\">\n")
                    .append("    <label class=\"form-check-label\" for=\"gender")
                    .append(values[i].name())
                    .append("\">")
                    .append(values[i].getDescription())
                    .append("</label>\n");
            if (i == length - 1)
                sb.append("<div class=\"invalid-feedback\">Es obligatorio seleccionar un género.</div>\n");
            sb.append("</div>\n");
        }
        return sb.toString();
    }
}
