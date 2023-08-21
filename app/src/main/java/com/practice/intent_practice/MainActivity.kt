package com.practice.intent_practice

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.practice.intent_practice.ui.theme.IntentpracticeTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntentpracticeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    viewModel.uri?.let {
                        AsyncImage(model = it, contentDescription = "pic")
                    }


                    Button(onClick = {
                        // Explicit Intent #1. class in the same application

                        // Intent(context, class)
                        //Intent(applicationContext, SecondActivity::class.java).also {
                        //    startActivity(it)
                        //}

                        // Explicit Intent #2. to the another activity of specific application

                        // Intent(action)
                        // activity actions => what we want to do with this intent
                        // ACTION_MAIN : refers to "launch mainactivity"
                        // launch youtube app => receiver of this intent : youtubw
                        // Youtube app knows how to handle intent and what to do with this
                        // Intent(Intent.ACTION_MAIN).also {
                        //     // check if it is actually installed on the device...
                        //     // otherwise, if not installed, the app will crash
                        //     // for checking(resolveActivity()), manifest queries in AndroidManifest.xml
                        //     it.`package` = "com.google.android.youtube"
                        //     /*if (it.resolveActivity(packageManager) != null) {
                        //         startActivity(it)
                        //     }*/
                        //     try {
                        //         startActivity(it)
                        //     } catch (e: ActivityNotFoundException) {
                        //         e.printStackTrace()
                        //     }
                        // }

                        // Implicit Intent #1. email
                        // ACTION_SEND : https://developer.android.com/training/sharing/send?hl=ko
                        // 인텐트가 실행할 작업을 지정해야 합니다.
                        // Android에서는 ACTION_SEND 작업을 사용하여
                        // 프로세스 경계를 넘어서까지 하나의 활동에서 다른 활동으로 데이터를 보냅니다.
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            // mimetype => 데이터 및 데이터의 유형을 지정해야 합니다.
                            // 어떤 타입의 데이터를 다른 프로세스로 보낼지 결정해야 한다.
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "this is the subject")
                            putExtra(Intent.EXTRA_TEXT, "this is the content")
                        }

                        // Sharesheet를 사용.
                        // Android Sharesheet를 표시하려면 Intent.createChooser()를 호출하여
                        // Intent 객체에 전달해야 합니다.
                        // 그러면 Android Sharesheet를 항상 표시하는 인텐트의 버전을 반환합니다.
                        if (intent.resolveActivity(packageManager) != null) {
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                    }) {

                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // image uri
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        // use viewmodel to use in composable...
        viewModel.updateUri(uri)
    }
}
