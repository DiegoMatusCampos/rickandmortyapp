package com.example.rickandmorty.presentation.character_list.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.ui.theme.RickAndMortyTheme



@Composable
fun CharacterListItem(
    onClickItem: () -> Unit,
    character: Character,
    modifier: Modifier = Modifier,

    ) {

    Row(

        modifier = Modifier
            .clickable(enabled = true , onClick = onClickItem)
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp))
            .heightIn(
                min = 150.dp, max = 150.dp
            )

    )
    {

        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()

        ) {

            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)

            )
        }

    }

}

@Preview
@Composable
private fun CharacterListItemPreview() {
    RickAndMortyTheme {
        CharacterListItem(
            character = Character(
                name = "Toxic Rick",
                status = "Dead",
                image = "",
                specie = "Humanoid",
                gender = "Male",
                origin = "Tierra",
                lastLocation = "Tierra",
                episodes = listOf(1,2,3),
            ),
            onClickItem = {

            }
        )
    }
}