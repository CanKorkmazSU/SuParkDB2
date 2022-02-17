package com.example.suparkdb2.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RowWithDropDownMenu(
    menuItemOnClick:(String)->Unit
){
    var expandedState by remember{mutableStateOf(false)}

    Row(
        Modifier
            .width(240.dp)
            .height(32.dp)
            .clickable {
                expandedState = !expandedState
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Choose Your Role in Campus",
            color = Color.Gray
        )
        Canvas(
            modifier = Modifier
                .size(12.dp)
                .weight(weight = 1f)
                .align(Alignment.CenterVertically)
        ) {
            drawCircle(color = Color.Cyan)
        }
        DropdownMenu(expanded = expandedState, onDismissRequest = { expandedState = false }) {
            DropdownMenuItem(onClick = { menuItemOnClick("Student") }) {
                Text("Student")
            }
            DropdownMenuItem(onClick = { menuItemOnClick("Faculty Member") }) {
                Text("Faculty Member")
            }
            DropdownMenuItem(onClick = { menuItemOnClick("Personnel") }) {
                Text("Personnel")
            }
            DropdownMenuItem(onClick = { menuItemOnClick("Admin") }) {
                Text("Admin")
            }

        }
    }
}

@Preview
@Composable
fun DropDownMenuMePreview(){
    val it1 = "yes"
    RowWithDropDownMenu(menuItemOnClick = { print(it1)} )
}