package pt.ipbeja.shoppinglist.roomdatabase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity for DatabaseShoppingItem
 * @author João Paulo Barros
 * @version  2020/01/18
 */
@Entity(tableName = "EntityShoppingItem")
public class EntityShoppingItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemName")
    public String itemName;
    @NonNull
    public Boolean selected;

    public EntityShoppingItem(@NonNull String itemName) {
        this(itemName, false);
    }

    public EntityShoppingItem(@NonNull String itemName, boolean select) {
        this.itemName = itemName;
        this.selected = select;
    }
}
