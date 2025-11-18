package com.pixelrabbit.testapplication.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import com.pixelrabbit.testapplication.R
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    private val context: Context
) {
    private val soundPool: SoundPool
    private var beepSoundId = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(3, android.media.AudioManager.STREAM_MUSIC, 0)
        }

        // Загружаем звук из raw ресурсов
        beepSoundId = soundPool.load(context, R.raw.beep, 1)
    }

    suspend fun playBeep(isSingle: Boolean) {
        if (isSingle) {
            // Один пик
            soundPool.play(beepSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        } else {
            // Два пика
            soundPool.play(beepSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
            delay(300)
            soundPool.play(beepSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }
}