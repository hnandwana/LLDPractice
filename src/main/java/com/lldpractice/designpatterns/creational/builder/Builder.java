package com.lldpractice.designpatterns.creational.builder;

import com.lldpractice.designpatterns.creational.builder.email.Email;
import com.lldpractice.designpatterns.creational.builder.httprequest.HttpMethod;
import com.lldpractice.designpatterns.creational.builder.httprequest.HttpRequest;
import com.lldpractice.designpatterns.creational.builder.httprequest.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Builder {
    public static void main(String[] args) {


        List<String> ccList = new ArrayList<>();
        ccList.add("user1@example.com");
        Email email = new Email.EmailBuilder("abc@gmail.com", "Meeting Reminder")
                .from("xyz@gmail.com")
                .body("Don't forget about our meeting tomorrow at 10 AM.")
                .cc(ccList)
                .bcc(List.of("wwedd", "rtrtrt"))
                .priority(Priority.HIGH)
                .attachments(List.of("agenda.pdf", "minutes.docx"))
                .build();
        email.getCc().add("rdgeredfewd");
        System.out.println("Email Details:");
        System.out.println("To: " + email.getTo());
        System.out.println("From: " + email.getFrom());
        System.out.println("Subject: " + email.getSubject());
        System.out.println("Body: " + email.getBody());
        System.out.println("Priority: " + email.getPriority());
        System.out.println("CC: " + email.getCc());
        System.out.println("BCC: " + email.getBcc());
        System.out.println("Attachments: " + email.getAttachments());


        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder("http://example.com/api/data", HttpMethod.POST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer token_value")
                .addQueryParam("id", "12345")
                .addHeader("Custom-Header", "CustomValue")
                .headers(Map.of("Another-Header", "AnotherValue"))
                .timeout(60)
                .followRedirects(false)
                .body("{\"key\":\"value\"}")
                .build();

        System.out.println("HTTP Request Details:");
        System.out.println("URL: " + httpRequest.getUrl());
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Headers: " + httpRequest.getHeaders());
        System.out.println("Query Params: " + httpRequest.getQueryParams());
        System.out.println("Timeout: " + httpRequest.getTimeout() + " seconds");
        System.out.println("Follow Redirects: " + httpRequest.isFollowRedirects());
        System.out.println("Body: " + httpRequest.getBody());
System.out.println(httpRequest);

    }
}