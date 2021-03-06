package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {

    @Test
    public void whenPostMethod() {
        var content = "POST /topic/weather HTTP/1.1" + System.lineSeparator()
                + "Host: localhost:9000" + System.lineSeparator()
                + "User-Agent: curl/7.67.0" + System.lineSeparator()
                + "Accept: */*" + System.lineSeparator()
                + "Content-Length: 7" + System.lineSeparator()
                + "Content-Type: application/x-www-form-urlencoded" + System.lineSeparator() + System.lineSeparator()
                + "text=13";
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param("text"), is("13"));
    }

    @Test
    public void whenGetMethod() {
        var content = "GET /queue/weather HTTP/1.1" + System.lineSeparator()
               + "Host: localhost:9000" + System.lineSeparator()
               + "User-Agent: curl/7.67.0" + System.lineSeparator()
               + "Accept: */*" + System.lineSeparator() + System.lineSeparator()
               + "userId=1";
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.queue(), is("weather"));
        assertNull(req.param(null));
    }
}
