package no.delfidata.topicmaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ontopia.infoset.core.LocatorIF;
import net.ontopia.infoset.impl.basic.URILocator;
import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.utils.ObjectUtils;

@SuppressWarnings("unchecked")
public class TopicMapUtil {
	private final TopicMapIF tm;
	private static final Map<String, String> replacements;

	static {
		replacements = new HashMap<String, String>();
		replacements.put( "ont:", "http://psi.ontopia.net/ontology/" );
		replacements.put( "ex:", "http://psi.example.org/" );
	}

	public TopicMapUtil( TopicMapIF tm ) {
		this.tm = tm;
	}

	public String getSortName( TopicIF topic ) {
		for (Iterator<TopicNameIF> names = topic.getTopicNames().iterator(); names.hasNext();) {
			TopicNameIF name = names.next();
			if (hasSubjectIdentifier( name.getType(), "http://www.topicmaps.org/xtm/1.0/core.xtm#sort" )) {
				return name.getValue();
			}
		}
		return "";
	}

	public String getOccurrenceValueByType( TopicIF topic, String psi ) {
		OccurrenceIF occurrence = getOccurrenceByType( topic, psi );
		return occurrence == null ? null : occurrence.getValue();
	}

	public OccurrenceIF getOccurrenceByType( TopicIF topic, String psi ) {
		List<OccurrenceIF> occurrences = getOccurrencesByType( topic, psi );
		if (occurrences.size() == 0) return null;
		return occurrences.get( 0 );
	}

	protected List<OccurrenceIF> getOccurrencesByType( TopicIF topic, String psi ) {
		List<OccurrenceIF> result = new ArrayList<OccurrenceIF>();
		TopicIF wantedOccurrenceType = getTopicByPsi( psi );
		for (Iterator iterator = topic.getOccurrences().iterator(); iterator.hasNext();) {
			OccurrenceIF occurrence = (OccurrenceIF)iterator.next();
			if (wantedOccurrenceType.equals( occurrence.getType() )) {
				result.add( occurrence );
			}
		}
		return result;
	}

	public List<String> getOccurrenceValuesByType( TopicIF topic, String psi ) {
		List<OccurrenceIF> occurrences = getOccurrencesByType( topic, psi );
		List<String> result = new ArrayList<String>( occurrences.size() );
		for (OccurrenceIF occurrence : occurrences) {
			result.add( occurrence.getValue() );
		}
		return result;
	}

	public String getOccurrenceValueByDatatype( TopicIF topic, String datatypePsi ) {
		LocatorIF datatype = URILocator.create( datatypePsi );
		Iterator occurences = topic.getOccurrences().iterator();
		while (occurences.hasNext()) {
			OccurrenceIF occurence = (OccurrenceIF)occurences.next();
			if (datatype.equals( occurence.getDataType() )) {
				return occurence.getValue();
			}
		}
		return null;
	}

	public TopicIF getSupertype( TopicIF type ) {
		TopicIF superclassSubclass = getTopicByPsi( "http://psi.ontopia.net/ontology/superclass-subclass" );
		TopicIF superclassRole = getTopicByPsi( "http://psi.ontopia.net/ontology/superclass" );
		TopicIF subclassRole = getTopicByPsi( "http://psi.ontopia.net/ontology/subclass" );
		return findBinaryPlayer( superclassSubclass, type, subclassRole, superclassRole );
	}

	public TopicIF findBinaryPlayer( String associationTypePsi, TopicIF player, String nearRolePsi, String farRolePsi ) {
		return findBinaryPlayer( associationTypePsi, player, nearRolePsi, farRolePsi, null );
	}

	public TopicIF findBinaryPlayer( String associationTypePsi, TopicIF player, String nearRolePsi, String farRolePsi, TopicIF theme ) {
		TopicIF aType = getTopicByPsi( associationTypePsi );
		TopicIF near = getTopicByPsi( nearRolePsi );
		TopicIF far = getTopicByPsi( farRolePsi );
		return findBinaryPlayer( aType, player, near, far );
	}

	public TopicIF findBinaryPlayer( TopicIF aType, TopicIF player1, TopicIF rType1, TopicIF rType2 ) {
		return findBinaryPlayer( aType, player1, rType1, rType2, null );
	}

	public TopicIF findBinaryPlayer( TopicIF aType, TopicIF player1, TopicIF rType1, TopicIF rType2, TopicIF theme ) {
		Iterator iter = player1.getRolesByType( rType1, aType ).iterator();
		while (iter.hasNext()) {
			AssociationRoleIF role1 = (AssociationRoleIF)iter.next();
			AssociationIF assoc = role1.getAssociation();
			Collection scope = assoc.getScope();
			if (theme != null && !(scope.size() == 1 && scope.contains( theme ))) continue;
			if (theme == null && scope.size() > 0) continue;
			Collection roles = assoc.getRoles();
			if (roles.size() != 2) continue;
			Iterator riter = roles.iterator();
			while (riter.hasNext()) {
				AssociationRoleIF role2 = (AssociationRoleIF)riter.next();
				if (ObjectUtils.different( role1, role2 )) {
					return role2.getPlayer();
				}
			}
		}
		return null;
	}

