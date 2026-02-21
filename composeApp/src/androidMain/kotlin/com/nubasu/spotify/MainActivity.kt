package com.nubasu.spotify.webapi.wrapper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val callbackUriState = mutableStateOf<String?>(null)

    /**
     * Executes onCreate.
     *
     * @param savedInstanceState The savedInstanceState parameter.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        callbackUriState.value = intent?.dataString

        setContent {
            App(callbackUriFromIntent = callbackUriState.value)
        }
    }

    /**
     * Executes onNewIntent.
     *
     * @param intent The intent parameter.
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        callbackUriState.value = intent.dataString
    }
}

/**
 * Executes AppAndroidPreview.
 */
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
