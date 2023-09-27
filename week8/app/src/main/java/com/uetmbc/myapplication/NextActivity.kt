package com.uetmbc.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.uetmbc.myapplication.databinding.ActivityNextBinding

class NextActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNextBinding

    private var fullName: String = "";
    private var message: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_next)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        var intent = intent;
        this.fullName = intent?.getStringExtra("fullName").toString();
        this.message = intent?.getStringExtra("message").toString();
    }

    override fun finish()
    {
        var intent = Intent()
        val feedback = "OK, Hello " + this.fullName + ". How are you?";

        intent.putExtra("feedback", feedback);
        setResult(Activity.RESULT_OK, intent);

        super.finish();
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_next)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}