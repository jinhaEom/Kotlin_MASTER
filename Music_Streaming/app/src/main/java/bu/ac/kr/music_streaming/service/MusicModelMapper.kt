package bu.ac.kr.music_streaming.service

fun MusicEntity.mapper(id:Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = streamUrl,
        coverUrl = coverUrl,
        track = track,
        artist = artist
    )