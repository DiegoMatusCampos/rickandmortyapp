package com.example.rickandmorty.presentation.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.character_detail.components.SectionInformation
import com.example.rickandmorty.presentation.character_list.CharacterUiState
import java.nio.file.WatchEvent

@Composable
fun DetailCharacterScreen(
    uiState: CharacterUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        )
    ) {

        val colorBadge = when(uiState.selectedCharacter?.status){
                "Dead" -> Color.Red
                "Alive" -> Color.Green
            else -> Color.Yellow
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = uiState.selectedCharacter?.name?:"",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )

                Text(
                    text = uiState.selectedCharacter?.specie?:"",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight =  FontWeight.Light,
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(64.dp))
                        .padding(horizontal = 16.dp)


                )
                Spacer(Modifier.width(8.dp))

                Text(
                    text = uiState.selectedCharacter?.status?:"",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .background(colorBadge, RoundedCornerShape(64.dp))
                        .padding(horizontal = 16.dp)
                )


            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uiState.selectedCharacter?.image)
                .crossfade(true)
                .build(),
            contentDescription = uiState.selectedCharacter?.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f,true)
                .clip(RoundedCornerShape(32.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionInformation(
            title = stringResource(R.string.gender),
            value = uiState.selectedCharacter?.gender?: ""
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionInformation(
            title = stringResource(R.string.origen),
            value = uiState.selectedCharacter?.origin?: ""
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionInformation(
            title = stringResource(R.string.last_location),
            value = uiState.selectedCharacter?.lastLocation?: ""
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 64.dp)
        ) {
            Text(
                text = stringResource(R.string.episodes_button),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

    }
}