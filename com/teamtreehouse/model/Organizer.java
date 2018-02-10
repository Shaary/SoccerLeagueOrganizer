package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Organizer {
  private Map<String, String> mMenu;
  private Player[] mPlayers;
  private BufferedReader mReader;
  private List<Team> mTeamList;
  //private Map<Integer, Player> mMapPlayers;  
    
  public Organizer(Player[] players) {
    mPlayers = players;
    mTeamList = new ArrayList<Team>();
    mReader = new BufferedReader(new InputStreamReader(System.in));
    mMenu = new LinkedHashMap<String, String>();
    mMenu.put("create", "Create a new team for the season");
    mMenu.put("add", "Add a new player to a team");
    mMenu.put("remove", "Removes player from a team");
    mMenu.put("report", "View a report of a team by height");
    mMenu.put("balance", "View the League Balance Report");
    mMenu.put("roster", "View Roster");
    mMenu.put("quit", "Type 'quit' to finish the session");
    /*mMapPlayers = new TreeMap<Integer, Player>();
    int i = 1;
    for (Player player : mPlayers) {
      mMapPlayers.put(i, player);
      i++; 
    } */
  }
   
  public void run() {
    String choice = "";
    do {
      try {
       choice = promptAction();
       switch(choice) {
        case "create": 
          Team team = promptNewTeam();
          System.out.printf("You created team %s %n", team);
          mTeamList.add(team);
          break;
        case "add":
          if (!mTeamList.isEmpty()) {
            team = chooseTeam();
            Player player = choosePlayer();
            team.addPlayer(player,team);
          }
          break;
         case "remove":
          team = chooseTeam();
          Player player = choosePlayer(team);
          team.removePlayer(player);
          break;
         case "quit":
          System.out.println("Thanks for using the programm");
          break;
         case "report":
          team = chooseTeam();
          report(team);
          break; 
         case "balance":
          balanceReport();
          break;
         case "roster":
          roster();
         break;
         default:
         System.out.printf("Unknown choice:  '%s'. Try again.  %n%n%n",
                             choice);
                 
        }
      } catch(IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
      }
    } while(!choice.equals("quit")); 
  } 
  
  private Team chooseTeam () throws IOException {
    int i = 1;
    Collections.sort(mTeamList);
    for (Team team : mTeamList) {
      System.out.println(i + " " +team);
      i++;
    }
    System.out.println("Choose team #:   ");
    int choice = Integer.parseInt(mReader.readLine());
    System.out.printf("You chose: %s%n", mTeamList.get(choice - 1));
    System.out.println();
    return mTeamList.get(choice - 1);
  }
  
  private Player choosePlayer() throws IOException {
	Arrays.sort(mPlayers);
	int i = 1;
	for (Player player : mPlayers) {
		System.out.print(i + ") ");
		System.out.println(player);
		i++;
	}
    /*for (Map.Entry<Integer, Player> entry : mMapPlayers.entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue());
    } */
    System.out.println("---------------------------");
    System.out.println("Choose a player #:  ");
    int choice = Integer.parseInt(mReader.readLine());
    System.out.println();
    return mPlayers[choice - 1]; 
  } 
  
  private Player choosePlayer(Team team) throws IOException {
    Set<Player> listOfPlayers = team.getListOfPlayers();
    TreeMap<Integer, Player> mapList = new TreeMap<Integer, Player>();
    int i = 1;
    for (Player player : listOfPlayers) {
      System.out.println(i + " " +player);
      mapList.put(i, player);
      i++;
    }
    System.out.println("Choose a player #:  ");
    int choice = Integer.parseInt(mReader.readLine());
    System.out.println(); 
    return (mapList.get(choice));
    }
      
  private String promptAction() throws IOException {
    System.out.println("---------------------------");
    System.out.printf("Your options are: %n");
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    System.out.println("---------------------------");
    System.out.print("What do you want to do:   ");
    String choice = mReader.readLine();
    System.out.println();
    return choice.trim().toLowerCase();
  }
  
  
  private Team promptNewTeam() throws IOException {
    System.out.print("Enter the team's name:  ");
    String teamName = mReader.readLine();
    System.out.print("Enter the team's coach name:  ");
    String coachName = mReader.readLine();
    System.out.println();
    return (new Team(teamName, coachName));
  }
 private void report(Team team) {
   Set<Player> teamList = new TreeSet<Player> (team.getListOfPlayers());
   Map<String, ArrayList<String>> heightRange = new TreeMap<String, ArrayList<String>>();
   ArrayList<String> shorty = new ArrayList<String>();
   ArrayList<String> average = new ArrayList<String>();
   ArrayList<String> tall = new ArrayList<String>();
   
   for (Player player : teamList) {
	if ((player.getHeightInInches() >= 35) && (player.getHeightInInches() <= 40)) {
		shorty.add(player.getName());
	}
	if ((player.getHeightInInches() >= 41) && (player.getHeightInInches() <= 46)) {
		average.add(player.getName());
	}
	if ((player.getHeightInInches() >= 47) && (player.getHeightInInches() <= 50)) {
		tall.add(player.getName());
	}
   }
   heightRange.put("35-40 inches", shorty);
   heightRange.put("41-46 inches", average);
   heightRange.put("47-50 inches", tall);
   
   for (Map.Entry<String, ArrayList<String>> entry : heightRange.entrySet()) {
	      System.out.println(entry.getKey() + " " + entry.getValue());
   }
 }
 
 private void balanceReport() {
  for (Team team : mTeamList) {
    System.out.println(team.balance());
  }
 }
 private void roster() {
  for (Team team : mTeamList) {
    System.out.printf("Team %s coached by %s - ", team.getTeamName(), team.getCoachName());
    System.out.println(team.getListOfPlayers());
  }
 }
}
