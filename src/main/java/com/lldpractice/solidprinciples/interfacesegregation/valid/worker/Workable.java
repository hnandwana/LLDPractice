package com.lldpractice.solidprinciples.interfacesegregation.valid.worker;

// ✅ Focused interfaces — each represents ONE capability
// Clients implement only what they need

public interface Workable {
    void work();
}

