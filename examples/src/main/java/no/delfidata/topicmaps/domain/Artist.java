package no.delfidata.topicmaps.domain;

import java.util.List;

import net.ontopia.topicmaps.core.TopicIF;
import no.delfidata.topicmaps.Psi;
import no.delfidata.topicmaps.TopicMapObject;

@Psi("http://psi.example.org/artist")
public class Artist extends TopicMapObject {

	public Artist( TopicIF topic ) {
		super( topic );
	}

	public List<Album> getAlbums() {
		return getAssociatedTopicMapObjects( Album.class, "ex:album-created-by", "ex:artist", "ex:album" );
	}
}
