package com.lldpractice.designpatterns.creational.builder.email;

import com.lldpractice.designpatterns.creational.builder.httprequest.Priority;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Collections.emptyList;

@Getter
public class Email {

    private final String to;
    private final String subject;
    private final String from;
    private final String body;
    private  List<String> cc;
    private final List<String> bcc;
    private final List<String> attachments;
    private final Priority priority;


    private Email(EmailBuilder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.from = builder.from;
        this.body = builder.body;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.attachments = builder.attachments;
        this.priority = builder.priority;
    }

    public static class EmailBuilder {
        private final String to;
        private final String subject;
        private String from = "noreply@system.com";
        private String body = "";
        private List<String> cc = emptyList();
        private List<String> bcc = emptyList();
        private List<String> attachments = emptyList();
        private Priority priority = Priority.NORMAL;


        public EmailBuilder(String to, String subject) {
            if (StringUtils.isBlank(to)) {
                throw new IllegalArgumentException("Recipient email address 'to' cannot be null or empty.");
            }
            if (StringUtils.isBlank(subject)) {
                throw new IllegalArgumentException("Email subject cannot be null or empty.");
            }
            this.to = to;
            this.subject = subject;
        }


        public EmailBuilder from(String from) {
            this.from = from;
            return this;
        }

        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailBuilder cc(List<String> cc) {
            this.cc = (cc==null)? emptyList() : cc;
            return this;
        }

        public EmailBuilder bcc(List<String> bcc) {
            this.bcc = (bcc==null)? emptyList() : bcc;
            return this;
        }

        public EmailBuilder attachments(List<String> attachments) {
            this.attachments = (attachments==null)? emptyList() : attachments;
            return this;
        }

        public EmailBuilder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Email build() {
            return new Email(this);
        }


    }

}
