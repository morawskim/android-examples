package pl.morawskim.retrofit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ListView listView = (ListView) findViewById(R.id.listview);

        // Create an instance of our GitHub API interface.
        GitHubService github = retrofit.create(GitHubService.class);
        Call<List<Repo>> call = github.listRepos("morawskim");

        final Context c = this;
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful()) {

                    List<Repo> list = response.body();
                    RepoArrayAdapter adapter = new RepoArrayAdapter(
                            c,
                            list.toArray(new Repo[] {})
                    );
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(c, "Failure during download repositories", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(c, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
