package ittimfn.android.sqlite.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ittimfn.android.sqlite.recode.Person;

import ittimfn.android.sqlite.R;
import android.widget.Toast;

/**
 * SQLiteHelperとViewを紐付ける。
 * 
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    private final LayoutInflater inflater;
    
    private final Context context;
    private final List<Person> persons;

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, R.layout.person_item, persons);
        this.inflater = LayoutInflater.from(context);

        this.context = context;
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Viewの再利用
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.person_item, parent, false);
        }

        Toast.makeText(context, "getView: " + position, Toast.LENGTH_SHORT).show();

        Person person = this.persons.get(position);

        TextView idView = convertView.findViewById(R.id.person_id);
        idView.setText(String.valueOf(person.id()));

        TextView nameView = convertView.findViewById(R.id.person_name);
        nameView.setText(person.name());

        return convertView;
    }
    
}
