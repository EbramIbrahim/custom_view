package com.example.customview

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList()
)

data class PathData(
    val id: String,
    val color: Color,
    val paths: List<Offset>
)

val allColors = listOf(
    Color.Black,
    Color.Red,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Magenta,
    Color.Cyan,
)


sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    data class OnSelectColor(val color: Color) : DrawingAction
    data object OnClearCanvasClick : DrawingAction
}

class DrawingViewModel : ViewModel() {


    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()


    fun onAction(action: DrawingAction) {
        when (action) {
            DrawingAction.OnClearCanvasClick -> onClearCanvasClick()
            is DrawingAction.OnDraw -> onDraw(action.offset)
            DrawingAction.OnNewPathStart -> onNewPathStart()
            DrawingAction.OnPathEnd -> onPathEnd()
            is DrawingAction.OnSelectColor -> onSelectColor(action.color)
        }
    }

    private fun onSelectColor(color: Color) {
        _drawingState.update { it.copy(selectedColor = color) }
    }

    private fun onPathEnd() {
        val currentPath = _drawingState.value.currentPath ?: return
        _drawingState.update {
            it.copy(
                currentPath = null,
                paths = it.paths + currentPath
            )
        }
    }

    private fun onNewPathStart() {
        _drawingState.update {
            it.copy(
                currentPath = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = it.selectedColor,
                    paths = emptyList()
                )
            )
        }
    }

    private fun onDraw(offset: Offset) {
        val currentPath = _drawingState.value.currentPath ?: return
        _drawingState.update {
            it.copy(
                currentPath = currentPath.copy(
                    paths = currentPath.paths + offset
                )
            )
        }
    }

    private fun onClearCanvasClick() {
        _drawingState.update {
            it.copy(
                currentPath = null,
                paths = emptyList()
            )
        }
    }
}







