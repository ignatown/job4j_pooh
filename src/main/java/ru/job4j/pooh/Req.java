package ru.job4j.pooh;

import java.util.Map;

public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final Map<String, String> params;

    public Req(String method, String mode, String queue, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.params = params;
    }

    public static Req of(String content) {
        String[] bySp = content.split(" ");
        Map<String, String> map = null;
        if (content.contains("POST")) {
            String[] bySl = bySp[1].split("/");
            String[] byLn = bySp[bySp.length - 1].split("\n");
            String[] fPrms = byLn[byLn.length - 1].split("=");
            map = Map.of(fPrms[0], fPrms[1]);
            return new Req(bySp[0], bySl[1], bySl[2], map);
        }
        if (content.contains("GET")) {
            String[] bySl = bySp[1].split("/");
            return new Req(bySp[0], bySl[1], bySl[2], map);

        }
        throw new IllegalArgumentException();
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String queue() {
        return this.queue;
    }

    public Map<String, String> params() {
        return this.params;
    }

    public String param(String key) {
        if (key != null) {
            return params.get(key);
        }
        return null;
    }

    public String keyMap() {
        if (params.isEmpty()) {
            return null;
        }
        return params.keySet().iterator().next();
    }
}