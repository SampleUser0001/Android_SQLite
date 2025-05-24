package ittimfn.android.sqlite;

import java.util.List;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import ittimfn.android.sqlite.adapter.PersonAdapter;
import ittimfn.android.sqlite.helper.PersonHelper;
import ittimfn.android.sqlite.recode.Person;

public class MainActivity extends AppCompatActivity {

    private PersonHelper personHelper;
    private ListView personListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.personHelper = new PersonHelper(this);
        this.personListView = findViewById(R.id.person_list_view);

        loadDataAndDisplay();
    }

    private void loadDataAndDisplay() {
        List<Person> persons = personHelper.getAll();
        PersonAdapter adapter = new PersonAdapter(this, persons);
        this.personListView.setAdapter(adapter);
    }
}