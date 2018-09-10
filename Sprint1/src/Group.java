import java.util.Date;

public class Group {
	Date dateCreated;
	String title;
	String description;
	
	public Group(String title, String desc, Date date) {
		this.title = title;
		this.description = desc;
		this.dateCreated = date;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
