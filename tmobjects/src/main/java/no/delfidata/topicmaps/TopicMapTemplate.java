package no.delfidata.topicmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;

public class TopicMapTemplate {

	private final TopicMapIF tm;

	public TopicMapTemplate( TopicMapIF tm ) {
		this.tm = tm;
	}

	public List<TopicIF> queryForArray( String query, Map<String, ?> params ) {
		QueryProcessorIF queryProcessor = QueryUtils.getQueryProcessor( tm );
		QueryResultIF queryresult = null;
		List<TopicIF> result = new ArrayList<TopicIF>();
		try {
			queryresult = queryProcessor.execute( query, params );
			while (queryresult.next()) {
				result.add( (TopicIF)queryresult.getValue( 0 ) );
			}
			return result;
		} catch (InvalidQueryException e) {
			throw new RuntimeException( query, e );
		} finally {
			if (null != queryresult) queryresult.close();
		}
	}
}
