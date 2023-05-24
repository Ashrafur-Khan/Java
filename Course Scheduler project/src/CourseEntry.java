
import java.util.LinkedList;
import java.util.Queue;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ashrafur
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String courseDescription;
    private int seats;
    private Queue<StudentEntry> waitlist;

    public CourseEntry(String semester, String courseCode, String courseDescription, int seats) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.seats = seats;
        this.waitlist = new LinkedList<>(); 
    }
    
    

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public int getSeats() {
        return seats;
    }
    
    public Queue<StudentEntry> getWaitlist() {
        return waitlist;
    }

    
}
