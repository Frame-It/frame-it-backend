package com.org.framelt.notification.adapter.out

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.messaging.MessagingException
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Component
class EmailSender(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
) {

    fun sendEmail(
        toEmail: String,
        subject: String,
        text: String,
    ) {
        val context = Context()
        context.setVariable("text", text)

        val htmlContent = templateEngine.process("emailTemplate", context)
        val mimeMessage = mailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(mimeMessage, false, "UTF-8")
            helper.setTo(toEmail)
            helper.setSubject(subject)
            helper.setText(htmlContent, true)

            mailSender.send(mimeMessage)
        } catch (e: MessagingException) {
            throw RuntimeException("이메일 알림 전송에 실패했습니다.", e)
        }
    }
}
