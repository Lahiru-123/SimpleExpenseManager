package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE = "200300A.sqlite";
    private static final int VERSION = 1;

    //TABLES
    public static final String ACCOUNT = "account";
    public static final String TRANSACTION = "transac";

    //COMMON COLUMNS
    public static final String ACCOUNT_NO = "accountNo";

    //ACCOUNT TABLE - COLUMNS
    public static final String BANK = "bankName";
    public static final String ACC_HOLDER = "accountHolder";
    public static final String BALANCE = "balance";

    //TRANSACTION TABLE - COLUMNS
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String Expense_TYPE = "expenseType";
    public static final String AMOUNT = "amount";


    public DBManager(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query_1 = "CREATE TABLE " + ACCOUNT + "(" +
                ACCOUNT_NO + " TEXT PRIMARY KEY, " +
                BANK + " TEXT ," +
                ACC_HOLDER + " TEXT , " +
                BALANCE + " DOUBLE)";


        String query_2 = "CREATE TABLE " + TRANSACTION + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATE + " TEXT , " +
                Expense_TYPE + " TEXT , " +
                AMOUNT + " DOUBLE , " +
                ACCOUNT_NO + " TEXT," +
                "FOREIGN KEY (" + ACCOUNT_NO + ") REFERENCES " + ACCOUNT + "(" + ACCOUNT_NO + "))";

        database.execSQL(query_1);
        database.execSQL(query_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        database.execSQL("drop table if exists " + ACCOUNT);
        database.execSQL("drop table if exists " + TRANSACTION);

        // create new tables
        onCreate(database);
    }

}
