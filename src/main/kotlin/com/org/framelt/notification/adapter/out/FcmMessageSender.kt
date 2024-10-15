package com.org.framelt.notification.adapter.out

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.org.framelt.notification.application.port.`in`.NotificationSendPort
import com.org.framelt.notification.application.service.NotificationLetter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.ExecutionException
import javax.annotation.PostConstruct

@Component
class FcmMessageSender(
    @Value("\${fcm.certification.path}")
    private val fcmCertificationPath: String,
) : NotificationSendPort {

    private val log = LoggerFactory.getLogger(FcmMessageSender::class.java)

    @PostConstruct
    fun initialize() {
        try {
            val resource = ClassPathResource(fcmCertificationPath)
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.inputStream))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            log.error("FCM 인증이 실패했습니다.", e)
        }
    }

    override fun sendTo(letter: NotificationLetter) {
        val deviceToken = letter.receiver.deviseToken

        if (deviceToken.isNullOrEmpty()) {
            return
        }

        val notification = Notification.builder()
            .setTitle(letter.title)
            .setBody(letter.content)
            .build()
        val message = Message.builder()
            .setToken(deviceToken)
            .setNotification(notification)
            .build()

        try {
            val response = FirebaseMessaging.getInstance().sendAsync(message).get()
            log.info("알림 전송 성공 : $response")
        } catch (e: InterruptedException) {
            log.error("FCM 알림 스레드에서 문제가 발생했습니다.", e)
        } catch (e: ExecutionException) {
            log.error("FCM 알림 전송에 실패했습니다.", e)
        }
    }
}
