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
import com.mun.smartcalendar.ui.theme.LoginBackGround
import com.mun.smartcalendar.ui.theme.Navy
import com.mun.smartcalendar.ui.theme.SmartCalendarTheme


class LoginActivity : ComponentActivity() {

    private val googleSigninClient: GoogleSignInClient by lazy { getGoogleClient() }

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
            .requestServerAuthCode(getString(R.string.web_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
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

            //파이어베이스 서버에 저장을 해야함
            //그리고 로그인 되어있으면 자동으로 메인으로 넘어가야함 이건 Splash에서 처리
            val email = account?.email.toString()
            val displayName = account?.displayName.toString()
            Log.d("success", "signInResult:$email / $displayName")
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ApiException){
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }
}
