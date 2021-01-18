package pt.ipbeja.shoppinglist.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao for DatabaseShoppingItem
 * @author João Paulo Barros
 * @version  2020/01/18
 */
@Dao
public interface DaoShoppingItem {
    @Query("SELECT * FROM EntityShoppingItem ORDER BY itemName ASC")
    LiveData<List<EntityShoppingItem>> loadAllItemsOrdered();

    //@Query("SELECT * FROM EntityShoppingItem WHERE selected = 1")
    //LiveData<List<EntityShoppingItem>> loadAllSelected();

    @Insert
    void insert(EntityShoppingItem item);

    @Update(entity = EntityShoppingItem.class)
    public void update(EntityShoppingItemUpdate itemToUpdate);

    @Delete
    void delete(EntityShoppingItem item);

    @Query("DELETE FROM EntityShoppingItem")
    void deleteAll();

    @Query("SELECT EXISTS(SELECT * FROM EntityShoppingItem WHERE itemName = :itemName)")
    boolean itemNameExists(String itemName);
}
