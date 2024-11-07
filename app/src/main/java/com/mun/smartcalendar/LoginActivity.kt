package com.mun.smartcalendar

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.mun.smartcalendar.ui.theme.LoginBackGround
import com.mun.smartcalendar.ui.theme.Navy
import com.mun.smartcalendar.ui.theme.SmartCalendarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val googleSigninClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCalendarTheme {
                LoginScreen()
            }
        }
    }

    @Composable
    fun LoginScreen() {
        //상태바 색상 설정
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(color = LoginBackGround)

        Scaffold(
            modifier = Modifier
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(LoginBackGround),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = getString(R.string.app_name),
                    color = Navy,
                    fontSize = 40.sp)
                IconButton(onClick = { requestGoogleLogin() },
                    modifier = Modifier.size(240.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.android_light_rd_si), // drawable 리소스
                        contentDescription = null,
                    )
                }
            }
        }
    }

    private fun requestGoogleLogin() {
        googleSigninClient.signOut()
        val signInIntent = googleSigninClient.signInIntent

        resultLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
            .requestEmail() // 이메일 요청
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken

            //Google 계정을 Firebase 인증에 연결
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        val email = firebaseUser?.email ?: "No Email"
                        val name = firebaseUser?.displayName ?: "No Name"
                        saveUserToFireStore(email, name, firebaseUser?.uid?: "")

                        Log.d("Auth Success", "signInWithCredential:success - $email, $name")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Log.w("Auth Failed", "signInWithCredential:failure", authTask.exception)
                    }
                }
        } catch (e: ApiException){
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun saveUserToFireStore(email: String, name: String, userId: String) {
        val user = hashMapOf(
            "email" to email,
            "name" to name,
            "timestamp" to System.currentTimeMillis()
        )

        //users로 컬렉션을 만들고 그 아래에 uid로 document Id를 사용하여 저장(폴더명이 각 유저별 uid라고 생각하면됨)
        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("firestore success", "User saved SuccessFully.")
            }
            .addOnFailureListener { e ->
                Log.d("firestore Failed", "Error saving User : $e")
            }
    }

    override fun onStart() {
        super.onStart()
        val account = this.let { GoogleSignIn.getLastSignedInAccount(it) }

        if (account != null) { //null을 반환하면 아직 로그인 전이기 때문. null이 아니라면 로그인 한 상태
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
