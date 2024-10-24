package com.mun.smartcalendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun UserScreen(context: Context,
                navController: NavController,
                modifier: Modifier = Modifier) {
    val googleSigninClient: GoogleSignInClient by lazy { getGoogleClient(context) }

    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Button(onClick = { googleSigninClient.signOut().addOnCompleteListener {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)

            if (context is Activity) {
                context.finish()
            }
        } }) {
            Text(text = "LogOut")
        }
    }
}

private fun getGoogleClient(context: Context): GoogleSignInClient {
    val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(context.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, googleSignInOption)
}