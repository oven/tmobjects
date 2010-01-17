package no.delfidata.topicmaps.examples.gui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.delfidata.topicmaps.TopicMapObject;
import no.delfidata.topicmaps.TopicMapObjectRepository;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloWorldController implements Controller {

	private String viewName;

	public void setViewName( String viewName ) {
		this.viewName = viewName;
	}

	@Override
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Map model = new HashMap();
		model.put( "foo", new Date() );

		TopicMapObjectRepository repository = (TopicMapObjectRepository)request.getAttribute( "repository" );
		TopicMapObject topic = repository.getByPsi( "http://psi.example.org/AHardDaysNightAlbum" );

		model.put( "topic", topic );
		ModelAndView mav = new ModelAndView( viewName );
		mav.addAllObjects( model );
		return mav;
	}

}
