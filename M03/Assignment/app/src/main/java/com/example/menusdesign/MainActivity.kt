package com.example.menusdesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)

        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when(menuItem.itemId) {

                R.id.nav_category_first -> { Toast.makeText(this, "First", Toast.LENGTH_LONG).show() }
                R.id.nav_category_second -> { Toast.makeText(this, "Second", Toast.LENGTH_LONG).show() }
                R.id.nav_category_third -> { Toast.makeText(this, "Third", Toast.LENGTH_LONG).show() }
                R.id.nav_category_start_activity -> { startActivity(Intent(this, SecondActivity::class.java))}
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.menu_toast -> {
                Toast.makeText(this, "Options Menu Toast", Toast.LENGTH_LONG).show()
            }
            R.id.menu_text -> {
                tv_main.text = "Text was Changed"
            }
            R.id.menu_background -> {
                constraint_parent.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
