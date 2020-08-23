package com.tindercarousel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tindercarousel.adapter.FavoritesAdapter;
import com.tindercarousel.adapter.ProfilesAdapter;
import com.tindercarousel.database.DatabaseHelper;
import com.tindercarousel.database.User;
import com.tindercarousel.network.RetrofitClientInstance;
import com.tindercarousel.network.TinderApi;
import com.tindercarousel.pojo.FavoritesModel;
import com.tindercarousel.pojo.UserData;
import com.tindercarousel.utils.DbTask;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    private CardStackView stackView;
    private Button buttonFavorite;
    private int position;
    List<FavoritesModel> favList = new ArrayList<>();
    List<UserData.Results> users = new ArrayList<>();
    ProfilesAdapter adapter;
    CardStackLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initializing Views
        buttonFavorite = findViewById(R.id.buttonFavorite);
        stackView = findViewById(R.id.stack_view);
        layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setOverlayInterpolator(new LinearInterpolator());
        layoutManager.setSwipeableMethod(SwipeableMethod.Manual);
        stackView.setLayoutManager(layoutManager);
        adapter = new ProfilesAdapter(MainActivity.this, users);
        stackView.setAdapter(adapter);

        //Database Instance
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(MainActivity.this);


        //Getting Data from api
        final TinderApi instance = RetrofitClientInstance.getRetrofitInstance().create(TinderApi.class);
        Call<JsonObject> call = instance.getData();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                UserData data = new Gson().fromJson(response.body().toString(), UserData.class);
                users.add(data.getResults().get(0));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        //Onclick listener to view profiles marked as favorite by swiping right
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("Favorites");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        favList.addAll(databaseHelper.userDAO().getUsers());
                    }
                });
                if(favList.size()>0) {
                    FavoritesAdapter favAdapter = new FavoritesAdapter(MainActivity.this, favList);
                    stackView.setAdapter(favAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "You haven't marked anyone as favorite", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Tinder Carousel");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }


            }
        });
    }

    @Override
    public void setTurnScreenOn(boolean turnScreenOn) {
        super.setTurnScreenOn(turnScreenOn);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            position = layoutManager.getTopPosition() - 1; //Top position is the card visible so -1 to get swiped card position in list
            User data = new User(users.get(position).getUser().getName().getFirst() + " " + users.get(position).getUser().getName().getLast(),
                    users.get(position).getUser().getLocation().getStreet(),
                    users.get(position).getUser().getDob(),
                    users.get(position).getUser().getPicture(),
                    users.get(position).getUser().getPhone(),
                    users.get(position).getUser().getUsername());
            //Async task to insert favorite profile into local database
            DbTask task = new DbTask(MainActivity.this, data, MainActivity.this);
            task.execute();
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }


    @Override
    public void onCardAppeared(View view, int position) {
        //get next data
        getRandomData();
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        //get next data
        getRandomData();
    }

    //Method to call api in order to get user data
    public void getRandomData() {
        //Retrofit Instance
        TinderApi instance = RetrofitClientInstance.getRetrofitInstance().create(TinderApi.class);
        Call<JsonObject> call = instance.getData();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    UserData data = new Gson().fromJson(response.body().toString(), UserData.class);
                    users.add(data.getResults().get(0));
                    // notifyDatasetChanged will not work here as it will set the adapter again
                    // so we will always be getting first user's card
                    if (layoutManager.getTopPosition() < users.size()) {
                        adapter.notifyItemRangeInserted(users.size()-1, 1);
                    }

                } catch (NullPointerException e){
                    Log.d("Exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportActionBar().setTitle("Tinder Carousel");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            stackView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
