package bu.ac.kr.music_streaming

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import bu.ac.kr.music_streaming.service.MusicDto
import bu.ac.kr.music_streaming.service.MusicService
import com.google.android.exoplayer2.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment : Fragment(R.layout.fragment_player) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getVideoListFromServer()
    }
    private fun getVideoListFromServer(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also{
                it.listMusics()
                    .enqueue(object: Callback<MusicDto>{
                        override fun onResponse(
                            call: Call<MusicDto>,
                            response: Response<MusicDto>
                        ) {
                            Log.d("PlayerFragment","${response.body()}")
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {
                           
                        }

                    })
            }
    }
    companion object{
        fun newInstance() : PlayerFragment{
            return PlayerFragment()
        }
    }
}