package com.example.materialdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val item1 = PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings)

        DrawerBuilder()
            .withActivity(this)
            .withTranslucentStatusBar(false)
            .withActionBarDrawerToggle(false)
            .addDrawerItems(item1, item2)
            .build()
    }
}
