package com.example.probuilder.presentation.screen.project.create

sealed class ProjectStep {
    object CreateProject : ProjectStep()
    object CreateClient : ProjectStep()
    object CreateRoomsStep : ProjectStep()
    object CreateRoom : ProjectStep()
    object CreateWorkers : ProjectStep()
}
