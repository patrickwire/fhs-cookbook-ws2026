package at.fhs.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.fhs.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContent {
            CookBookTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    Text("Hallo FH Salzburg")   // Tag-1-Live-Coding: aus "Hello World!" wurde das hier
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CookBookTheme { Greeting() }
}
