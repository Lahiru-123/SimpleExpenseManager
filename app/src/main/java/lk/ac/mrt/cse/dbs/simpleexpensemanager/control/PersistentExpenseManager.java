package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentDataAccessObjects.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentDataAccessObjects.PersistentTransactionDAO;

/**
 *
 */
public class PersistentExpenseManager extends ExpenseManager {

    public PersistentExpenseManager(Context context) {
        super(context);
        setup(context);
    }

    @Override
    public void setup(Context context) {
        /*** Setup the persistent storage implementation ***/

        TransactionDAO persistentMemoryTransactionDAO = new PersistentTransactionDAO(context);
        setTransactionsDAO(persistentMemoryTransactionDAO);

        AccountDAO persistentMemoryAccountDAO = new PersistentAccountDAO(context);
        setAccountsDAO(persistentMemoryAccountDAO);

        /*** End***/
    }
}
