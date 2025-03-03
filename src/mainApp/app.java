package mainApp;

import java.util.List;
import java.util.Scanner;

import auth.UserAuth;
import todo.TodoManager;

public class app {
	public static void main(String[] args) {
		String loggedInUser = "";
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (loggedInUser.equals("")) {
				int choice = getLoginMenu(sc);
				if (choice != 1 && choice != 2) {
					showTqMessage();
					break;
				} else if (choice == 1) {
					registerUserMenu(sc);
				} else {
					loggedInUser = loginUserMenu(sc);
				}
			} else {
				int choice = loggedInMenu(sc, loggedInUser);
				if (choice == -1) {
					loggedInUser = "";
				}
			}
			
		}

	}

	public static int getLoginMenu(Scanner sc) {
		System.out.println(
				"Welcome to Your Personal Todo List!!!\n Choose the option\n1\tRegister User\n2\tLogin User\nother\tExit");
		int choice = sc.nextInt();
		sc.nextLine();
		System.out.println("------------------------------------------------------------------------------------");
		return choice;
	}

	public static void registerUserMenu(Scanner sc) {
		System.out.println("Register as a new User!!!");
		System.out.println("Enter username:");
		String username = sc.nextLine();
		System.out.println("Enter password:");
		String password = sc.nextLine();
//		System.out.println("Username is:"+username+"Password is:"+password+"Length of pass:"+password.length());
		UserAuth.registerUser(username, password);
		
	}
	public static String loginUserMenu(Scanner sc) {
		System.out.println("Login with your credentials!!!");
		System.out.println("Enter username:");
		String username = sc.nextLine();
		System.out.println("Enter password:");
		String password = sc.nextLine();
		boolean status = UserAuth.loginUser(username, password);
		System.out.println("------------------------------------------------------------------------------------");
		if(status) {
			return username;
		}
		return "";
	}

	public static int loggedInMenu(Scanner sc, String username) {
		while (true) {
			System.out.println("1\tGet Tasks\n2\tAdd New Task\n3\tModify Task\n\4\tRemove Task\nOther\tLogout");
			int choice = sc.nextInt();
			sc.nextLine();
			System.out.println("------------------------------------------------------------------------------------");
			if (choice == 1) {
				showTasks(TodoManager.getTasks(username));
			} else if (choice == 2) {
				while (true) {
					System.out.println("Enter Task Name:");
					String taskName = sc.nextLine();
					if (!taskName.equals("")) {
						boolean res = TodoManager.addTask(username, taskName);
						if (res) {
							System.out.println("Task added Successfully!");
							break;
						} else {
							System.out.println("Please retry");
						}
					}
					System.out.println("------------------------------------------------------------------------------------");
				}
			} else if (choice == 3) {
				while (true) {
					showTasks(TodoManager.getTasks(username));
					System.out.println("Enter taskId you want to modify:");
					int taskId = sc.nextInt();
					List<String> existingData = TodoManager.getTask(username, taskId);
					if (existingData.size() == 0) {
						System.out.println("Enter Correct TaskId!");
						break;
					} else {
						System.out.println("Enter column you want to modify:\n1\tTask Name\n2\tStatus\n3\tmodify both");
						int modifyChoice = sc.nextInt();
						String taskName = existingData.get(0);
						String status = existingData.get(1);
						sc.nextLine();
						if (modifyChoice == 1 || modifyChoice == 3) {
							System.out.println("Enter new Task Name:");
							taskName = sc.nextLine();
						}
						if (modifyChoice == 2 || modifyChoice == 3) {
							System.out.println("Enter new Task Status:");
							status = sc.nextLine();
						}
						if (modifyChoice > 3 && modifyChoice < 1) {
							System.out.println("Enter Correct Choice!");
							break;
						} else {
							boolean res = TodoManager.modifyTask(username, taskId, taskName, status);
							if (res) {
								System.out.println("Task modified Successfully!");
								break;
							}
							else {
								System.out.println("Something went wrong,please try again!");
							}
						}
					}
					System.out.println("------------------------------------------------------------------------------------");
				}
			} else if (choice == 4) {
				while (true) {
					showTasks(TodoManager.getTasks(username));
					System.out.println("Enter taskId you want to delete:");
					int taskId = sc.nextInt();
					sc.nextLine();
					boolean res = TodoManager.deleteTask(username, taskId);
					if (res) {
						System.out.println("Task deleted Successfully!");
						break;
					}
					System.out.println("------------------------------------------------------------------------------------");
				}
			}
			else break;

		}
		return -1;
	}

	public static void showTqMessage() {
		System.out.println("Thank you for using our app!");
		System.out.println("------------------------------------------------------------------------------------");
	}
	public static void showTasks(List<String> tasks) {
		System.out.println("Task Id\tTask Name\tStatus\tCreatedAt");
		for(int i=0;i<tasks.size();i++) {
			System.out.println(tasks.get(i));
		}
		System.out.println("------------------------------------------------------------------------------------");
	}
}
