package ru.job4j.pooh;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TopicServiceTest {
    @Test
    public void whenPostThenGetTopic() {
        var topicService = new TopicService();
        Map<String, String> params = new HashMap<>();
        params.put("UserId", "1");
        params.put("temperature", "18");
        topicService.process(
                new Req("POST", "topic", "weather", params)
        );
        var result = topicService.process(
                new Req("GET", "topic", "weather", params)
        );
        assertThat(result.text(), is("18"));
    }
}