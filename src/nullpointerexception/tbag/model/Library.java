package nullpointerexception.tbag.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import nullpointerexception.tbag.other.Pair;
import nullpointerexception.tbag.persist.ReadCSV;

public class Library {
	private ArrayList<String> usernames;
	private ArrayList<String> passwords;
	private Map<String, String> credentials;
	
	// create model - test version
	public Library() {
		usernames = new ArrayList<String>();
		passwords = new ArrayList<String>();
		credentials = new TreeMap<String, String>();
		
		loadUsers();
	}		
	
	public void loadUsers() {
		try {
			ReadCSV readUsers = new ReadCSV("users.csv");
			
			try {
				while (true) {
					List<String> userInfo = readUsers.next();
					if (userInfo == null) {
						break;
					}
					Iterator<String> i = userInfo.iterator();
					
					credentials.put(i.next(), i.next());
				}
			}
			finally {
				readUsers.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// login name - test version
	public boolean validateUserName(String name) {
		return credentials.containsKey(name);
	}

	// login credentials - test version
	public boolean validatePW(String name, String pw) {
		if (credentials.containsKey(name)) {
			if  (credentials.get(name).equals(pw)) {
				return true;
			}
		}			
		return false;
	}
	
	public String createNewUser(String name, String pw) {
		System.out.println("Creating new user data ... ");
		boolean alreadyExists = false;
		String statusMessage = "";
		
		ArrayList<Pair<String, String>> userData = getCurrentUserData();
		
		for (Pair<String, String> p : userData) {
			if (p.getLeft().equals(name)) {
				System.out.println("Is " + name + " == " + p.getLeft());
				statusMessage = "User already exists. ";
				alreadyExists = true;
				break;
			}
		}
		if (!alreadyExists) {
			userData.add(new Pair<String, String>(name, pw));
			
			writeData(userData);
			
			statusMessage = "Created new user data for '" + name + "!' ";
		}
		
		return statusMessage;
	}
	
	private ArrayList<Pair<String, String>> getCurrentUserData() {
		ArrayList<Pair<String, String>> userData = new ArrayList<Pair<String, String>>();
		
		File file;
		try {
			file = new File(this.getClass().getClassLoader().getResource("nullpointerexception/tbag/csvs/users.csv").toURI());
			
			try {
				Scanner scan = new Scanner(file);
				String data = "";
				StringTokenizer st;
				
				while (scan.hasNext()) {
					data = scan.nextLine();
					
					st = new StringTokenizer(data, ",");
					
					System.out.println("Adding '" + data + "' ... ");
					
					userData.add(new Pair<String, String>(st.nextToken(), st.nextToken()));
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} 
		catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		return userData;
	}
	
	private void writeData(ArrayList<Pair<String, String>> userData) {
		System.out.println("WriteData() called. ");
		try {
			FileWriter fw;
			
			try {
				fw = new FileWriter(new File(this.getClass().getClassLoader().getResource("nullpointerexception/tbag/csvs/users.csv").toURI()));
				
				System.out.println("Writing data to file ... ");
				
				for (Pair<String, String> p : userData) {
					System.out.println("Writing: '" + p.getLeft() + "," + p.getRight() + "' ... ");
					
					fw.write(p.getLeft() + "," + p.getRight() + "\n");
				}
				
				fw.close();
				
				System.out.println("Finished writing data. ");
			} 
			catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
