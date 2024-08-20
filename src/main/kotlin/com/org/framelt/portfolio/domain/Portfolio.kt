package com.org.framelt.portfolio.domain

import com.org.framelt.user.domain.User

class Portfolio(
    val id: Long? = null,
    val manage: User,
    val title: String? = null,
    val description: String? = null,
    val photos: List<String>? = null,
    val hashtags: List<String>? = null,
//    val collaborators: String //이거를 링크랑 사용자 이름으로 하면 회원이 아닌사람이랑 구분해서 할 수 있을듯
)
