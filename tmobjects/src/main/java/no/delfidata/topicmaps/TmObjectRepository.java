package no.delfidata.topicmaps;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.ontopia.infoset.core.LocatorIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;

public class TmObjectRepository {

	private final Map<String, Class<? extends TmObject>> classes = new HashMap<String, Class<? extends TmObject>>();
	private final TopicMapUtil util;

	public TmObjectRepository( TopicMapIF tm ) {
		this.util = new TopicMapUtil( tm );
	}

	public TmObject getByPsi( String psi ) {
		TopicIF topic = util.getTopicByPsi( psi );
		return createInstance( topic );
	}

	public void addClass( Class<? extends TmObject> theClass ) {
		try {
			Field field = theClass.getField( "PSI" );
			String psi = (String)field.get( null );
			addClass( psi, theClass );
		} catch (Exception e) {
			throw new IllegalArgumentException( "Class " + theClass.getName()
					+ " lacks a public static String PSI. Use addClass(psi, theClass) instead" );
		}
	}

	public void addClass( String psi, Class<? extends TmObject> theClass ) {
		classes.put( psi, theClass );
	}

	private TmObject createInstance( TopicIF topic ) {
		Class<? extends TmObject> targetClass = identifyTargetClass( topic );
		return createInstanceOfTargetClass( topic, targetClass );
	}

	private Class<? extends TmObject> identifyTargetClass( TopicIF topic ) {
		TopicIF type = (TopicIF)topic.getTypes().iterator().next();
		while (type != null) {
			String psi = ((LocatorIF)type.getSubjectIdentifiers().iterator().next()).getAddress();
			Class<? extends TmObject> targetClass = classes.get( psi );
			if (null != targetClass) return targetClass;
			type = util.getSupertype( type );
		}
		throw new IllegalArgumentException( "No class has been added for topic: " + topic );
	}

	private TmObject createInstanceOfTargetClass( TopicIF topic, Class<? extends TmObject> targetClass ) {
		try {
			Constructor<? extends TmObject> constructor = targetClass.getConstructor( new Class[] { TopicIF.class } );
			return constructor.newInstance( new Object[] { topic } );
		} catch (Exception e) {
			throw new RuntimeException( e.getMessage(), e );
		}
	}
}
