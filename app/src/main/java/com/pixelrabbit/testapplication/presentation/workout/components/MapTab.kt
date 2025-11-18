package com.pixelrabbit.testapplication.presentation.workout.components

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.Marker
import com.pixelrabbit.testapplication.presentation.workout.WorkoutUiState

@Composable
fun MapTab(
    uiState: WorkoutUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Configuration.getInstance().userAgentValue = context.packageName

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    controller.setZoom(16.0)
                    controller.setCenter(uiState.gpsTrack.firstOrNull() ?: GeoPoint(55.755826, 37.617300))

                    overlays.clear()

                    if (uiState.gpsTrack.isNotEmpty()) {
                        val polyline = Polyline().apply {
                            setPoints(uiState.gpsTrack)
                            outlinePaint.strokeWidth = 12f
                            outlinePaint.color = Color.parseColor("#2196F3")
                        }
                        overlays.add(polyline)

                        val startMarker = Marker(this).apply {
                            position = uiState.gpsTrack.first()
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Старт"
                        }
                        overlays.add(startMarker)

                        if (uiState.isRunning && uiState.gpsTrack.isNotEmpty()) {
                            val currentMarker = Marker(this).apply {
                                position = uiState.gpsTrack.last()
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                title = "Текущая позиция"
                            }
                            overlays.add(currentMarker)
                        }
                    }
                }
            },
            update = { mapView ->
                mapView.overlays.clear()

                if (uiState.gpsTrack.isNotEmpty()) {
                    val polyline = Polyline().apply {
                        setPoints(uiState.gpsTrack)
                        outlinePaint.strokeWidth = 12f
                        outlinePaint.color = Color.parseColor("#2196F3")
                    }
                    mapView.overlays.add(polyline)

                    val startMarker = Marker(mapView).apply {
                        position = uiState.gpsTrack.first()
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        title = "Старт"
                    }
                    mapView.overlays.add(startMarker)

                    if (uiState.isRunning && uiState.gpsTrack.isNotEmpty()) {
                        val currentMarker = Marker(mapView).apply {
                            position = uiState.gpsTrack.last()
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Текущая позиция"
                        }
                        mapView.overlays.add(currentMarker)

                        mapView.controller.animateTo(uiState.gpsTrack.last())
                    }
                }

                mapView.invalidate()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}