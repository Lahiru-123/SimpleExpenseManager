/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentDataAccessObjects;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager.ACCOUNT_NO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager.AMOUNT;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager.DATE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager.Expense_TYPE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB.DBManager.TRANSACTION;


public class PersistentTransactionDAO implements TransactionDAO {
    private final DBManager helper;
    private SQLiteDatabase DB;

    public PersistentTransactionDAO(Context context) {
        helper = new DBManager(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType moneyType, double amount) {

        DB = helper.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ContentValues values = new ContentValues();
        values.put(DATE, dateFormat.format(date));
        values.put(ACCOUNT_NO, accountNo);
        values.put(Expense_TYPE, String.valueOf(moneyType));
        values.put(AMOUNT, amount);


        DB.insert(TRANSACTION, null, values);
        DB.close();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {
        List<Transaction> transactions = new ArrayList<>();

        DB = helper.getReadableDatabase();

        String[] projection = {DATE, ACCOUNT_NO, Expense_TYPE, AMOUNT};

        Cursor cursor = DB.query(TRANSACTION, projection, null, null, null, null, null);

        while(cursor.moveToNext()) {
            String dateLine = cursor.getString(cursor.getColumnIndex(DATE));
            @SuppressLint("SimpleDateFormat") Date date;
            date = new SimpleDateFormat("dd-MM-yyyy").parse(dateLine);
            String acc_No = cursor.getString(cursor.getColumnIndex(ACCOUNT_NO));
            String type = cursor.getString(cursor.getColumnIndex(Expense_TYPE));
            ExpenseType moneyType = ExpenseType.valueOf(type);
            double amount = cursor.getDouble(cursor.getColumnIndex(AMOUNT));
            Transaction transaction = new Transaction(date,acc_No,moneyType,amount);

            transactions.add(transaction);
        }
        cursor.close();
        return transactions;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {

        List<Transaction> transactions = new ArrayList<>();

        DB = helper.getReadableDatabase();

        String[] projection = {DATE, ACCOUNT_NO, Expense_TYPE, AMOUNT};

        Cursor cursor = DB.query(TRANSACTION, projection, null, null, null, null, null);

        int size = cursor.getCount();

        while(cursor.moveToNext()) {
            String dateLine = cursor.getString(cursor.getColumnIndex(DATE));
            @SuppressLint("SimpleDateFormat") Date date;
            date = new SimpleDateFormat("dd-MM-yyyy").parse(dateLine);
            String acc_No = cursor.getString(cursor.getColumnIndex(ACCOUNT_NO));
            String type = cursor.getString(cursor.getColumnIndex(Expense_TYPE));
            ExpenseType moneyType = ExpenseType.valueOf(type);
            double amount = cursor.getDouble(cursor.getColumnIndex(AMOUNT));
            Transaction transaction = new Transaction(date,acc_No,moneyType,amount);

            transactions.add(transaction);
        }

        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit,size);
    }

}
