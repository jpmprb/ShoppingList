package pt.ipbeja.shoppinglist.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import pt.ipbeja.shoppinglist.R;
import pt.ipbeja.shoppinglist.model.ShoppingListViewModel;
import pt.ipbeja.shoppinglist.roomdatabase.DaoShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItemUpdate;

/**
 * Activity with a RecycleView to show shopping list with checkable items
 * and a floating button to add new item
 * @author João Paulo Barros
 * @version  2020/01/18
 */
public class MainActivity extends AppCompatActivity {

    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 1;
    private ShoppingListViewModel mShoppingListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //https://stackoverflow.com/questions/42379660/how-to-prevent-recyclerview-item-from-blinking-after-notifyitemchangedpos/42379756
        recyclerView.setItemAnimator(null);

        this.mShoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        final ShoppingListAdapter adapter = new ShoppingListAdapter(this.mShoppingListViewModel);

        mShoppingListViewModel.getmAllItems().observe(this, items -> {
            // Update the items in the adapter.
            Log.d("Received by observer", itemsListToString(items));
            adapter.submitList(items);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, AddItem.class);
            startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);
        });
    }

    /**
     * for debug purposes
     * @param items
     * @return string with all item names separated by commas
     */
    private String itemsListToString(List<EntityShoppingItem> items) {
        String s = "";
        for(EntityShoppingItem item : items) { s += item.itemName + ", "; }
        return s.substring(0, s.lastIndexOf(" ") - 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            EntityShoppingItem item = new EntityShoppingItem(data.getStringExtra(AddItem.PUTEXTRA_NAME));
            Log.d("onActivityResult", item.itemName);
            mShoppingListViewModel.insert(item);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    void insertInViewModel(EntityShoppingItemUpdate itemUpdate) {
        mShoppingListViewModel.update(itemUpdate);
    }
}