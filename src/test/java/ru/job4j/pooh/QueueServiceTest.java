package ru.job4j.pooh;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        var queueService = new QueueService();
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "18");
        queueService.process(
                new Req("POST", "queue", "weather", params)
        );
        var result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("18"));
    }
}