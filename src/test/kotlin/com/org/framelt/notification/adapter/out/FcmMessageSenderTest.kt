package com.org.framelt.notification.adapter.out

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.util.concurrent.ExecutionException

@SpringJUnitConfig
class FcmMessageSenderTest {
    private val log = LoggerFactory.getLogger(FcmMessageSenderTest::class.java)

    private lateinit var fcmMessageSender: FcmMessageSender

    @Value("\${fcm.certification.path}")
    private lateinit var fcmCertificationPath: String

    @BeforeEach
    fun setUp() {
        fcmMessageSender = FcmMessageSender("frame-it-backend-config/framit-8771a-firebase-adminsdk-dix56-08c6250c3c.json")
        fcmMessageSender.initialize() // Firebase 초기화가 @PostConstruct로 진행됨
    }

    @Test
    fun `send notification to Firebase successfully`() {
        val data =
            mapOf(
                "title" to "다시한번",
                "body" to "This is a test message",
                "link" to "http://localhost:3000/feed",
            )

        val deviceToken = """
           dxeRfYFBqoIKAGWRfrFKlO:APA91bFeHVZnw70zUJhE9vf7fn9XPLLrb6TG3dJ9VFdY_bjVmPN4jSGOduIOGneK38O1qjIm5o5T7Z3v8hNDSduCgDoc7xe6fboin60rkaUTRblKSV7nQvtc2qUXIuseFFR-8FT6Yjtn 
        """ // 실제 테스트할 디바이스 토큰으로 변경
        val notification =
            Notification
                .builder()
                .setTitle("다시한번")
                .setBody("This is a test message")
                .build()

        val message =
            Message
                .builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .putAllData(data)
                .build()

        try {
            // When: Firebase로 메시지를 전송
            val response = FirebaseMessaging.getInstance().sendAsync(message).get()
            log.info("알림 전송 성공 : $response")
        } catch (e: InterruptedException) {
            log.error("FCM 알림 스레드에서 문제가 발생했습니다.", e)
            Thread.currentThread().interrupt() // 스레드 인터럽트 처리
        } catch (e: ExecutionException) {
            log.error("FCM 알림 전송에 실패했습니다.", e)
        }
    }
}
