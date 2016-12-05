package com.example.nipun.smarthomealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

import static java.security.AccessController.getContext;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button test;
    TextView displayName;
    TextView displayEmailId;
    NavigationView navigationView;
    private BroadcastReceiver broadcastReceiver;
    private FireBaseModel fireBaseModel;
    static List <GroceryList> gl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // Button button = (Button) findViewById(R.id.button2);
         setSupportActionBar(toolbar);

  //START SERVICE
        Intent i =new Intent(getApplicationContext(),ServiceLocation.class);
       // startService(i);


        if(!runtime_permissions()){


    }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FridgeFragment fm = new FridgeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_layout_for_fragment , fm,fm.getTag()).commit();



        //get instance and user

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //Log.d("Base Activity" , user.getDisplayName());

        //nav header name and email id
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        displayName = (TextView)header.findViewById(R.id.nav_name);
        displayEmailId = (TextView)header.findViewById(R.id.nav_email_id);
        if(user.getDisplayName() != null){displayName.setText(user.getDisplayName()); }

        displayEmailId.setText(user.getEmail());    SharedPreferences sharedPreferencesUid = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String userId = sharedPreferencesUid.getString("userId" , "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getInstance().getReference(userId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               FireBaseModel fbm = dataSnapshot.getValue(FireBaseModel.class);
                gl = fbm.getGroceryList();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });








    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }
        if(id == R.id.action_settings)
        {
            Log.d("Settings", "onOptionsItemSelected: ");
            // Start Activity
            Intent startSeetings = new Intent(BaseActivity.this ,SettingsActivity.class);
            BaseActivity.this.startActivity(startSeetings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d("Cmaera", "onNavigationItemSelected: ");
            FridgeFragment fm = new FridgeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_layout_for_fragment , fm,fm.getTag()).commit();

        } else if (id == R.id.nav_gallery) {
            Log.d("gallery", "onNavigationItemSelected: ");
            Log.d("Base Act", "onNavigationItemSelected: smart recipies ");
            EnterIngredientsFragment enterIngredientsFragment = new EnterIngredientsFragment();
            FragmentManager fragmentManager =  getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_layout_for_fragment , enterIngredientsFragment, enterIngredientsFragment.getTag()).commit();
        } else if (id == R.id.nav_groceryList){
            GroceryListFragment groceryListFragment = new GroceryListFragment();
            FragmentManager fragmentManager =  getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.base_layout_for_fragment , groceryListFragment, groceryListFragment.getTag()).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        Log.d("base act sign out check" , "SIGNED OUT 1");
        FirebaseAuth.getInstance().signOut();
        Intent startLogin = new Intent(BaseActivity.this , Login.class);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Signing Out", Toast.LENGTH_SHORT);
        toast.show();
        BaseActivity.this.startActivity(startLogin);
        Toast tost2 = Toast.makeText(context,"Signed Out Successfully" , Toast.LENGTH_SHORT );
        tost2.show();
    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

            }else {
                runtime_permissions();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                   // Log.d(intent.getExtras().get("coordinates").toString(), "onReceive: ");
                }
            };
        }
        //registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }


    //get shared pref



}
