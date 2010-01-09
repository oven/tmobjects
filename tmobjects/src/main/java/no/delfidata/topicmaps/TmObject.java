package no.delfidata.topicmaps;

import java.util.Iterator;
import java.util.List;

import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.utils.TopicStringifiers;

public abstract class TmObject {
	protected final TopicIF topic;
	protected final TopicMapBuilderIF builder;
	protected final TopicMapIF tm;
	protected final TopicMapUtil util;

	public TmObject( TopicIF topic ) {
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

}
