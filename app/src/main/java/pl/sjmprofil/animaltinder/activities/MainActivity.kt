package pl.sjmprofil.animaltinder.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

import pl.sjmprofil.animaltinder.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main_activity)

        setupNavigationUI()
    }

    private fun setupNavigationUI() {
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(nav_view_main_activity, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout_main_activity)
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(navController, drawer_layout_main_activity)
}