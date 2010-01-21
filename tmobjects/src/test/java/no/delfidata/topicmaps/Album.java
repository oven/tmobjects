package no.delfidata.topicmaps;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;

@Psi("http://psi.example.org/album")
public class Album extends TopicMapObject {
	public Album( TopicIF topic ) {
		super( topic );
	}

	public Album( TopicMapIF tm ) {
		super( tm );
	}

}
