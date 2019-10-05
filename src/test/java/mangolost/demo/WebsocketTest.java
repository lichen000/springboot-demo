package mangolost.demo;

import mangolost.demo.websocket.WebSocketServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chen.li200 on 2019-10-05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebsocketTest {

	/**
	 *
	 */
	@Test
	public void sendToOne() {
		String message = "1111";
		String sessionId = "22222";
		WebSocketServer.SendMessage(sessionId, message);
	}

	/**
	 *
	 */
	@Test
	public void sendAll() {
		String message = "1111";
		WebSocketServer.BroadCastInfo(message);
	}


}
