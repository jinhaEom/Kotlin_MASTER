package bu.ac.kr.music_streaming

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import bu.ac.kr.music_streaming.databinding.FragmentPlayerBinding
import bu.ac.kr.music_streaming.service.MusicDto
import bu.ac.kr.music_streaming.service.MusicService
import bu.ac.kr.music_streaming.service.mapper
import com.google.android.exoplayer2.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private var binding : FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true
    private lateinit var playListAdapter: PlayListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding
        initPlayListButton(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)

        getVideoListFromServer()
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        playListAdapter = PlayListAdapter{
            // TODO 음악 재생.

        }
        fragmentPlayerBinding.playListRecyclerView.apply{
            adapter = playListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playlistImageView.setOnClickListener {
            //TODO 만약에 서버에서 데이터가 다 불러오지 않은 형태일때 예외처리
            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView

            fragmentPlayerBinding.playListViewGroup.isVisible = isWatchingPlayListView.not()

            isWatchingPlayListView = !isWatchingPlayListView
        }
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
                            response.body()?.let{
                                val modelList = it.musics.mapIndexed{ index, musicEntity ->
                                    musicEntity.mapper(index.toLong())
                                }
                                playListAdapter.submitList(modelList)
                            }
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