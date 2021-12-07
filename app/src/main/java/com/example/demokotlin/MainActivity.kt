package com.example.demokotlin

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.demokotlin.databinding.ActivityMainBinding
import com.example.demokotlin.fragments.dashboard.DashboardFragment
import com.example.demokotlin.fragments.home.HomeFragment
import com.example.demokotlin.fragments.profile.ProfileDialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Initialise the DrawerLayout, NavigationView and ToggleBar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController
    // lateinit logout firebase auth
    lateinit var auth: FirebaseAuth

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser

        //check current user authentication
        if(currentUser==null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Call findViewById on the DrawerLayout
        drawerLayout = findViewById(R.id.main_drawer_layout)


        // Call findViewById on the NavigationView
        //navView = findViewById(R.id.nav_view)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
       // navigationView.setNavigationItemSelectedListener(this)

      /*  val navController = this.findNavController(R.id.Frag_Container)
        NavigationUI.setupActionBarWithNavController(this, navController)*/

        navController = findNavController(this@MainActivity,
            R.id.Frag_Container
        )

        //Google signIn option
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        //navigation handler
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        setSupportActionBar(toolbar)
        val toggle = object : ActionBarDrawerToggle(
            this, drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {}

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

       // changeFragment(HomeFragment())
        //Bottom Bar navigation Drawer Action Perform
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    changeFragment(HomeFragment())
                }
                R.id.nav_dashboard -> {
                    changeFragment(DashboardFragment())
                }
                R.id.nav_profile -> {
                    changeFragment(ProfileDialogFragment())
                }
            }
            true
        }
    }

    //Bottom navigation drawer fragments function
   /* private fun inflateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Frag_Container, newInstance)
        transaction.commit()
    }*/


    //
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.home -> {
                changeFragment(HomeFragment())
            }
            R.id.dashboard -> {
                changeFragment(DashboardFragment())
            }
            R.id.profile -> {
                changeFragment(ProfileDialogFragment())
            }
            R.id.settings -> {

            }
            R.id.logout ->{

                auth.signOut()
                startActivity(Intent(this,SingupActivity::class.java))
                Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show()
                finish()

                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent= Intent(this, LoginActivity::class.java)
                    Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


   fun changeFragment(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.Frag_Container,frag).commit()

    }


  /*  override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
*/
    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this,
            R.id.Frag_Container
        ).navigateUp() || super.onSupportNavigateUp()
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}




