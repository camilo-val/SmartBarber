package com.smartbarber.domain.model.queue.event;

import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class VirtualQueue {

    private boolean open;

    private final Queue<QueueReservation> reservations =
            new ConcurrentLinkedQueue<>();

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

}