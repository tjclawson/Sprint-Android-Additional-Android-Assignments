package com.example.menusdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val toolbar = findViewById<View>(R.id.second_toolbar) as androidx.appcompat.widget.Toolbar

        val item1 = PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_first)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_second)

        val result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            //.addDrawerItems(item1, DividerDrawerItem(), item2)
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener {
                override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                    return false
                    }
                }
            )
            .build()
    }
}
