package no.delfidata.topicmaps.domain;

import net.ontopia.topicmaps.core.TopicIF;
import no.delfidata.topicmaps.Psi;

@Psi("http://psi.example.org/group")
public class Group extends Artist {

	public Group( TopicIF topic ) {
		super( topic );
	}

}
