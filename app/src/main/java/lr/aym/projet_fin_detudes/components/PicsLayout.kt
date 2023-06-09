package lr.aym.projet_fin_detudes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PicsLayout(imagesUrls : List<String>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    when(imagesUrls.size){
        5 ->{
            //general container for 5 Pics
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().height(screenWidth.dp)
            ) {
                //upper row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    AsyncImage(
                        model = imagesUrls[0],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.6f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[1],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp),
                    color = MaterialTheme.colors.background
                )
                //lower Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = imagesUrls[2],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[3],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[4],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
        4 ->{
            //general container for 4 Pics
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().height(screenWidth.dp)
            ) {
                //upper row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    AsyncImage(
                        model = imagesUrls[0],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp),
                    color = MaterialTheme.colors.background
                )
                //lower Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = imagesUrls[1],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[2],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[3],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
        3 ->{
            //general container for 3 Pics
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().height(screenWidth.dp)
            ) {
                //upper row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    AsyncImage(
                        model = imagesUrls[0],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp),
                    color = MaterialTheme.colors.background
                )
                //lower Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = imagesUrls[1],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f),
                        contentScale = ContentScale.Crop
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colors.background
                    )
                    AsyncImage(
                        model = imagesUrls[2],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
        2 ->{
            //general container for 2 Pics
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().height(screenWidth.dp)
            ) {
                //upper row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    AsyncImage(
                        model = imagesUrls[0],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp),
                    color = MaterialTheme.colors.background
                )
                //lower Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    AsyncImage(
                        model = imagesUrls[1],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
        1 ->{
            //general container for 1 Pic
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().height(screenWidth.dp)
            ) {
                AsyncImage(
                    model = imagesUrls[0],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }











}