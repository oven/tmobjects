package no.delfidata.topicmaps.domain;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import no.delfidata.topicmaps.Psi;
import no.delfidata.topicmaps.TopicMapObject;

@Psi("http://psi.example.org/album")
public class Album extends TopicMapObject {
	public Album( TopicIF topic ) {
		super( topic );
	}

	public Album( TopicMapIF tm ) {
		super( tm );
	}

	public Artist getArtist() {
		return (Artist)getAssociatedTopicMapObject( "ex:album-created-by", "ex:album", "ex:artist" );
	}

	public String getCover() {
		return getOccurrenceValue( "ex:album-cover" );
	}

	public void setCover( String value ) {
		setOccurrenceValue( "ex:album-cover", value );
	}
}
