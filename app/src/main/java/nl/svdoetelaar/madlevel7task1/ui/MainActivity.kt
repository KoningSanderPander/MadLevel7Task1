package nl.svdoetelaar.madlevel7task1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.findNavController
import nl.svdoetelaar.madlevel7task1.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

        FirebaseFirestore.setLoggingEnabled(true)
        FirebaseApp.initializeApp(this)

    }


}