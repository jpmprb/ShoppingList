package pt.ipbeja.shoppinglist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItemUpdate;

/**
 * App ViewModel with ling to Reposiry Facade and data,
 * tobe presented in MainActiivty,
 * updated by the Room database
 * @author João Paulo Barros
 * @version  2020/01/18
 */
public class ShoppingListViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<EntityShoppingItem>>  mAllItems;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new Repository(application);
        this.mAllItems = this.mRepository.getmAllItems();
    }

    public LiveData<List<EntityShoppingItem>> getmAllItems() {
        return mAllItems;
    }

    public void insert(EntityShoppingItem item) {
        mRepository.insert(item);
    }

    public void update(EntityShoppingItemUpdate itemUpdate) {
        mRepository.update(itemUpdate);
    }


//    public void updateSelected(EntityShoppingItemUpdate itemUpdated) {
//        mRepository.updateSelected(itemUpdated);
//    }
}
