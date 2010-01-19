package no.delfidata.topicmaps;

import java.util.Iterator;
import java.util.List;

import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.utils.TopicStringifiers;

public abstract class TopicMapObject {
	protected final TopicIF topic;
	protected final TopicMapBuilderIF builder;
	protected final TopicMapIF tm;
	protected final TopicMapUtil util;

	public TopicMapObject( TopicIF topic ) {
		this.topic = topic;
		this.tm = topic.getTopicMap();
		this.builder = tm.getBuilder();
		this.util = new TopicMapUtil( tm );
	}

	public String getName() {
		return notNull( TopicStringifiers.getDefaultStringifier().toString( topic ) );
	}

	public void setName( String name ) {
		removeNames();
		builder.makeTopicName( topic, name );
	}

	protected String getOccurrenceValue( String psi ) {
		return util.getOccurrenceValueByType( topic, psi );
	}

	protected void setOccurrenceValue( String occurrenceTypePsi, String value ) {
		removeOccurrenceValues( occurrenceTypePsi );
		builder.makeOccurrence( topic, util.getTopicByPsi( occurrenceTypePsi ), value );
	}

	@SuppressWarnings("unchecked")
	protected void removeNames() {
		Iterator<TopicNameIF> names = topic.getTopicNames().iterator();
		while (names.hasNext()) {
			names.next().remove();
		}
	}

	protected void removeOccurrenceValues( String occurrenceTypePsi ) {
		for (OccurrenceIF occurrence : util.getOccurrencesByType( topic, occurrenceTypePsi )) {
			occurrence.remove();
		}
	}

	protected final String notNull( String string ) {
		return string == null ? "" : string;
	}

	public List<TopicIF> getAssociatedTopics( String associationTypePsi, String myRoleTypePsi, String wantedRoleTypePsi ) {
		return util.findBinaryPlayers( associationTypePsi, topic, myRoleTypePsi, wantedRoleTypePsi );
	}

	public TopicIF getAssociatedTopic( String associationTypePsi, String myRoleTypePsi, String wantedRoleTypePsi ) {
		List<TopicIF> topics = getAssociatedTopics( associationTypePsi, myRoleTypePsi, wantedRoleTypePsi );
		if (topics.size() == 0) return null;
		return topics.get( 0 );
	}

	public TopicMapObject getAssociatedTopicMapObject( String associationTypePsi, String myRoleTypePsi, String wantedRoleTypePsi ) {
		TopicIF associatedTopic = getAssociatedTopic( associationTypePsi, myRoleTypePsi, wantedRoleTypePsi );
		return TopicMapObjectRepository.getInstance().createInstance( associatedTopic );
	}

	public List<TopicMapObject> getAssociatedTopicMapObjects( String associationTypePsi, String myRoleTypePsi, String wantedRoleTypePsi ) {
		return getAssociatedTopicMapObjects( TopicMapObject.class, associationTypePsi, myRoleTypePsi, wantedRoleTypePsi );
	}

	public <T extends TopicMapObject> List<T> getAssociatedTopicMapObjects( Class<T> theclass, String associationTypePsi,
			String myRoleTypePsi, String wantedRoleTypePsi ) {
		List<TopicIF> topics = getAssociatedTopics( associationTypePsi, myRoleTypePsi, wantedRoleTypePsi );
		return TopicMapObjectRepository.getInstance().createInstances( theclass, topics );
	}

	public void associateWithTopic( TopicMapObject otherTopic, String associationTypePsi, String myRolePsi, String otherRolePsi ) {
		new AssociationBuilder( tm ).createAssociation( associationTypePsi ).addRole( topic, myRolePsi ).addRole( otherTopic,
				otherRolePsi );
	}

	public TopicIF getTopic() {
		return topic;
	}

	public String getDescription() {
		return getOccurrenceValue( "http://psi.ontopia.net/ontology/description" );
	}

	@Override
	public String toString() {
		return getName();
	}

}
