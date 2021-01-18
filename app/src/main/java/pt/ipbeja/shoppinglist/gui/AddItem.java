package pt.ipbeja.shoppinglist.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import pt.ipbeja.shoppinglist.R;

/**
 * Activity to add an item to the shopping list.
 * Just an EditText and a Button to save it
 * @author João Paulo Barros
 * @version  2020/01/18
 */
public class AddItem extends AppCompatActivity {

    final public static String PUTEXTRA_NAME = "item data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final EditText mEditItemText = findViewById(R.id.edit_item_text);

        final Button button = findViewById(R.id.button_add_item);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditItemText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String itemName = mEditItemText.getText().toString();
                replyIntent.putExtra(PUTEXTRA_NAME, itemName);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        // https://stackoverflow.com/questions/5593053/open-soft-keyboard-programmatically
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mEditItemText.requestFocus();
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