	public List<TopicIF> findBinaryPlayers( TopicIF associationType, TopicIF player, TopicIF nearRole, TopicIF farRole ) {
		return findBinaryPlayers( associationType, player, nearRole, farRole, null );
	}

	public List<TopicIF> findBinaryPlayers( String associationTypePsi, TopicIF player, String nearRolePsi, String farRolePsi ) {
		return findBinaryPlayers( associationTypePsi, player, nearRolePsi, farRolePsi, null );
	}

	public List<TopicIF> findBinaryPlayers( String associationTypePsi, TopicIF player, String nearRolePsi, String farRolePsi,
			TopicIF theme ) {
		TopicIF aType = getTopicByPsi( associationTypePsi );
		TopicIF near = getTopicByPsi( nearRolePsi );
		TopicIF far = getTopicByPsi( farRolePsi );
		return findBinaryPlayers( aType, player, near, far, theme );
	}

	public List<TopicIF> findBinaryPlayers( TopicIF associationType, TopicIF player, TopicIF playerRoleType, TopicIF wantedRoleType,
			TopicIF theme ) {
		List<TopicIF> result = new ArrayList<TopicIF>();
		Collection<AssociationRoleIF> playerRoles = player.getRolesByType( playerRoleType, associationType );
		for (AssociationRoleIF role1 : playerRoles) {
			AssociationIF assoc = role1.getAssociation();
			Collection scope = assoc.getScope();
			if (theme != null && !(scope.size() == 1 && scope.contains( theme ))) continue;
			if (theme == null && scope.size() > 0) continue;
			Collection<AssociationRoleIF> roles = assoc.getRoles();
			if (roles.size() != 2) continue;
			for (AssociationRoleIF role2 : roles) {
				if (ObjectUtils.different( role1, role2 )) {
					result.add( role2.getPlayer() );
				}
			}
		}
		return result;
	}

	public TopicIF getTopicByPsi( String psi ) {
		psi = doReplacements( psi );
		return tm.getTopicBySubjectIdentifier( URILocator.create( psi ) );
	}

	public boolean instanceOf( TopicIF topic, String psi ) {
		Iterator types = topic.getTypes().iterator();
		if (!types.hasNext()) return false;
		TopicIF topicType = (TopicIF)types.next();
		while (null != topicType) {
			if (hasSubjectIdentifier( topicType, psi )) return true;
			topicType = getSupertype( topicType );
		}
		return false;
	}

	public TopicIF[] filterByTopicType( TopicIF[] topics, String typePsi ) {
		List<TopicIF> result = new ArrayList<TopicIF>( topics.length );
		for (int i = 0; i < topics.length; i++) {
			TopicIF topic = topics[i];
			if (instanceOf( topic, typePsi )) {
				result.add( topic );
			}
		}
		return result.toArray( new TopicIF[result.size()] );
	}

	public TopicIF getTopicBySubjectLocator( String locator ) {
		return tm.getTopicBySubjectLocator( URILocator.create( locator ) );
	}

	public boolean isUnaryAssociationSet( TopicIF topic, String unaryAssociationRolePsi ) {
		TopicIF unaryAssociationRole = getTopicByPsi( unaryAssociationRolePsi );
		Collection roles = topic.getRolesByType( unaryAssociationRole );
		return roles.size() > 0;
	}

	public String doReplacements( String input ) {
		Iterator entries = replacements.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry)entries.next();
			String key = (String)entry.getKey();
			if (input.startsWith( key )) {
				input = input.replaceAll( "^" + key, (String)entry.getValue() );
				return input;
			}
		}
		return input;
	}

	public boolean hasSubjectIdentifier( TopicIF topic, String psi ) {
		psi = doReplacements( psi );
		if (topic == null) return false;
		Iterator psis = topic.getSubjectIdentifiers().iterator();
		while (psis.hasNext()) {
			LocatorIF thisPsi = (LocatorIF)psis.next();
			if (psi.equals( thisPsi.getAddress() )) return true;
		}
		return false;
	}

	/**
	 * Ensures that the given topic is associated with the right topic map
	 * 
	 * @param topic The topic
	 * @return the same topic if the topic exists in this.tm, otherwise a new TopicIF with the same ObjectId in this.tm.
	 */
	public TopicIF getTopicInTopicMap( TopicIF topic ) {
		if (topic.getTopicMap() == tm) return topic;
		return (TopicIF)tm.getObjectById( topic.getObjectId() );
	}

	public TopicIF getRolePlayer( AssociationIF association, TopicIF role ) {
		Iterator roles = association.getRolesByType( role ).iterator();
		if (!roles.hasNext()) return null;
		return ((AssociationRoleIF)roles.next()).getPlayer();
	}
}
