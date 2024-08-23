package com.example.probuilder.presentation.screen.project.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.domain.model.Project
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private var _project = MutableStateFlow(Project())
    val project: StateFlow<Project> = _project

    init {
        savedStateHandle.get<String>("project")?.let { projectStr ->
            _project.value = Gson().fromJson(projectStr, Project::class.java)
        }
    }
}