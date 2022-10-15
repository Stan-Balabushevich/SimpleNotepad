package id.slava.nt.simplenotepad

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

const val TITLE = "Title"
const val CONTENT = "Content"
const val TIME_ADDED = "Added"
const val TIME_EDITED = "Edited"

class MainViewModel: ViewModel() {


    private val _searchBy = MutableStateFlow("")
    val searchBy: StateFlow<String>
        get() = _searchBy

    fun setSearchBy(searchBy: String){
        _searchBy.value = searchBy
    }

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    fun setSearchText(searchText: String){
        _searchText.value = searchText
    }



}