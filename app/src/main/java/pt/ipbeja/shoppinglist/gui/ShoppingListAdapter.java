package pt.ipbeja.shoppinglist.gui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pt.ipbeja.shoppinglist.R;
import pt.ipbeja.shoppinglist.model.ShoppingListViewModel;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItem;
import pt.ipbeja.shoppinglist.roomdatabase.EntityShoppingItemUpdate;

/**
 * Needed for androidx.recyclerview.widget.RecyclerView in
 * MainActivity with layout in "activity_main.xml"
 * @author João Paulo Barros
 * @version  2020/01/18
 */
public class ShoppingListAdapter extends
        ListAdapter<EntityShoppingItem, ShoppingListAdapter.ItemViewHolder> {

    private static ShoppingListViewModel shoppingListViewModel;

    public ShoppingListAdapter(ShoppingListViewModel shoppingListViewModel) {
        super(DIFF_CALLBACK);
        ShoppingListAdapter.shoppingListViewModel = shoppingListViewModel;
    }

    @NonNull
    @Override // called one time for each item
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_layout, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        EntityShoppingItem current = this.getItem(position);
        holder.bind(current);
    }

    // mirrors LinearLayout in recyclerview_item_layout.xml
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView shoppingListItemView;

        private ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shoppingListItemView = itemView.findViewById(R.id.recyclerview_item_textview);

            shoppingListItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shoppingListItemView.setChecked(!shoppingListItemView.isChecked());
                    //shoppingListItemView.setCheckMarkDrawable(shoppingListItemView.isChecked() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
                    //shoppingListItemView.setCheckMarkDrawable(shoppingListItemView.isChecked() ? R.drawable.checked_on : R.drawable.checked_off);
                    ShoppingListAdapter.shoppingListViewModel.update(
                            new EntityShoppingItemUpdate(((CheckedTextView)v).getText().toString(), !shoppingListItemView.isChecked()));
                }
            });
        }

        /**
         * updates gui based in current data on viewmodel and database
         * @param current item with data in viewmodel and database
         */
        public void bind(EntityShoppingItem current) {
            this.shoppingListItemView.setChecked(current.selected);
            this.shoppingListItemView.setText(current.itemName);
            this.shoppingListItemView.setCheckMarkDrawable(current.selected ? R.drawable.checked_on : R.drawable.checked_off);
        }
    }

    public static final DiffUtil.ItemCallback<EntityShoppingItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<EntityShoppingItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull EntityShoppingItem oldItem, @NonNull EntityShoppingItem newItem) {
                    return oldItem == newItem;
                }

                @Override
                public boolean areContentsTheSame(@NonNull EntityShoppingItem oldItem, @NonNull EntityShoppingItem newItem) {
                    return oldItem.itemName.equals(newItem.itemName);
                }
            };

}
