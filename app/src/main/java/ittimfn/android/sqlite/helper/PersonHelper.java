package ittimfn.android.sqlite.helper;

import ittimfn.android.sqlite.recode.Person;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PersonHelper extends SQLiteOpenHelper {

    public PersonHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = 
            String.format("CREATE TABLE %s (", getTableName()) + 
            String.format(
                "%s %s PRIMARY KEY AUTOINCREMENT, ", 
                PersonColumn.ID.getName(), PersonColumn.ID.getType()) +
            String.format(
                "%s %s ", 
                PersonColumn.NAME.getName(), PersonColumn.NAME.getType()) +
            ")";
        db.execSQL(sql);

        insertInitialData(db);

    }

    private void insertInitialData(SQLiteDatabase db) {
        List<Person> persons = InitialData.getInitialData();
        for (Person person : persons) {
            String sql = String.format(
                "INSERT INTO %s (%s, %s) VALUES (%d, '%s')",
                getTableName(),
                PersonColumn.ID.getName(),
                PersonColumn.NAME.getName(),
                person.id(),
                person.name()
            );
            db.execSQL(sql);
        }
    }

    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + getTableName() + " order by " + PersonColumn.ID.getName(), null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                    cursor.getInt(0),
                    cursor.getString(1)
                );
                persons.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return persons;

        // return InitialData.getInitialData(); // For demonstration, returning initial data directly
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
        onCreate(db);
    }

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    public String getDatabaseName() {
        return DATABASE_NAME;
    }
    public String getTableName() {
        return "person";
    }
    public int dbVersion() {
        return DATABASE_VERSION;
    }
    
    public enum PersonColumn {
        ID("id", "INTEGER"),
        NAME("name", "TEXT");

        private final String name;
        private final String type;

        PersonColumn(String columnName, String columnType) {
            this.name = columnName;
            this.type = columnType;
        }

        public String getName() {
            return this.name;
        }
        public String getType() {
            return this.type;
        }
    }

    public enum InitialData {
        PERSON_1(1, "Alice"),
        PERSON_2(2, "Bob"),
        PERSON_3(3, "Charlie");

        private final int id;
        private final String name;

        private InitialData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static List<Person> getInitialData() {
            List<Person> persons = new ArrayList<>();
            for (InitialData data : InitialData.values()) {
                persons.add(new Person(data.id, data.name));
            }
            return persons;
        }
    }
}
