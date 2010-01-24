package no.delfidata.topicmaps.examples.gui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import no.delfidata.topicmaps.TopicMapObjectRepository;
import no.delfidata.topicmaps.domain.Album;
import no.delfidata.topicmaps.domain.Artist;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("unchecked")
public class CreateAlbumController extends SimpleFormController {

	@Override
	protected Map referenceData( HttpServletRequest request ) throws Exception {
		Artist artist = (Artist)TopicMapObjectRepository.getInstance().getById( request.getParameter( "artistId" ) );
		Map model = new HashMap();
		model.put( "artist", artist );
		return model;
	}

	@Override
	protected ModelAndView onSubmit( Object command ) throws Exception {
		CreateAlbumCommand form = (CreateAlbumCommand)command;

		Artist artist = (Artist)TopicMapObjectRepository.getInstance().getById( form.getArtistId() );
		Album newAlbum = artist.createAlbum();
		newAlbum.setName( form.getName() );
		newAlbum.setDescription( form.getDescription() );
		newAlbum.setCover( form.getCover() );
		return new ModelAndView( getSuccessView() + "?id=" + artist.getId() );
	}

	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		CreateAlbumCommand result = new CreateAlbumCommand();
		result.setArtistId( request.getParameter( "artistId" ) );
		return result;
	}

}

class CreateAlbumCommand {
	private String name, description, cover, artistId;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId( String value ) {
		artistId = value;
	}

	public void setCover( String cover ) {
		this.cover = cover;
	}

	public String getCover() {
		return cover;
	}
}
