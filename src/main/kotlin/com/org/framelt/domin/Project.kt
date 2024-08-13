package com.org.framelt.domin

import com.org.framelt.domin.user.Identity
import com.org.framelt.domin.user.Location
import java.time.LocalDateTime


data class Project(
    val id: Long? = null,
    val title: String,
    val identity: Identity,
    val shootingDateTime: LocalDateTime,
    val locationType: Location,
    val concept: String,
    val photos: List<String>,
    val description: String,
    val editingDetails: String?
){

}