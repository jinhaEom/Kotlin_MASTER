package bu.ac.kr.music_streaming.service

import bu.ac.kr.music_streaming.PlayerModel

fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = streamUrl,
        coverUrl = coverUrl,
        track = track,
        artist = artist
    )

fun MusicDto.mapper(): PlayerModel =
    PlayerModel(
        playMusicList = musics.mapIndexed {
            val modelList = musics.mapIndexed { index, musicEntity ->
                musicEntity.mapper(index.toLong())
            }
            )
