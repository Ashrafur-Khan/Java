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


public class StudentQueries {
    private static Connection connection;
    public static PreparedStatement addStudent; 
    public static PreparedStatement getAllStudents;
    
    public static PreparedStatement getStudent;
    public static PreparedStatement dropStudent;
    
    private static ResultSet resultSet;
    public static ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();

    
    
    public static void addStudent (StudentEntry student) {
    connection = DBConnection.getConnection();
    
    try {
        addStudent = connection.prepareStatement("insert into app.student "
                + "(studentid, firstname, lastname) values(?,?,?) ");
        
        addStudent.setString(1, student.getStudentID());
        addStudent.setString(2, student.getFirstName());
        addStudent.setString(3, student.getLastName());
        addStudent.executeUpdate();
        
    }
    catch(SQLException sqlException) {
        sqlException.printStackTrace();
    }
        
    }
    
    
   public static ArrayList<StudentEntry> getAllStudents() {
       allStudents.clear();
        connection = DBConnection.getConnection();
        
        try {
            getAllStudents = connection.prepareStatement("select * from app.student");
            resultSet = getAllStudents.executeQuery();
            while(resultSet.next()){
                allStudents.add(new StudentEntry(
                resultSet.getString(1), 
                resultSet.getString(2), 
                resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException) { 
            sqlException.printStackTrace();
        }
        
       return allStudents;
    }
    
   
   
   
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        
        try {
            getStudent = connection.prepareStatement("select * from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            if (resultSet.next()) {
            return new StudentEntry(
                    resultSet.getString(1), 
                    resultSet.getString(2), 
                    resultSet.getString(3));  
                    
            }
            
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        
        try { 
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?"); 
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate(); 
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
    
}

