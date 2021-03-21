package es.dawgroup2.juding.auxTypes.gender;

import org.springframework.stereotype.Component;

@Component
public class GenderService {

    /**
     * Returns a Gender enumeration value attending to its stringified identificator (e. g. "M" for {@code Mujer}).
     *
     * @param id Gender identificator
     * @return Gender value
     */
    public Gender findGenderById(String id) {
        for (Gender g : Gender.values()) {
            if (g.name().equals(id))
                return g;
        }
        return null;
    }

    /**
     * Returns a radio button input with as many values as Genders have been declared in enum type.
     *
     * @param gender Default selected gender (nullable)
     * @return String with radio button input ready for printing.
     */
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
