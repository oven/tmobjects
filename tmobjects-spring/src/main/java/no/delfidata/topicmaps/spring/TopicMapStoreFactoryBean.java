package no.delfidata.topicmaps.spring;

import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;

import org.springframework.beans.factory.FactoryBean;

public class TopicMapStoreFactoryBean implements FactoryBean {

	private String topicMapId;
	private boolean readOnly;

	@Override
	public Object getObject() throws Exception {
		return TopicMaps.createStore( topicMapId, readOnly );
	}

	public void setTopicMapId( String value ) {
		this.topicMapId = value;
	}

	public void setReadOnly( boolean value ) {
		this.readOnly = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getObjectType() {
		return TopicMapStoreIF.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
