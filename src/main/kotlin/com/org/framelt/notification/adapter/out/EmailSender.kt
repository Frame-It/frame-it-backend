package com.org.framelt.notification.adapter.out

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSender(
    private val mailSender: JavaMailSender,
) {

    fun sendEmail(
        toEmail: String,
        subject: String,
        text: String,
    ) {
        val emailForm = createEmailForm(toEmail, subject, text)
        mailSender.send(emailForm)
    }

    private fun createEmailForm(
        toEmail: String,
        subject: String,
        text: String
    ): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setTo(toEmail)
        message.setSubject(subject)
        message.setText(text)

        return message
    }
}
