package es.dawgroup2.juding.auxTypes.attendances;

import org.springframework.stereotype.Component;

@Component
public class AttendanceService {

    /**
     * Finds the Attendance object by its long name
     * @param id Id of the attendance
     * @return The Attendance object or null
     */
    public Attendance findAttendanceById(String id) {
        for (Attendance attendance : Attendance.values()) {
            if (attendance.getLongName().equals(id))
                return attendance;
        }
        return null;
    }

    /**
     * Finds the long name by the Attendance object
     * @param attendance The Attendance object
     * @return The long name of the Attendance object
     */
    public String getAttendanceToString(Attendance attendance) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Attendance a : Attendance.values()) {
            if(a.equals(attendance)){
                return a.getLongName();
            }
        }
        return null;
    }
}
