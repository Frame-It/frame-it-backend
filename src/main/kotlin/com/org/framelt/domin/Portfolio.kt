package com.org.framelt.domin

class Portfolio(
    val id: Long? = null,
    val title: String,
    val description: String,
    val photos: List<String>,
    val hashtags: List<String>,
    val collaborators: String //이거를 링크랑 사용자 이름으로 하면 회원이 아닌사람이랑 구분해서 할 수 있을듯
)
{

}