package lr.aym.projet_fin_detudes.views.addPostScreen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import lr.aym.projet_fin_detudes.R

@Composable
fun OnDoneDialog(
    onDoneDialogState: State<OnDoneDialogState>,
    viewModel: AddPostViewModel
) {
    if (onDoneDialogState.value != OnDoneDialogState.Closed) {

        Dialog(
            onDismissRequest = {
                viewModel.onDialogDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSystemInDarkTheme()) MaterialTheme.colors.surface else Color(
                    0xFFD2D2D2
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {

                    when (val onDoneDialogStateValue = onDoneDialogState.value) {
                        is OnDoneDialogState.Closed -> Unit

                        is OnDoneDialogState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(80.dp),
                                color = MaterialTheme.colors.primary
                            )
                        }

                        is OnDoneDialogState.Failure -> onDoneDialogStateValue.apply {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_cancel_24),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                            Text(
                                text = errorMessage,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onBackground
                            )
                        }

                        is OnDoneDialogState.Success -> {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                contentDescription = null,
                                tint = Color(0xFF4DC92E),
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.post_submitted_successfully),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }

                }
            }
        }
    }

}

