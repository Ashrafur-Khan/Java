

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ashrafur
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;





public class ScheduleQuery {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent; 
    private static PreparedStatement getScheduledStudentCount; 
    private static int studentCount;
    private static ResultSet resultSet;
    //private static ArrayList<ScheduleEntry> studentSchedule; 
    private static PreparedStatement getScheduledStudentByCourse; 
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse; 
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry; 
    
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule "
                    + "(semester, coursecode, status, studentid, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStatus());
            addScheduleEntry.setString(4, entry.getStudentID());
            Timestamp timestamp = new Timestamp(entry.getTimestamp().getTime());
            addScheduleEntry.setTimestamp(5, timestamp);
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        //Trying the list inside method
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<>();
        
        try {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule "
                + "where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            //Had to remake schedule table because of errors
            if (resultSet != null) {
                while(resultSet.next()) {
                    studentSchedule.add(new ScheduleEntry(
                    resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
                    resultSet.getString(4), resultSet.getTimestamp(5))); 
                }
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return studentSchedule;
    } 

    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        
        int studentCount = 0; 
        
        try {
            getScheduledStudentCount = connection.prepareStatement("select status from app.schedule "
                    + "where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            while(resultSet.next()) {
                String status = resultSet.getString(1);
                if (status.equalsIgnoreCase("S")){
                    studentCount ++;
            }
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<>();
                
        try {
            getScheduledStudentByCourse = connection.prepareStatement("select studentid, timestamp from app.schedule "
                    + "where semester = ? and coursecode = ? and status = ?");
            getScheduledStudentByCourse.setString(1, semester);
            getScheduledStudentByCourse.setString(2, courseCode); 
            getScheduledStudentByCourse.setString(3, "S"); 
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next()) { 
                String studentID = resultSet.getString(1);
                Timestamp timestamp = resultSet.getTimestamp(2);
                scheduledStudents.add(new ScheduleEntry (
                        semester, studentID, courseCode, "S", timestamp));
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduledStudents;
    }
    
    public static  ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
                
        try {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select studentid, timestamp from app.schedule "
                    + "where semester = ? and coursecode = ? and status = ?");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode); 
            getWaitlistedStudentsByCourse.setString(3, "W"); 
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next()) { 
                String studentID = resultSet.getString(1);
                Timestamp timestamp = resultSet.getTimestamp(2);
                waitlistedStudents.add(new ScheduleEntry (
                        semester, studentID, courseCode, "W", timestamp));
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        
        try { 
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule "
                    + "where semester = ? and studentid = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester); 
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode); 
            dropStudentScheduleByCourse.executeUpdate(); 
            
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        try {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule "
                    + "where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester); 
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate(); 

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        
        try { 
            updateScheduleEntry = connection.prepareStatement("update app.schedule "
                    + "set status = 'S', "
                    + "timestamp = CURRENT_TIMESTAMP "
                    + "where semester = ? and studentid = ? and coursecode = ?");
            updateScheduleEntry.setString(1, semester);
            updateScheduleEntry.setString(2, entry.getStudentID());
            updateScheduleEntry.setString(3, entry.getCourseCode());
            updateScheduleEntry.executeUpdate();
 
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}
