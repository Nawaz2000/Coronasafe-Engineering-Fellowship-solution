/*
 * This class is a simple POJO which defines the structure of any todo 
 * in this application. At present it contains only "note" but certain
 * variables can be added to enhance control and functionality
 * 
 * @author Md. Nawaz Rahaman
 * @project Coronasafe Engineering Fellowship task
 * @since 2020-12-26
 */

public class TodoStructure {
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
