package no.delfidata.topicmaps.examples.gui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.delfidata.topicmaps.TopicMapObjectRepository;
import no.delfidata.topicmaps.domain.Artist;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@SuppressWarnings("unchecked")
public class ListArtistsController implements Controller {

	private String viewName;

	public void setViewName( String viewName ) {
		this.viewName = viewName;
	}

	@Override
	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Map model = new HashMap();

		TopicMapObjectRepository repository = TopicMapObjectRepository.getInstance();
		model.put( "artists", repository.getByType( Artist.class ) );

		ModelAndView mav = new ModelAndView( viewName );
		mav.addAllObjects( model );
		return mav;
	}

}
