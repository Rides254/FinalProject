package com.example.firebasestorage.screens.products


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.firebasestorage.data.ProductViewModel
import com.example.firebasestorage.models.Product

import com.example.firebasestorage.ui.theme.FirebasestorageTheme
import com.example.firebasestorage.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProductsScreen(navController:NavHostController) {
    var mContext = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {


        var productRepository = ProductViewModel(navController, mContext)


        val emptyProductState = remember { mutableStateOf(Product("","","","","")) }
        var emptyProductsListState = remember { mutableStateListOf<Product>() }

        var products = productRepository.allProducts(emptyProductState, emptyProductsListState)


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(
                title = { Text("Househelp Information") },
                colors = TopAppBarDefaults.largeTopAppBarColors(blue),
                navigationIcon = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Menu, null)
                    }
                })


            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(){
                items(products){
                    ProductItem(
                        name = it.name,
                        quantity = it.quantity,
                        price = it.price,
                        id = it.id,
                        navController = navController,
                        productRepository = productRepository,
                        productImage = it.imageUrl
                    )
                }
            }
        }
    }
}


@Composable
fun ProductItem(name:String, quantity:String, price:String, id:String,
                navController:NavHostController,
                productRepository:ProductViewModel, productImage:String) {
    val mContext= LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = rememberAsyncImagePainter(productImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Row(modifier = Modifier.padding(start = 80.dp)) {
            Text(text = "Employee Full Name :", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = name)
        }

        Row(modifier = Modifier.padding(start = 80.dp)) {
            Text(text = "Phone Number :",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
            Text(text = quantity)
        }
        Row(modifier = Modifier.padding(start = 80.dp)) {
            Text(text = "Preferred Town :", fontWeight = FontWeight.Bold,fontSize = 20.sp)
            Text(text = price)
        }

        Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            Button(onClick = {
                productRepository.deleteProduct(id)
            },shape = RoundedCornerShape(5.dp)  ,
                colors = ButtonDefaults.buttonColors(blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)) {
                Text(text = "Delete")
                
                Spacer(modifier = Modifier.height(20.dp))
            }
        }





    }
}

@Composable
@Preview(showBackground = true)
fun ViewProductsScreenPreview(){

    ViewProductsScreen(navController = rememberNavController())

}