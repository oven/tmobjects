package no.delfidata.topicmaps.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import no.delfidata.topicmaps.TopicMapObjectRepository;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class OpenSessionInViewFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
			throws ServletException, IOException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext( getServletContext() );
		TopicMapObjectRepository repository = (TopicMapObjectRepository)context.getBean( "repository" );
		TopicMapStoreIF store = (TopicMapStoreIF)context.getBean( "topicMapStore" );
		TopicMapIF tm = store.getTopicMap();
		repository.setTopicMap( tm );
		request.setAttribute( "repository", repository );
		request.setAttribute( "tm", tm );

		try {
			filterChain.doFilter( request, response );
			store.commit();
		} catch (Exception e) {
			store.abort();
		} finally {
			store.close();
		}
	}
}
