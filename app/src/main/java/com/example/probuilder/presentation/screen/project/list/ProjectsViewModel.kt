package com.example.probuilder.presentation.screen.project.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.domain.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val projectService: ProjectService,
) : ViewModel() {

    private val _projects = projectService.projects.map { list -> list.sortedBy { it.name } }
    val projects: Flow<List<Project>> = _projects

    fun removeProject(project: Project) = viewModelScope.launch {
        projectService.delete(project.id)
    }

}
