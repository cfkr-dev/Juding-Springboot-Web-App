package es.dawgroup2.juding.attendances;

import org.springframework.stereotype.Component;

@Component
public class AttendanceService {
    public Attendance findAttendanceById(String id) {
        for (Attendance attendance : Attendance.values()) {
            if (attendance.getLongName().equals(id))
                return attendance;
        }
        return null;
    }

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
