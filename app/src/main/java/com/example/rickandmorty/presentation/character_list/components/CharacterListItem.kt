package com.example.rickandmorty.presentation.character_list.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.ui.theme.AliveGreen
import com.example.rickandmorty.ui.theme.GreyText
import com.example.rickandmorty.ui.theme.NeonGreen
import com.example.rickandmorty.ui.theme.RedDead
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.UnknownGrey


@Composable
fun CharacterListItem(
    onClickItem: () -> Unit,
    character: Character,
    modifier: Modifier = Modifier,

    ) {

    val statusColor = when (character.status.uppercase()) {
        "ALIVE" -> AliveGreen
        "DEAD" -> RedDead
        else -> UnknownGrey
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2E3A)),
        onClick = {
            onClickItem()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.padding(horizontal = 26.dp)
            ) {
                Text(
                    character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = NeonGreen
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(

                ) {
                    Text(
                        stringResource(R.string.specie),
                        style = MaterialTheme.typography.bodyLarge, color = GreyText
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        character.specie,
                        style = MaterialTheme.typography.bodyLarge, color = GreyText
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(

                ) {
                    Text(
                        stringResource(R.string.status),
                        style = MaterialTheme.typography.bodyLarge, color = GreyText
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        character.status,
                        style = MaterialTheme.typography.bodyLarge, color = statusColor
                    )
                }
            }
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