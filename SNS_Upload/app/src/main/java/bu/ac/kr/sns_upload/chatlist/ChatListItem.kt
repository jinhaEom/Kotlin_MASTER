package bu.ac.kr.sns_upload.chatlist

data class ChatListItem(

    val buyerId : String,
    val sellerId : String,
    val itemTitle : String,
    val key : Long
){
    constructor(): this("","","",0)
}