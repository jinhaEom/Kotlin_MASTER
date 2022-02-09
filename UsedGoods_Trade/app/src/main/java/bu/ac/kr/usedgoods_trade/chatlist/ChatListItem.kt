package bu.ac.kr.usedgoods_trade.chatlist

data class ChatListItem(

    val buyerId : String,
    val sellerId : String,
    val itemTitle : String,
    val key : Long
){
    constructor(): this("","","",0)
}