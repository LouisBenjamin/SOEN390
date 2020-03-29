package com.conupods.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        Button done = findViewById(R.id.done2);
        Button personalPage = findViewById(R.id.toggle2_1);
        Button infoPage = findViewById(R.id.toggle2_2);

        CheckBox metro = findViewById(R.id.metro);
        CheckBox train = findViewById(R.id.train);
        CheckBox bus = findViewById(R.id.bus);
        CheckBox concordiaShuttle = findViewById(R.id.concordiaShuttle);

        CheckBox elevators = findViewById(R.id.elevators);
        CheckBox escalators = findViewById(R.id.escalators);
        CheckBox stairs = findViewById(R.id.stairs);
        CheckBox accessibilityInfo = findViewById(R.id.accessibilityInfo);
        CheckBox stepFreeTrips = findViewById(R.id.stepFreeTrips);

        setCheckedBoxes();

        //Top Menu Button

        done.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });
        personalPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsActivity.this, SettingsPersonalActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });
        infoPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsActivity.this, SettingsInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });

        //My transit options

        metro.setOnClickListener(view -> changePreferences(metro));
        train.setOnClickListener(view -> changePreferences(train));
        bus.setOnClickListener(view -> changePreferences(bus));
        concordiaShuttle.setOnClickListener(view -> changePreferences(concordiaShuttle));

        //My indoor options

        elevators.setOnClickListener(view -> changePreferences(elevators));
        escalators.setOnClickListener(view -> changePreferences(escalators));
        stairs.setOnClickListener(view -> changePreferences(stairs));
        accessibilityInfo.setOnClickListener(view -> changePreferences(accessibilityInfo));
        stepFreeTrips.setOnClickListener(view -> changePreferences(stepFreeTrips));
    }


    protected void changePreferences(CheckBox preference) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Editor prefEdit = preferences.edit();

        if (preference.isChecked()) {
            prefEdit.putBoolean(String.valueOf(preference.getId()), true).apply();
        }
        else {
            prefEdit.putBoolean(String.valueOf(preference.getId()), false).apply();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        setCheckedBoxes();
    }


    protected void setCheckedBoxes() {

        checkBoxIfInPreference(findViewById(R.id.metro));
        checkBoxIfInPreference(findViewById(R.id.train));
        checkBoxIfInPreference(findViewById(R.id.bus));
        checkBoxIfInPreference(findViewById(R.id.concordiaShuttle));
        checkBoxIfInPreference(findViewById(R.id.elevators));
        checkBoxIfInPreference(findViewById(R.id.escalators));
        checkBoxIfInPreference(findViewById(R.id.stairs));
        checkBoxIfInPreference(findViewById(R.id.accessibilityInfo));
        checkBoxIfInPreference(findViewById(R.id.stepFreeTrips));
    }


    protected void checkBoxIfInPreference(CheckBox preference) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        if (preferences.getBoolean(String.valueOf(preference.getId()), false)) {
            preference.setChecked(true);
        }
    }
}
