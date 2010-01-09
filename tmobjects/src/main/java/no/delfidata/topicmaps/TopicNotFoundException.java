package no.delfidata.topicmaps;

@SuppressWarnings("serial")
public class TopicNotFoundException extends RuntimeException {
	public TopicNotFoundException( String psi ) {
		super( "No topic with psi " + psi + " was found in the topic map." );
	}
}
