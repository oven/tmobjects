package no.delfidata.topicmaps.domain;

import net.ontopia.topicmaps.core.TopicIF;
import no.delfidata.topicmaps.Psi;
import no.delfidata.topicmaps.TopicMapObject;

@Psi("http://psi.example.org/album")
public class Album extends TopicMapObject {
	public Album( TopicIF topic ) {
		super( topic );
	}
}
