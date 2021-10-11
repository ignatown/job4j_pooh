package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> users
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.method())) {
            return new Resp(post(req), 200);
        }
        if ("GET".equals(req.method())) {
            return new Resp(get(req), 200);
        }
        throw new IllegalArgumentException();
    }

    private String post(Req req) {
        String rsl = "";
        queues.putIfAbsent(req.queue(), new ConcurrentLinkedQueue<>());
        for (String key : req.params().keySet()) {
            if (!"UserId".equals(key)) {
                rsl = req.param(key);
            }
        }
        queues.get(req.queue()).offer(rsl);
            for (String str : users.keySet()) {
                users.get(str).get(req.queue()).offer(rsl);
            }
        return rsl;
    }

    private String get(Req req) {
        String key = req.param("UserId");
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
        for (String queue : queues.keySet()) {
            ConcurrentLinkedQueue<String> userQueue = new ConcurrentLinkedQueue<>(queues.get(queue));
            map.put(queue, userQueue);
        }
        users.putIfAbsent(key, map);
        return users.get(key).get(req.queue()).poll();
    }

}