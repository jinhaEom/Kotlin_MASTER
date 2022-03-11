package bu.ac.kr.music_streaming

import bu.ac.kr.music_streaming.service.MusicModel

data class PlayerModel(
    val playMusicList: List<MusicModel> = emptyList(),
    var currentPosition : Int = -1,
    private var isWatchingPlayListView: Boolean = true
){

}
