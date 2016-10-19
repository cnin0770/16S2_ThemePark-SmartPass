package Entity;

/**
 * Entity class of Card
 * @version 1.0
 *
 */
public class Card {
	
	private String id;
	private String name;
	private String birthday;
	private String height;
	private String address;
	private String attracVisitHistory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAttracVisitHistory() {
		return attracVisitHistory;
	}

	public void setAttracVisitHistory(String attracVisitHistory) {
		this.attracVisitHistory = attracVisitHistory;
	}

}
