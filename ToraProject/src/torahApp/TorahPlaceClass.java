package torahApp;

public class TorahPlaceClass {
	String Name;
	Boolean isStartBook;
	Boolean isStartPerek;
	Boolean isStartParasha;
	
	public TorahPlaceClass(String name, 
			Boolean startBook,
			Boolean startPerek,
			Boolean startParasha) {
		Name = name;
		isStartBook = startBook;
		isStartPerek = startPerek;
		isStartParasha = startParasha;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Boolean getIsStartBook() {
		return isStartBook;
	}
	public void setIsStartBook(Boolean isStartBook) {
		this.isStartBook = isStartBook;
	}
	public Boolean getIsStartPerek() {
		return isStartPerek;
	}
	public void setIsStartPerek(Boolean isStartPerek) {
		this.isStartPerek = isStartPerek;
	}
	public Boolean getIsStartParasha() {
		return isStartParasha;
	}
	public void setIsStartParasha(Boolean isStartParasha) {
		this.isStartParasha = isStartParasha;
	}
}
