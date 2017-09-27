package pl.morawskim.retrofit;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RepoArrayAdapter extends ArrayAdapter<Repo>
{
    private final Context context;
    private final Repo[] values;

    public RepoArrayAdapter(Context context, Repo[] values)
    {
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView textViewId = (TextView) rowView.findViewById(R.id.repo_id);
        TextView textViewName = (TextView) rowView.findViewById(R.id.repo_name);
        textViewId.setText(values[position].id);
        textViewName.setText(values[position].name);
        return rowView;
    }
}
