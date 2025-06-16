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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.character_detail.components.SectionInformation
import com.example.rickandmorty.presentation.character_list.components.LoadingScreen
import com.example.rickandmorty.presentation.navigation.TopAppBar
import com.example.rickandmorty.presentation.util.ObserverAsEvents
import com.example.rickandmorty.ui.theme.AliveGreen
import com.example.rickandmorty.ui.theme.DarkBackground
import com.example.rickandmorty.ui.theme.NeonGreen
import com.example.rickandmorty.ui.theme.RedDead
import com.example.rickandmorty.ui.theme.UnknownGrey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun DetailCharacterScreen(
    onAction: (DetailCharacterListAction) -> Unit,
    uiState: DetailCharacterUiState,
    events: Flow<DetailCharacterEvents>,
    modifier: Modifier = Modifier
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    ObserverAsEvents(
        events
    ) { event ->

        when (event) {
            is DetailCharacterEvents.Error -> {

                scope.launch {
                    SnackbarHostState().showSnackbar(event.message.name)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.detail),
                onBack = {
                    onAction(DetailCharacterListAction.onNavigateUp)
                },
                canNavigateUp = true,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Column(
                modifier = modifier
                    .padding(top = 16.dp)
                    .then(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    )
            ) {

                val colorBadge = when (uiState.selectedCharacter?.status) {
                    "Dead" -> RedDead
                    "Alive" -> AliveGreen
                    else -> UnknownGrey
                }
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiState.selectedCharacter?.image)
                        .crossfade(true)
                        .build(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = uiState.selectedCharacter?.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f, true)
                        .clip(RoundedCornerShape(32.dp))
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = uiState.selectedCharacter?.name ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = NeonGreen,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = uiState.selectedCharacter?.specie ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(64.dp)
                            )
                            .padding(horizontal = 16.dp)


                    )
                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = uiState.selectedCharacter?.status ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Light,
                        color = DarkBackground,
                        modifier = Modifier
                            .background(colorBadge, RoundedCornerShape(64.dp))
                            .padding(horizontal = 16.dp)
                    )


                }


                Spacer(modifier = Modifier.height(32.dp))


                SectionInformation(
                    title = stringResource(R.string.gender),
                    value = uiState.selectedCharacter?.gender ?: ""
                )

                Spacer(modifier = Modifier.height(32.dp))

                SectionInformation(
                    title = stringResource(R.string.origen),
                    value = uiState.selectedCharacter?.origin ?: ""
                )

                Spacer(modifier = Modifier.height(32.dp))

                SectionInformation(
                    title = stringResource(R.string.last_location),
                    value = uiState.selectedCharacter?.lastLocation ?: ""
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        onAction(
                            DetailCharacterListAction.onClickShowEpisodes(
                                uiState.selectedCharacter?.episodes ?: listOf()
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.episodes_button),
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

            }
        }


    }
}