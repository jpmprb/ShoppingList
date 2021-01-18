package pt.ipbeja.shoppinglist.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipbeja.shoppinglist.roomdatabase.DaoShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.DatabaseShoppingList;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItemUpdate;

/**
 * Just a Facade between ShoppingListViewModel and Room
 * @author João Paulo Barros
 * @version  2020/01/18
 */
public class Repository {

    private DaoShoppingItem mItemDao;
    private LiveData<List<EntityShoppingItem>> mAllItems;

    public Repository(Application app) {
        DatabaseShoppingList db = DatabaseShoppingList.getDatabase(app);
        mItemDao = db.shoppingItemDao();
        mAllItems = this.mItemDao.loadAllItemsOrdered();
    }

    public LiveData<List<EntityShoppingItem>> getmAllItems() {
        return this.mAllItems;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(EntityShoppingItem item) {
        DatabaseShoppingList.databaseWriteExecutor.execute(() -> {
            if (mItemDao.itemNameExists(item.itemName) == false)
                mItemDao.insert(item);
        });
    }

    public void update(EntityShoppingItemUpdate itemUpdate) {
        DatabaseShoppingList.databaseWriteExecutor.execute(() -> {
            mItemDao.update(itemUpdate);
        });
    }

//    public void updateSelected(EntityShoppingItemUpdate itemUpdated) {
//        DatabaseShoppingList.databaseWriteExecutor.execute(() -> {
//            mItemDao.updateSelected(itemUpdated);
//        });
//    }
}

