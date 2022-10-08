package id.slava.nt.simplenotepad.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

@Composable
fun ExpandableSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    onSearchTitle: (String) -> Unit,
    modifier: Modifier = Modifier,
    expandedInitially: Boolean = false,
    menuOpenInitially: Boolean = false,
    tint: Color = MaterialTheme.colors.onPrimary
) {
    val (expanded, onExpandedChanged) = remember {
        mutableStateOf(expandedInitially)
    }

    val searchMenuOptions = remember {
        mutableStateOf(menuOpenInitially)
    }

    var searchTitle by remember { mutableStateOf("") }


    Crossfade(targetState = expanded) { isSearchFieldVisible ->
        when (isSearchFieldVisible) {
            true -> ExpandedSearchView(
                searchDisplay = searchDisplay,
                onSearchDisplayChanged = onSearchDisplayChanged,
                onSearchDisplayClosed = onSearchDisplayClosed,
                onExpandedChanged = onExpandedChanged,
                modifier = modifier,
                tint = tint,
                searchTitle = searchTitle
            )

            false -> CollapsedSearchView(
                onExpandedChanged = onExpandedChanged,
                searchMenuOptions = searchMenuOptions,
                modifier = modifier,
                tint = tint,
                onDropdownMenuItemSelected = {
                    searchTitle = it
                    onSearchTitle(it)
                }
            )
        }
    }
}

@Composable
fun SearchIcon(iconTint: Color) {
    Icon(
        imageVector =  Icons.Filled.Search,
        contentDescription = stringResource(id = R.string.search),
        tint = iconTint
    )
}

@Composable
fun CollapsedSearchView(
    onExpandedChanged: (Boolean) -> Unit,
    searchMenuOptions: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
    onDropdownMenuItemSelected: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.notes),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(start = 16.dp),

        )
        IconButton(onClick = {
            searchMenuOptions.value= true

        }) {
            SearchIcon(iconTint = tint)
        }

        when( searchMenuOptions.value){

            true -> SearchOptions(onExpandedChanged = onExpandedChanged,
                mExpandedMenu = searchMenuOptions,
                onDropdownMenuItemSelected = onDropdownMenuItemSelected)
            else -> {}
        }
    }
}

@Composable
fun SearchOptions(onExpandedChanged: (Boolean) -> Unit,
                  mExpandedMenu: MutableState<Boolean>,
                  onDropdownMenuItemSelected: (String) -> Unit){

    val dropDownOptions = listOf(stringResource(id = R.string.search_title),
        stringResource(id = R.string.search_content))
    
    DropdownMenu(expanded = mExpandedMenu.value,
        onDismissRequest = { mExpandedMenu.value = false })
         {
             dropDownOptions.forEach { label ->
                 DropdownMenuItem(onClick = {
                     onDropdownMenuItemSelected(label)
                     mExpandedMenu.value = false
                     onExpandedChanged(true)
                 }) {
                     Text(text = label)
                 }
            }
        }
}

@Composable
fun ExpandedSearchView(
    searchDisplay: String,
    searchTitle: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
) {
    val focusManager = LocalFocusManager.current

    val textFieldFocusRequester = remember { FocusRequester() }

    SideEffect {
        textFieldFocusRequester.requestFocus()
    }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length)))
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onExpandedChanged(false)
            onSearchDisplayClosed()
        }) {
            Icon(
                imageVector =  Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back_arrow),
                tint = tint
            )
        }
        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onSearchDisplayChanged(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(textFieldFocusRequester),
            label = {
                Text(text = searchTitle, color = tint)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@Preview
@Composable
fun CollapsedSearchViewPreview() {
    SimpleNotepadTheme {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            ExpandableSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = {},
                onSearchDisplayClosed = {},
                onSearchTitle = {}
            )
        }
    }
}

@Preview
@Composable
fun ExpandedSearchViewPreview() {
    SimpleNotepadTheme {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            ExpandableSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = {},
                expandedInitially = true,
                onSearchDisplayClosed = {},
                onSearchTitle = {}
            )
        }
    }
}