package mangolost.demo.websocket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/websocket")
public class WebSocketController {

    /**
     *
     * @param message
     * @param sessionId
     * @return
     */
    @RequestMapping("sendone")
    public String sendToOne(String message, String sessionId) throws IOException {
        WebSocketServer.SendMessage(sessionId, message);
        return "OK";
    }

    /**
     *
     * @param message
     * @return
     */
    @RequestMapping("sendall")
    public String sendAll(String message) throws IOException {
        WebSocketServer.BroadCastInfo(message);
        return "OK";
    }

}
