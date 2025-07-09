package com.example.customview.edit_screen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class DrawingMode {
    BRUSH, ERASER
}

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val rotation: Float = 0f,
    val drawingType: DrawingMode = DrawingMode.BRUSH
)


data class PathData(
    val id: String,
    val color: Color,
    val paths: List<Offset>,
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
    data class OnRotationChanged(val rotation: Float): DrawingAction
    data class OnDrawModeChanged(val mode: DrawingMode): DrawingAction
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
            is DrawingAction.OnRotationChanged -> onRotationChanged(action.rotation)
            is DrawingAction.OnDrawModeChanged -> onDrawModeChanged(action.mode)
        }
    }

    private fun onDrawModeChanged(mode: DrawingMode) {
        _drawingState.update { it.copy(drawingType = mode) }

    }

    private fun onRotationChanged(rotation: Float) {
        _drawingState.update { it.copy(rotation = rotation) }
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
                    color = if (it.drawingType == DrawingMode.ERASER) Color.Transparent else it.selectedColor,
                    paths = emptyList(),
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







