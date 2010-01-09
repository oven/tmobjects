package no.delfidata.topicmaps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;

import org.junit.Before;
import org.junit.Test;

public class TopicMapObjectTest {

	private TopicMapStoreIF store;
	private TopicMapIF tm;
	private TopicMapObjectRepository repository;

	@Before
	public void setUp() throws Exception {
		store = TopicMaps.createStore( "JillsMusic.xtm", false );
		tm = store.getTopicMap();
		assertNotNull( tm );
		repository = new TopicMapObjectRepository( tm );
		repository.addClass( Album.class );
		repository.addClass( Artist.class );
		repository.addClass( Group.class );
	}

	@Test
	public void getTopic() {
		TopicMapObject testObj = repository.getByPsi( "http://psi.example.org/AHardDaysNightAlbum" );
		assertEquals( "A Hard Day's Night", testObj.getName() );
	}

	@Test
	public void getTopic_returns_correct_subclass() {
		Group beatles = (Group)repository.getByPsi( "ex:the_beatles" );
		assertNotNull( beatles );
	}

	@Test
	public void setTopicName() {
		TopicMapObject testObj = repository.getByPsi( "ex:AHardDaysNightAlbum" );
		assertEquals( "A Hard Day's Night", testObj.getName() );
		testObj.setName( "foo" );
		assertEquals( "foo", testObj.getName() );
		assertEquals( 1, testObj.topic.getTopicNames().size() );
	}

	@Test
	public void getOccurrenceValue() {
		TopicMapObject testObj = repository.getByPsi( "http://psi.example.org/the_beatles" );
		assertEquals( "Heart-throb 60's group.", testObj.getOccurrenceValue( "http://psi.ontopia.net/ontology/description" ) );
	}

	@Test
	public void setOccurrenceValue() {
		TopicMapObject testObj = repository.getByPsi( "http://psi.example.org/the_beatles" );
		testObj.setOccurrenceValue( "http://psi.ontopia.net/ontology/description", "foo" );
		assertEquals( "foo", testObj.getOccurrenceValue( "http://psi.ontopia.net/ontology/description" ) );
	}

	@Test
	public void getAssociatedTopics() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		List<TopicIF> topics = testObj.getAssociatedTopics( "ex:album-created-by", "ex:artist", "ex:album" );
		assertEquals( 2, topics.size() );
	}
}
