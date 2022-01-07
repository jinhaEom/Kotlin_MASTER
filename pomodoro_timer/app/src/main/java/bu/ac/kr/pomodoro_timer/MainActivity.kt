package bu.ac.kr.pomodoro_timer

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainMinutesTextView)
    }
    private val remainSecondTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainSecondesTextView)
    }
    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }
    private var currentCountDownTimer: CountDownTimer? = null

    private val soundPool = SoundPool.Builder().build()
    private var tickingSoundId: Int? = null

    private var bellSoundId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause() // 앱 -> 홈으로 나갔을때 사운드 멈춤.
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()  // soundPool 에 기존에 로드되었던 파일이 해제되게 됨.
    }

    private fun bindViews() {
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        updateRemainTime(progress * 60 * 1000L)

                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    stopCountDown()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?: return

                    if (seekBar.progress ==0){
                        stopCountDown()
                    }else{
                        startCountDown()

                    }
                }
            }
        )
    }
    private fun stopCountDown(){ // 현재 시간이 멈추는 메소드
        currentCountDownTimer?.cancel()
        currentCountDownTimer = null
        soundPool.autoPause()
    }

    private fun initSounds() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
        object : CountDownTimer(initialMillis, 1000L) {

            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }
    private fun startCountDown(){

        currentCountDownTimer =
            createCountDownTimer(seekBar.progress * 60 * 1000L).start()

        currentCountDownTimer?.start()

        tickingSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
        }
    }
    private fun completeCountDown() {
        updateRemainTime(0)
        updateSeekBar(0)
        soundPool.autoPause()
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d'".format(remainSeconds / 60)
        remainSecondTextView.text = "%02d".format(remainSeconds % 60)

    }

    private fun updateSeekBar(remainMillis: Long) {
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}