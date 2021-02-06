/*
 * This is the class file containing the "main()" which processes the command
 * line inputs.
 * 
 * ADDITIONAL FUNCTIONALITIES ADDED/ CODE IMPROVED:
 * 
 * 1. CASE INDEPENDENT COMMANDS: users can now type in commands without 
 *    worrying about wheather it is in uppercase or lowercase.
 *    
 * 2. TODO-LIST DELETION: Users can now delete the whole todo-list and start
 *    again from the beginning
 *    Syntax: ./todo delete-todolist
 *    
 * 3. DOWNLOAD: Users can now download their todo by providing the location path
 *    Syntax: ./todo download-todo LOCATION/PATH
 *    Example:  ./todo download-todo C:\Users\mail2\OneDrive\Desktop\folder
 * 
 * @author Md. Nawaz Rahaman
 * @project Coronasafe Engineering Fellowship task
 * @since 2020-12-26
 */

import java.io.IOException;

public class Todo {
	public static void main(String args[]) throws IOException {
		HelperTodo obj = new HelperTodo();
		if (args.length != 0) {
			String cmd = args[0].toLowerCase();
			switch (cmd) {
			case "add":
				if (args.length == 1) {
					System.out.println("Error: Missing todo string. Nothing added!\n");
				} else {
					obj.addTodo(args[1]);
				}
				break;
			case "del":
				if (args.length == 1) {
					System.out.printf("Error: Missing NUMBER for deleting todo.\n");
				} else {
					obj.deleteTodo(Integer.parseInt(args[1]));
				}
				break;
			case "help":
				obj.help();
				break;
			case "ls":
				obj.showRemainingTodo();
				break;
			case "done":
				if (args.length == 1) {
					System.out.printf("Error: Missing NUMBER for marking todo as done.\n");
				} else {
					obj.completeTodo(Integer.parseInt(args[1]));
				}
				break;
			case "report":
				obj.report();
				break;
			case "delete-todolist":
				obj.deleteWholeTodo();
				break;
			case "download-todo":
				obj.download(args[1]);
				break;
			default:
				System.out.println("Sorry, no such command exists!");
			}
		} else {
			obj.help();
		}

	}
}
