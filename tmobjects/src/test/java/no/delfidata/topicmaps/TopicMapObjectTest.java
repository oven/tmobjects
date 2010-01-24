package no.delfidata.topicmaps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ontopia.infoset.core.LocatorIF;
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

		List<Class<? extends TopicMapObject>> classes = new ArrayList<Class<? extends TopicMapObject>>();
		classes.add( Group.class );

		repository = new TopicMapObjectRepository( tm );
		repository.setClasses( classes );
		repository.addClass( Artist.class );
		repository.addClass( Album.class );

		TopicMapObjectRepository.setInstance( repository );
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
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		assertEquals( "Heart-throb 60's group.", testObj.getOccurrenceValue( "http://psi.ontopia.net/ontology/description" ) );
	}

	@Test
	public void setOccurrenceValue() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		testObj.setDescription( "foo" );
		assertEquals( "foo", testObj.getDescription() );
	}

	@Test
	public void getAssociatedTopics() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		List<TopicIF> topics = testObj.getAssociatedTopics( "ex:album-created-by", "ex:artist", "ex:album" );
		assertEquals( 2, topics.size() );
	}

	@Test
	public void getAssociatedTopic() {
		TopicMapObject testObj = repository.getByPsi( "ex:AHardDaysNightAlbum" );
		TopicIF artist = testObj.getAssociatedTopic( "ex:album-created-by", "ex:album", "ex:artist" );
		assertNotNull( artist );
	}

	@Test
	public void getAssociatedTopicMapObject() {
		TopicMapObject testObj = repository.getByPsi( "ex:AHardDaysNightAlbum" );
		Artist artist = (Artist)testObj.getAssociatedTopicMapObject( "ex:album-created-by", "ex:album", "ex:artist" );
		assertNotNull( artist );
	}

	@Test
	public void getAssociatedTopicMapObjectsWithSpecifiedListType() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		List<Album> albums = testObj.getAssociatedTopicMapObjects( Album.class, "ex:album-created-by", "ex:artist", "ex:album" );
		assertNotNull( albums.get( 0 ) );
	}

	@Test
	public void getAssociatedTopicMapObjects() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		List<TopicMapObject> albums = testObj.getAssociatedTopicMapObjects( "ex:album-created-by", "ex:artist", "ex:album" );
		assertNotNull( albums.get( 0 ) );
	}

	@Test
	public void associateWithOtherTopic() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		TopicMapObject album = repository.getByPsi( "ex:ChaosAndCreationInTheBackyard" );
		testObj.associateWithTopic( album, "ex:album-created-by", "ex:artist", "ex:album" );
		List<TopicIF> topics = testObj.getAssociatedTopics( "ex:album-created-by", "ex:artist", "ex:album" );
		assertEquals( 3, topics.size() );
	}

	@Test
	public void toStringReturnsTopicName() {
		TopicMapObject testObj = repository.getByPsi( "ex:the_beatles" );
		assertEquals( "The Beatles", testObj.toString() );
	}

	@Test
	public void createNewObject() {
		Album testObj = new Album( tm );
		testObj.setPsi( "ex:my_album" );
		assertNotNull( repository.getByPsi( "ex:my_album" ) );
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setPsiClearsAllPreviousPsis() {
		Album testObj = new Album( tm );
		testObj.addPsi( "ex:foo" );
		testObj.addPsi( "ex:bar" );
		testObj.setPsi( "ex:blapp" );
		Collection psis = testObj.getTopic().getSubjectIdentifiers();
		assertEquals( 1, psis.size() );
		assertEquals( "http://psi.example.org/blapp", ((LocatorIF)psis.iterator().next()).getAddress() );
	}

	@Test
	public void getTopicMapObjectsByType() {
		List<Artist> result = repository.getByType( Artist.class );
		assertEquals( 3, result.size() );
	}
}
