package pt.ipbeja.shoppinglist.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Entity to update EntityShoppingItem iin DatabaseShoppingItem
 * @author João Paulo Barros
 * @version  2020/01/18
 */
@Entity
public class EntityShoppingItemUpdate {
    //@PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemName")
    public String itemName;
    @NonNull
    public Boolean selected;

    public EntityShoppingItemUpdate(String itemName, boolean selected) {
        this.itemName = itemName;
        this.selected = selected;
    }
}

