package com.lldpractice.solidprinciples.dependencyinversion.valid;

// ✅ ABSTRACTION — high-level and low-level BOTH depend on this
// OrderService depends on THIS, not on EmailSender or SmsSender
public interface NotificationSender {
    void send(String message);
}

