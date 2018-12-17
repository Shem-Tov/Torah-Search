package console;

import java.util.ArrayList;

public class MenuOptionClass {
	private ArrayList<String> messages;
	
	MenuOptionClass(String... strings){
		messages = new ArrayList<String>();
		for (String str:strings) {
			messages.add(str);
		}
	}
	
	public ArrayList<String> getMessages(){
		return messages;
	}
	
	public int size(){
		return messages.size();
	}
}
