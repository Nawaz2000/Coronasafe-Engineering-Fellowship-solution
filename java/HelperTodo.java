/*
 * This class contains all the methods that are accessed by the "Todo.class".
 * After processing of the requests, the are handled here.
 * 
 * @author Md. Nawaz Rahaman
 * @project Coronasafe Engineering Fellowship task
 * @since 2020-12-26
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class HelperTodo {
	private FileWriter fileWriter;
	private TodoStructure todoStructure;
	private ArrayList<TodoStructure> todoList;// stores the contents of "todo.txt"
	private ArrayList<TodoStructure> doneList;// stores the contents of "done.txt"

	/*
	 * Apart from variable initializations and loading up of lists, the files if
	 * they don't exist are created here in the contructor.
	 */
	public HelperTodo() throws IOException {
		todoList = new ArrayList<>();
		doneList = new ArrayList<>();

		File file = new File("todo.txt");
		file.createNewFile();
		Scanner myReader = new Scanner(file);
		while (myReader.hasNextLine()) {
			todoStructure = new TodoStructure();
			String data = myReader.nextLine();
			todoStructure.setNote(data);
			todoList.add(todoStructure);
		}
		myReader.close();

		file = new File("done.txt");
		file.createNewFile();
		myReader = new Scanner(file);
		while (myReader.hasNextLine()) {
			todoStructure = new TodoStructure();
			String data = myReader.nextLine();
			todoStructure.setNote(data);
			doneList.add(todoStructure);
		}
		myReader.close();

	}

	/*
	 * responds to "./todo help"
	 */
	public void help() {
		System.out.printf("Usage :-\n");
		System.out.printf("$ ./todo add \"todo item\"  # Add a new todo\n");
		System.out.printf("$ ./todo ls               # Show remaining todos\n");
		System.out.printf("$ ./todo del NUMBER       # Delete a todo\n");
		System.out.printf("$ ./todo done NUMBER      # Complete a todo\n");
		System.out.printf("$ ./todo help             # Show usage\n");
		System.out.printf("$ ./todo report           # Statistics");

	}

	/*
	 * after each file operations, the files are updated here
	 */
	public void updateFile(String filename) throws IOException {
		fileWriter = new FileWriter(filename);
		fileWriter.flush();
		if (filename.startsWith("todo")) {
			for (int currIndex = 0; currIndex < todoList.size(); currIndex++) {
				String currText = todoList.get(currIndex).getNote();
				fileWriter.write(currText + "\n");
			}
		} else if (filename.startsWith("done")) {
			for (int currIndex = 0; currIndex < doneList.size(); currIndex++) {
				String currText = doneList.get(currIndex).getNote();
				fileWriter.write("x " + LocalDate.now() + " " + currText + "\n");
			}
		}
		fileWriter.close();
	}

	/*
	 * responds to "./todo add "todo item""
	 */
	public void addTodo(String note) throws IOException {
		todoStructure = new TodoStructure();
		todoStructure.setNote(note);
		if (!todoList.contains(todoStructure)) {
			todoList.add(todoStructure);
		}

		updateFile("todo.txt");
		System.out.printf("Added todo: \"" + note + "\"");
	}

	/*
	 * responds to "./todo ls"
	 */
	public void showRemainingTodo() {
		if (todoList.size() != 0) {
			for (int currTodo = todoList.size() - 1; currTodo >= 0; currTodo--) {
				System.out.printf("[" + (currTodo + 1) + "] " + todoList.get(currTodo).getNote() + "\n");
			}
		} else
			System.out.println("There are no pending todos!");
	}

	/*
	 * responds to "./todo del NUMBER"
	 */
	public void deleteTodo(int num) throws IOException {
		if (num <= todoList.size() && num > 0) {
			todoList.remove(num - 1);
			updateFile("todo.txt");
			System.out.printf("Deleted todo #" + num + "\n");
		} else {
			System.out.printf("Error: todo #" + num + " does not exist. Nothing deleted.\n");
		}
	}

	/*
	 * responds to "./todo done NUMBER"
	 */
	public void completeTodo(int num) throws IOException {
		if (num <= todoList.size() && num > 0) {
			todoStructure = todoList.get(num - 1);
			if (!doneList.contains(todoStructure)) {
				doneList.add(todoStructure);
			}
			updateFile("done.txt");
			todoList.remove(num - 1);
			updateFile("todo.txt");
			System.out.printf("Marked todo #" + num + " as done.\n");
		} else {
			System.out.printf("Error: todo #" + num + " does not exist.\n");
		}
	}

	/*
	 * responds to "./todo report"
	 */
	public void report() {
		System.out.printf(LocalDate.now() + " Pending : " + todoList.size() + " Completed : " + doneList.size() + "\n");
	}

	public void deleteWholeTodo() throws IOException {
		fileWriter = new FileWriter("todo.txt");
		fileWriter.flush();
		fileWriter.close();

		fileWriter = new FileWriter("done.txt");
		fileWriter.flush();
		fileWriter.close();

		System.out.println("Todo list deleted successfully!");
	}

	private void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
		} finally {
			inputStream.close();
			outputStream.close();
		}
	}

	public void download(String destination) throws IOException {
		File source = new File("todo.txt");
		File dest = new File(destination + "\\todo.txt");

		copyFileUsingStream(source, dest);

		source = new File("done.txt");
		dest = new File(destination + "\\done.txt");

		copyFileUsingStream(source, dest);

		System.out.println("Todo list downloaded successfully!");
	}
}
