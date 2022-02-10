package bu.ac.kr.usedgoods_trade.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String
)
{
    constructor(): this("","")
}