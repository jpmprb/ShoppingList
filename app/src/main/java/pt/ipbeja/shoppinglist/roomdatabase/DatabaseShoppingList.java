package pt.ipbeja.shoppinglist.roomdatabase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room database
 * @author João Paulo Barros
 * @version  2020/01/18
 */
// https://synetech.cz/en/blog/how-to-migrate-room-database-painlessly-en
@Database(entities = {EntityShoppingItem.class}, version = 1, exportSchema = false)
public abstract class DatabaseShoppingList extends RoomDatabase {

    public abstract DaoShoppingItem shoppingItemDao();

    private static volatile DatabaseShoppingList INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback
            sRoomDatabaseCallBackToAddInitialData = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("DatabaseShoppingList",
                    "OnCreate sRoomDatabaseCallBackToAddInitialData");
            databaseWriteExecutor.execute(() -> {
                        DaoShoppingItem dao = INSTANCE.shoppingItemDao();
                        dao.deleteAll();
                        dao.insert(new EntityShoppingItem("Arroz"));
                        dao.insert(new EntityShoppingItem("Iogurtes"));
                        Log.d("DatabaseShoppingList",
                                "add initial items");
                    }
            );
        }
    };


    public static DatabaseShoppingList getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseShoppingList.class) {
                if (INSTANCE == null) {
                    Log.d("DatabaseShoppingList","build database");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseShoppingList.class, "word_database")
                            .addCallback(sRoomDatabaseCallBackToAddInitialData)
                            // .allowMainThreadQueries()
                            // .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        Log.d("DatabaseShoppingList","get database");
        return INSTANCE;
    }
}
