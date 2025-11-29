package com.lldpractice.designpatterns.behavioural.observer.observable;

import com.lldpractice.designpatterns.behavioural.observer.displayobserver.Observer;

public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

}

