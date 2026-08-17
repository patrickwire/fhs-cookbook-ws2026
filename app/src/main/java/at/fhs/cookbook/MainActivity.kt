package at.fhs.cookbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.fhs.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Log.d("LIFECYCLE", "onCreate")      // Logcat-Filter: LIFECYCLE
        setContent {
            CookBookTheme {
                Greeting()
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

// Der Cliffhanger von Tag 1: dieser Zähler wohnt in der Activity — Drehen = weg.
// (Auflösung in Block 3: State + remember, und in E13 das ViewModel.)
var zaehlerDemo = 0

@Composable
fun Greeting() {
    Text("Hallo FH Salzburg")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CookBookTheme { Greeting() }
}
