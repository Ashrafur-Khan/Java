/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ashrafur
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class CourseQueries {
    private static Connection connection;
    // don't know if we need it
    //private static ArrayList<String> faculty = new ArrayList<String>();
    public static PreparedStatement getAllCourses; 
    public static PreparedStatement addCourse; 
    public static PreparedStatement getAllCourseCodes; 
    public static PreparedStatement getCourseSeats;
    private static ResultSet resultSet;
    public static PreparedStatement dropCourse;
    public static PreparedStatement getCourse;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        
        try {
            getAllCourses = connection.prepareStatement("select * from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            while(resultSet.next()){
                allCourses.add(new CourseEntry(
                resultSet.getString(1), 
                resultSet.getString(2), 
                resultSet.getString(3), 
                resultSet.getInt(4)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
       return allCourses;
    }
    
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        
        try {

            addCourse = connection.prepareStatement("insert into app.course "
                    + "(semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setString(4, ""+course.getSeats());
            addCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }   
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> allCodes = new ArrayList<String>();
        
        try {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            while(resultSet.next()){
                allCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException) { 
            sqlException.printStackTrace();
        }
        
        return allCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        
        try { 
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            if (resultSet.next()){
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return seats;
    }
    
    public static CourseEntry getCourse(String courseCode) {
        connection = DBConnection.getConnection();
        
        try {
            getCourse = connection.prepareStatement("select * from app.course where coursecode = ?");
            getCourse.setString(1, courseCode);
            resultSet = getCourse.executeQuery(); 
            
            resultSet.next(); 
            return new CourseEntry(
                    resultSet.getString(1), 
                    resultSet.getString(2), 
                    resultSet.getString(3),
                    resultSet.getInt(4));  
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    
    
    public static void dropCourse(String semester, String courseCode) { 
        connection = DBConnection.getConnection();
        
        try { 
            dropCourse = connection.prepareStatement("delete from app.course where semester = ? and coursecode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
            
        }
        catch (SQLException sqlException) { 
            sqlException.printStackTrace();
        }
    }
    
        
    
}
