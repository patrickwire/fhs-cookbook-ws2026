package at.fhs.cookbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import at.fhs.cookbook.ui.CookBookScreen
import at.fhs.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Log.d("LIFECYCLE", "onCreate")      // Logcat-Filter: LIFECYCLE
        setContent {
            CookBookTheme {
                CookBookScreen()
            }
        }
    }

    // Live-Coding E4: App drehen und die Reihenfolge im Logcat mitlesen.
    override fun onStart()   { super.onStart();   Log.d("LIFECYCLE", "onStart") }
    override fun onResume()  { super.onResume();  Log.d("LIFECYCLE", "onResume") }
    override fun onPause()   { super.onPause();   Log.d("LIFECYCLE", "onPause") }
    override fun onStop()    { super.onStop();    Log.d("LIFECYCLE", "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("LIFECYCLE", "onDestroy") }
}
