package com.example.rickandmorty.presentation.episode_list.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.domain.model.Episode
import com.example.rickandmorty.ui.theme.NeonGreen
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun EpisodeListItem(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2E3A)),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.
                padding(top = 16.dp).
            padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleLarge,
                color = NeonGreen
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Episodio ${episode.episodeNumber}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    )
                )

                Text(
                    text = "Temporada ${episode.seasonNumber}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    ),
                    modifier= Modifier.weight(1f)
                )

                Text(
                    text = episode.airDate,
                    style = MaterialTheme.typography.titleSmall,

                )
            }
        }
    }
}

@Preview
@Composable
private fun EpisodeListItemPreview() {
    RickAndMortyTheme {
        EpisodeListItem(
            episode = Episode(
                id = 1,
                name = "Prueba Episodio",
                airDate = "December 3, 2024",
                episodeNumber = 1,
                seasonNumber = 1
            )
        )
    }
}