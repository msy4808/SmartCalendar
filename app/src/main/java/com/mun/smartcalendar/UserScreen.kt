package com.mun.smartcalendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mun.smartcalendar.ui.theme.LightGray
import com.mun.smartcalendar.ui.theme.LoginBackGround
import com.mun.smartcalendar.ui.theme.Navy
import com.mun.smartcalendar.ui.theme.UltraLightGray

@Composable
fun UserScreen(
    context: Context,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val googleSigninClient: GoogleSignInClient by lazy { getGoogleClient(context) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White)
    val list: List<String> = List(10) { "$it" }

    fetchCurrentUserDetails()

    Column(modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text(
                    text = MainActivity.userName,
                    modifier = modifier.padding(8.dp)
                )
                Spacer(modifier = modifier.weight(1f))
                Button(
                    onClick = {
                        googleSigninClient.signOut().addOnCompleteListener {
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)

                            if (context is Activity) {
                                context.finish()
                            }
                        }
                    },
                    colors = ButtonColors(LoginBackGround, Navy, LightGray, LightGray)
                ) {
                    Text(text = "LogOut")
                }
            }
        }
        Divider(
            color = UltraLightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(list) {items ->
                Text(text = "Hello! Test List $items",
                    color = Navy)
            }
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

private fun fetchCurrentUserDetails() {
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser

    if (currentUser != null) {
        val userId = currentUser.uid
        Log.d("fetchCurrentUserDetails UserId", " : $userId")

        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    MainActivity.userEmail = document.getString("email") ?: "No email"
                    MainActivity.userName = document.getString("name") ?: "No Name"

                } else {
                    Log.d("userInfo not exist", "User document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.d("userInfo Failed", "Error : $e")
            }
    } else {
        Log.d("userInfo Null", "currentUser is Null")
    }
}


@Preview
@Composable
fun SimpleComposablePreview() {
    UserScreen(LocalContext.current, rememberNavController())
}