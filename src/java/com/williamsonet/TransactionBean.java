/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.williamsonet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author jeremy.williamson
 */
@ManagedBean
@SessionScoped
public class TransactionBean implements Serializable {

    private final String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private final String userid = "jeremy";
    private final String password = "JKha3454";
    private Connection connection;
    private OracleDataSource datasource;
    
    private BigDecimal newAmount;
    private String verification;
            
    public TransactionBean() {

    }//end constructor
    
    public String getVerification() {
        return verification;
    }//end getter

    public void setVerification(String verification) {
        this.verification = verification;
    }//end setter
    
    public BigDecimal getNewAmount() {
        return newAmount;
    }//end getter

    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
    }//end setter

    public void getDBConnection() throws SQLException {
        try {
            datasource = new OracleDataSource();
            datasource.setURL(jdbcURL);
            connection = datasource.getConnection(userid, password);

        } catch (SQLException e) {
            //connection error checking
            //return "Error getting datasource!";
        }//end try/catch
        
        //Used for some error checking
        /*if (connection != null) {
            return connection.getSchema();
        } else {
            return "null";
        }//end if/else*/
                
    }//end getDBConnection
    
    public List<Transaction> getTransactionList() throws SQLException{
        
        //get database connection
        getDBConnection();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT DATETIME, AMOUNT FROM TRANSACTIONS");
        
        
        ResultSet result = preparedStatement.executeQuery();
        
        List<Transaction> transactionList = new ArrayList<>();
        
        while(result.next()){
            Transaction transaction = new Transaction();
            
            transaction.setDate(result.getDate("DATETIME"));
            transaction.setAmount(result.getBigDecimal("AMOUNT"));
            
            //store all data into a list
            transactionList.add(transaction);
        }//end While
        
        connection.close();
        
        return transactionList;
        
    }///end get TransactionList
    
    public BigDecimal getTotal() throws SQLException{
        //get database connection
        getDBConnection();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT AMOUNT FROM TRANSACTIONS");
        
        ResultSet result = preparedStatement.executeQuery();
        
        BigDecimal total = BigDecimal.valueOf(0);
        
        while(result.next()){
            total = total.add(result.getBigDecimal("amount").setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }//end While
        
        connection.close();
        
        return total;
    }//end getTotal()
    
    public void addAmount() throws SQLException{
        //get database connection
        getDBConnection();
        
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(   "INSERT INTO TRANSACTIONS"
                                                                            +"(AMOUNT)"
                                                                            +"VALUES"
                                                                            +"("+newAmount+")");
        preparedStatement.executeQuery();
        
        verification = "entered into TRANSACTIONS";
        
        }catch (SQLException e){
            verification = "SQL Error! Transaction did not get entered.";
            e.printStackTrace();
            connection.close();
        }
        connection.close();
    }//end addAmount

}//end TransactionBean class
