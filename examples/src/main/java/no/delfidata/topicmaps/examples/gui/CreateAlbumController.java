package no.delfidata.topicmaps.examples.gui;

import javax.servlet.http.HttpServletRequest;

import no.delfidata.topicmaps.TopicMapObjectRepository;
import no.delfidata.topicmaps.domain.Album;
import no.delfidata.topicmaps.domain.Artist;

import org.springframework.web.servlet.mvc.SimpleFormController;

public class CreateAlbumController extends SimpleFormController {

	@Override
	protected void doSubmitAction( Object command ) throws Exception {
		Artist artist = (Artist)TopicMapObjectRepository.getInstance().getByPsi( "ex:the_beatles" );
		Album newAlbum = artist.createAlbum();
		CreateAlbumCommand form = (CreateAlbumCommand)command;
		newAlbum.setName( form.getName() );
		newAlbum.setDescription( form.getDescription() );
	}

	@Override
	protected Object formBackingObject( HttpServletRequest request ) throws Exception {
		return new CreateAlbumCommand();
	}

}

class CreateAlbumCommand {
	private String name, description;

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

}
