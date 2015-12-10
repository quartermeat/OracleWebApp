/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.williamsonet;

import java.math.BigDecimal;
import java.sql.Date;
/**
 *
 * @author jeremy.williamson
 */
public class Transaction {
    private Date date;
    private BigDecimal amount;

    
    public Date getDate() {
        return date;
    }//end getter

    public void setDate(Date date) {
        this.date = date;
    }//end setter

    public BigDecimal getAmount() {
        return amount;
    }//end getter

    public void setAmount(BigDecimal amount) {
        //even though the textbox is validated and a number converter is used: this is a failsafe 
        //to ensure the set amount is rounded up or down to the nearest 2 decimal places
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }//end setter
    
    public String toString(){
        return date + ": " +  amount; 
    }//end toString
           
}//end Transaction Class
