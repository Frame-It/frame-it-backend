package com.org.framelt.portfolio.adapter.out

import org.springframework.http.MediaType
import java.util.*

interface FileUploadClient {
    fun upload(fileName: String, mediaType: MediaType, data: ByteArray): Optional<String>
}
