package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.method())) {
            String byQueue = req.queue();
            String byParam = req.param(req.keyMap());
            if (map.get(byQueue) == null) {
                ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
                queue.add(byParam);
                map.putIfAbsent(byQueue, queue);
            }
            return new Resp(byParam, 1);
        }
        if ("GET".equals(req.method())) {
            return new Resp(map.get(req.queue()).poll(), 0);
        }
        throw new IllegalArgumentException();
    }
}