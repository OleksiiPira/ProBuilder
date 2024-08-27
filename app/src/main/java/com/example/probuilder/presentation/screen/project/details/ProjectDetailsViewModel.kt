package com.example.probuilder.presentation.screen.project.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.remote.ProjectService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val projectService: ProjectService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val project = projectService.getProjectById(savedStateHandle.get<String>("projectId")?: "")
}