package ufrj.museunacional.testedemeteorito;

public class Question {
	
	public static final int AFFIRMATIVE_LINK = 0;
	public static final int NEGATIVE_LINK = -1;
	
	private int number;
	private String title;
	private String text;
	private String image;
	private int yes_link;
	private int no_link;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getYes_link() {
		return yes_link;
	}
	public void setYes_link(int yes_link) {
		this.yes_link = yes_link;
	}
	public int getNo_link() {
		return no_link;
	}
	public void setNo_link(int no_link) {
		this.no_link = no_link;
	}
	
}
