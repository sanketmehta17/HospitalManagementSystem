package Service;

import Interface.IAccount;
import Model.Accounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 *  Name of file: ManageAccountService.java
 *  Author:  Abimbola Babalola
 *  Purpose: This class manages the connection to the accounts
 *  Description: This class accesses the database to view accounts,
 *               insert and delete record
 */

public class ManageAccountService  implements IAccount {

  ResultSet resultSet = null;
  DatabaseService dbService;
  PreparedStatement insertAccounts;
  List<List<String>> dbRecord;
  boolean response = false;
  Connection conn;

  public ManageAccountService() {
  }

  public ManageAccountService(DatabaseService dbService) {
    this.dbService = dbService;
    this.conn=dbService.conn;
  }

  @Override
  public List<List<String>> getIncome( ) {
    List<String> dbRow;
    dbRecord = new ArrayList<>();
    try {
      String query="select order_id, name,qty, final_bill from order_items;" ;
      resultSet=  dbService.executeQuery(query);
      while (resultSet.next()) {
        dbRow = new ArrayList<>();
        dbRow.add(String.valueOf(resultSet.getInt("order_id")));
        dbRow.add(resultSet.getString("name"));
        dbRow.add(String.valueOf(resultSet.getInt("qty")));
        dbRow.add(String.valueOf(resultSet.getDouble("final_bill")));
        dbRecord.add(dbRow);
      }
    }
    catch (SQLException | ClassNotFoundException e) {
      e.getMessage();
    }

    return dbRecord;
  }

  @Override
  public List<List<String>> getExpenses( ) {
    List<String> dbRow;
    dbRecord = new ArrayList<>();
    try {
      String query="select   accId , account_date, account_type , category ,  pay_name,  amount  from  ExpenseAccounts " ;
      resultSet=  dbService.executeQuery(query);
      while (resultSet.next()) {
        dbRow = new ArrayList<>();
        dbRow.add(String.valueOf(resultSet.getInt("accId")));
        dbRow.add(String.valueOf(resultSet.getDate("account_date")));
        dbRow.add(resultSet.getString("account_type"));
        dbRow.add(resultSet.getString("category"));
        dbRow.add(resultSet.getString("pay_name"));
        dbRow.add(String.valueOf(resultSet.getDouble("amount")));
        dbRecord.add(dbRow);
      }
    }
    catch (SQLException | ClassNotFoundException e) {
      e.getMessage();
    }

    return dbRecord;
  }

  @Override
  public boolean addExpense(Accounts accounts) {
    try {
      String expenseType ;
      if(accounts.getExpenseType()==1){
        expenseType="Maintenance";
      }
      else if(accounts.getExpenseType()==2){
        expenseType="Supplies";
      }
      else {
        expenseType="Salary";
      }
      /*String queryUserTable = " insert into ExpenseAccounts(account_date, category,pay_name,amount,status) " +
              "select "+accounts.getDate()+ ", "+expenseType+", "+accounts.getPayName()+", "+accounts.getAmount()+", 'A' from dual " +
              "where not exists (select * from ExpenseAccounts where account_date='"+accounts.getDate()+"' and " +
              "category='"+expenseType+"' and pay_name='"+accounts.getPayName()+"'  and amount= "+accounts.getAmount()+";";
              */
      String queryUserTable = " insert into ExpenseAccounts(account_date, category,pay_name,amount,status) " +
              "select ?,?, ?, ?, ? from dual  where not exists (select * from ExpenseAccounts where account_date=? and " +
              " category=? and pay_name=?  and amount= ?;";

      insertAccounts =  dbService.conn.prepareStatement(queryUserTable);
      insertAccounts.setString(1, accounts.getDate() );
      insertAccounts.setString(2, expenseType);
      insertAccounts.setString(3, accounts.getPayName());
      insertAccounts.setDouble(4, accounts.getAmount());
      insertAccounts.setString(5, "A");
      insertAccounts.setString(6, accounts.getDate() );
      insertAccounts.setString(7, expenseType);
      insertAccounts.setString(8, accounts.getPayName());
      insertAccounts.setDouble(9, accounts.getAmount());
      insertAccounts.executeUpdate();
      response=true;
    } catch (SQLException   e) {
      System.out.println("SQLException: " + e.getMessage());
    }
    return response;
  }

  @Override
  public boolean DeleteRecord(Accounts accounts) {
    try {
      String queryUserTable = "update ExpenseAccounts set status='D' where account_date=?  " +
              "  and pay_name=? and amount=? ";
      insertAccounts = dbService.conn.prepareStatement(queryUserTable);
      insertAccounts.setString(1, accounts.getDate());
      insertAccounts.setString(2, accounts.getPayName());
      insertAccounts.setDouble(3, accounts.getAmount());
      insertAccounts.executeUpdate();
      response = true;
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
    }
    return response;
  }
}