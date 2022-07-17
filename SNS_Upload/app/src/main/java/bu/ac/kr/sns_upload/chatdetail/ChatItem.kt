package bu.ac.kr.sns_upload.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String
)
{
    constructor(): this("","")
}