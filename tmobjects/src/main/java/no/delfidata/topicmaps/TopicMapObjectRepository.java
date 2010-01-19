package no.delfidata.topicmaps;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ontopia.infoset.core.LocatorIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;

public class TopicMapObjectRepository {

	private static ThreadLocal<TopicMapObjectRepository> threadBoundInstance = new ThreadLocal<TopicMapObjectRepository>();
	private final Map<String, Class<? extends TopicMapObject>> classes = new HashMap<String, Class<? extends TopicMapObject>>();
	private TopicMapUtil util;

	public TopicMapObjectRepository() {}

	public TopicMapObjectRepository( TopicMapIF tm ) {
		setTopicMap( tm );
	}

	public void setTopicMap( TopicMapIF tm ) {
		this.util = new TopicMapUtil( tm );
	}

	public static void setInstance( TopicMapObjectRepository repository ) {
		threadBoundInstance.set( repository );
	}

	public static TopicMapObjectRepository getInstance() {
		return threadBoundInstance.get();
	}

	public TopicMapObject createInstance( TopicIF topic ) {
		Class<? extends TopicMapObject> targetClass = identifyTargetClass( topic );
		return createInstanceOfTargetClass( topic, targetClass );
	}

	@SuppressWarnings("unchecked")
	public <T extends TopicMapObject> List<T> createInstances( Class<T> theclass, List<TopicIF> topics ) {
		List<T> result = new ArrayList<T>( topics.size() );
		for (TopicIF topic : topics) {
			result.add( (T)createInstance( topic ) );
		}
		return result;
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
		Psi annotation = theClass.getAnnotation( Psi.class );
		if (null != annotation) return annotation.value();

		throw new IllegalArgumentException( "Class " + theClass.getName()
				+ " lacks a @Psi annotation. Use addClass(psi, theClass) instead" );
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

	public void setClasses( List<Class<? extends TopicMapObject>> classes ) {
		this.classes.clear();
		for (Class<? extends TopicMapObject> theClass : classes) {
			addClass( theClass );
		}
	}
}
