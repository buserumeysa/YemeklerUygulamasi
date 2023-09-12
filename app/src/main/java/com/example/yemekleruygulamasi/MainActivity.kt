package com.example.yemekleruygulamasi

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yemekleruygulamasi.ui.theme.YemeklerUygulamasiTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SayfaGecisleri()                 }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YemeklerUygulamasiTheme {
       SayfaGecisleri()
    }
}
@Composable
fun SayfaGecisleri() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa"){
        composable("anasayfa"){
            Anasayfa(navController = navController)
        }
        composable("detaySayfa/{yemek}", arguments = listOf(
            navArgument("yemek"){type= NavType.StringType}
        )){
            val json=it.arguments?.getString("yemek")
            val yemek =Gson().fromJson(json,Yemekler::class.java)
            DetaySayfa(yemek = yemek)
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController) {
    val yemekListesi = remember { mutableStateListOf<Yemekler>() }
    LaunchedEffect(key1 = true) {
        val y1 = Yemekler(1, "İçli Köfte", "iclikofte", 25)
        val y2 = Yemekler(2, "Lahmacun", "lahmacun", 27)
        val y3 = Yemekler(3, "Soda", "soda", 10)
        val y4 = Yemekler(4, "Ayran", "ayran", 8)
        val y5 = Yemekler(5, "Pepsi", "ppsi", 24)
        yemekListesi.add(y1)
        yemekListesi.add(y2)
        yemekListesi.add(y3)
        yemekListesi.add(y4)
        yemekListesi.add(y5)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Yemekler") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = colorResource(id = R.color.anaRenk),
                titleContentColor = colorResource(id = R.color.white)
            ),
        )
    },
        content = {

            LazyColumn {
                items(
                    count = yemekListesi.count(),
                    itemContent = {
                        val yemek = yemekListesi[it]
                        Card(
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .fillMaxWidth()) {
                            Row(modifier = Modifier.clickable {
                                val yemekJson=Gson().toJson(yemek)
                                navController.navigate("detaySayfa/$yemekJson")
                            }) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(all = 10.dp)
                                        .fillMaxWidth()
                                ) {
                                    //Aşağıdaki kod sayesinde resimlere isimleriyle ulaşmamız sağlanır
                                    val activity = (LocalContext.current as Activity)
                                    Image(
                                        bitmap = ImageBitmap.imageResource(
                                            id = activity.resources.getIdentifier(
                                                yemek.yemekResmi,
                                                "drawable",
                                                activity.packageName
                                            )
                                        ), contentDescription = "", modifier = Modifier.size(100.dp)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()) {
                                            Text(text = yemek.yemekAdi, fontSize = 25.sp)
                                            Spacer(modifier = Modifier.size(30.dp))
                                            Text(text = "${yemek.yemekFiyati}  ₺ ", color = Color.Blue)
                                            
                                        }
                                        Icon(painter = painterResource(id = R.drawable.arrow), contentDescription ="" )

                                    }
                                }

                            }

                        }
                    })
            }

        })

}

