package lr.aym.projet_fin_detudes.views.additionalSignInInfo

import androidx.compose.runtime.Composable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import lr.aym.projet_fin_detudes.components.LoadingTextButton
import lr.aym.projet_fin_detudes.model.emailPassword.AddToFirestoreResponse
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdditionalSignInInfo(
    viewModel: AdditionalSignUpInfoViewModel = hiltViewModel(),
    navController: NavController
) {

    val calendarState = rememberSheetState()
    MyCalendar(calendarState = calendarState, date = viewModel.dateOfBirth)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxHeight()
                .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary,

                    )
            }


            Text(
                text = "Username :",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,

                )

            OutlinedTextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it },
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(text = " User Name ", color = MaterialTheme.colors.onSecondary)
                },
                leadingIcon = {
                    Icon(Icons.Filled.Person, null, tint = MaterialTheme.colors.primary)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                ),
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Phone Number :",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )

            OutlinedTextField(
                value = viewModel.phoneNumber.value,
                shape = RoundedCornerShape(15.dp),
                onValueChange = { viewModel.phoneNumber.value = it },
                label = { Text("Phone Number", color = MaterialTheme.colors.onSecondary) },
                leadingIcon = {
                    Icon(Icons.Filled.Phone, null, tint = MaterialTheme.colors.primary)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Date of birth :",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )



            OutlinedTextField(
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                ),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                ),
                value = viewModel.dateOfBirth.value,
                shape = RoundedCornerShape(15.dp),
                onValueChange = { viewModel.dateOfBirth.value = it },
                label = { Text("Date of birth", color = MaterialTheme.colors.onSecondary) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            calendarState.show()
                        })
                },

                )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Gender :",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.gender.value == "Male",
                        onClick = { viewModel.gender.value = "Male" },
                        colors = RadioButtonDefaults.colors(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.onBackground,
                            MaterialTheme.colors.primary
                        )
                    )
                    Text(
                        text = "Male",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.gender.value == "Female",
                        onClick = { viewModel.gender.value = "Female" },
                        colors = RadioButtonDefaults.colors(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.onBackground,
                            MaterialTheme.colors.primary
                        )
                    )
                    Text(
                        text = "Female",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = viewModel.errorMessage.value, color = Color.Red)


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                LoadingTextButton(
                    showLoadingState = viewModel.showLoadingState,
                    text = "Sign Up",
                    width = 140.dp,
                    height = 45.dp,
                    padding = 16.dp
                ) {
                    viewModel.addUserToFireStore()

                }
            }
        }
    }

    when (val addUserResponse = viewModel.addUserResponse) {
        is AddToFirestoreResponse.Loading -> {
            viewModel.showLoadingState.value = true
        }

        is AddToFirestoreResponse.Failure -> addUserResponse.apply {
            viewModel.errorMessage.value = "${e.message}"
        }

        is AddToFirestoreResponse.Success -> {
            val userSuccess = addUserResponse.data
            LaunchedEffect(key1 = userSuccess) {
                if (userSuccess) {
                    navController.navigate("home_Screen")
                }
            }
        }
    }

}