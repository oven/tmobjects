package no.delfidata.topicmaps;

import static org.junit.Assert.assertNotNull;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AppTest {

	private TopicMapStoreIF store;
	private TopicMapIF tm;

	@Test
	public void openTopicMap() throws Exception {
		store = TopicMaps.createStore( "JillsMusic.xtm", false );
		tm = store.getTopicMap();
		assertNotNull( tm );
	}

	@Test
	public void getRepositoryFromSpring() throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext( "src/main/webapp/WEB-INF/applicationContext.xml" );
		TopicMapObjectRepository bean = (TopicMapObjectRepository)context.getBean( "repository" );
		assertNotNull( bean );
	}

}
