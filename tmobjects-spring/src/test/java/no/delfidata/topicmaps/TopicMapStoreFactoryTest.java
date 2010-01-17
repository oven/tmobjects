package no.delfidata.topicmaps;

import static org.junit.Assert.assertNotNull;
import net.ontopia.topicmaps.core.TopicMapStoreIF;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TopicMapStoreFactoryTest {
	@Test
	public void getTopicMap() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		TopicMapStoreIF store = (TopicMapStoreIF)context.getBean( "topicMapStore" );
		assertNotNull( store );
	}

}
