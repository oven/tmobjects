package no.delfidata.topicmaps;

import static org.junit.Assert.assertNotNull;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;

import org.junit.Test;

public class AppTest {

	private TopicMapStoreIF store;
	private TopicMapIF tm;

	@Test
	public void openTopicMap() throws Exception {
		store = TopicMaps.createStore("JillsMusic.xtm", false);
		tm = store.getTopicMap();
		assertNotNull(tm);
	}
}
