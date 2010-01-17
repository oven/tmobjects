package no.delfidata.topicmaps;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import net.ontopia.infoset.core.LocatorIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;

public class TopicMapObjectRepository {

	private final Map<String, Class<? extends TopicMapObject>> classes = new HashMap<String, Class<? extends TopicMapObject>>();
	private TopicMapUtil util;

	public TopicMapObjectRepository() {}

	public TopicMapObjectRepository( TopicMapIF tm ) {
		setTopicMap( tm );
	}

	public void setTopicMap( TopicMapIF tm ) {
		this.util = new TopicMapUtil( tm );
	}

	public TopicMapObject getByPsi( String psi ) {
		TopicIF topic = util.getTopicByPsi( psi );
		if (null == topic) throw new TopicNotFoundException( psi );
		return createInstance( topic );
	}

	public void addClass( Class<? extends TopicMapObject> theClass ) {
		String psi = getPsiFromClass( theClass );
		addClass( psi, theClass );
	}

	public void addClass( String psi, Class<? extends TopicMapObject> theClass ) {
		classes.put( psi, theClass );
	}

	private String getPsiFromClass( Class<? extends TopicMapObject> theClass ) {
		try {
			return (String)theClass.getField( "PSI" ).get( null );
		} catch (Exception e) {
			throw new IllegalArgumentException( "Class " + theClass.getName()
					+ " lacks a public static String PSI. Use addClass(psi, theClass) instead" );
		}
	}

	private TopicMapObject createInstance( TopicIF topic ) {
		Class<? extends TopicMapObject> targetClass = identifyTargetClass( topic );
		return createInstanceOfTargetClass( topic, targetClass );
	}

	private Class<? extends TopicMapObject> identifyTargetClass( TopicIF topic ) {
		TopicIF type = (TopicIF)topic.getTypes().iterator().next();
		while (type != null) {
			String psi = ((LocatorIF)type.getSubjectIdentifiers().iterator().next()).getAddress();
			Class<? extends TopicMapObject> targetClass = classes.get( psi );
			if (null != targetClass) return targetClass;
			type = util.getSupertype( type );
		}
		throw new IllegalArgumentException( "No class has been added for topic: " + topic );
	}

	private TopicMapObject createInstanceOfTargetClass( TopicIF topic, Class<? extends TopicMapObject> targetClass ) {
		try {
			Constructor<? extends TopicMapObject> constructor = targetClass.getConstructor( new Class[] { TopicIF.class } );
			return constructor.newInstance( new Object[] { topic } );
		} catch (Exception e) {
			throw new RuntimeException( e.getMessage(), e );
		}
	}

	public void setClasses( Map<String, Class<? extends TopicMapObject>> classes ) {
		this.classes.clear();
		this.classes.putAll( classes );
	}
}
