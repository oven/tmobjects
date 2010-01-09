package no.delfidata.topicmaps;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;

public class AssociationBuilder {
	private final TopicMapUtil util;
	private final TopicMapBuilderIF builder;
	private final TopicMapIF tm;

	/**
	 * Creates a new association in the topic map
	 * 
	 * @param associationTypePsi The association type
	 * @param tm The topic map to update
	 */
	public AssociationBuilder( TopicMapIF tm ) {
		this.tm = tm;
		builder = tm.getBuilder();
		this.util = new TopicMapUtil( tm );
	}

	public Association createAssociation( String psi ) {
		TopicIF menuAssoc = util.getTopicByPsi( psi );
		return new Association( menuAssoc );
	}

	class Association {
		private final AssociationIF association;

		private Association( TopicIF topic ) {
			this.association = builder.makeAssociation( topic );
		}

		public void setScope( String scopePsi ) {
			association.addTheme( util.getTopicByPsi( scopePsi ) );
		}

		public Association addRole( TopicIF topic, String roleTypePsi ) {
			topic = util.getTopicInTopicMap( topic );
			TopicMapBuilderIF builder = tm.getBuilder();
			TopicIF roleType = util.getTopicByPsi( roleTypePsi );
			builder.makeAssociationRole( association, roleType, topic );
			return this;
		}

		public Association addRole( String topicPsi, String roleTypePsi ) {
			return addRole( util.getTopicByPsi( topicPsi ), roleTypePsi );
		}

		public Association addRole( TopicMapObject otherTopic, String roleTypePsi ) {
			return addRole( otherTopic.getTopic(), roleTypePsi );
		}
	}
}
