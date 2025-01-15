package com.tfg.futstats;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.futstats.models.User;
import com.tfg.futstats.models.League;
import com.tfg.futstats.models.Player;
import com.tfg.futstats.models.Team;
import com.tfg.futstats.models.TeamMatch;
import com.tfg.futstats.models.Match;
import com.tfg.futstats.repositories.UserRepository;
import com.tfg.futstats.services.RestService;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer {

	@Autowired
	private RestService s;

	@Autowired
	private UserRepository ur;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@PostConstruct
	void init() throws IOException {

		User a = new User("admin", passwordEncoder.encode("pass"), null, false, "[user]", "[admin]");
		ur.save(a);

		for (int i = 0; i < 10; i++) {

			User u = new User("user" + i, passwordEncoder.encode("pass"), null, false, "[user]");
			ur.save(u);

		}

		for (int i = 10; i < 20; i++) {

			User u = new User("admin" + i, passwordEncoder.encode("pass"), null, false, "[admin]");
			ur.save(u);
		}

		League league = new League("LaLiga", "Javier Tebas", "Española", null, false);
		s.saveLeague(league);

		Team realMadrid = new Team(league, "Real Madrid", 98, "Spain", "Carlo Ancelotti", "Davide Ancelotti",
				"Florentino Pérez", "Santiago Bernabéu", null, false);
		s.saveTeam(realMadrid);
		league.setTeam(realMadrid);

		Player player1 = new Player(league, realMadrid, "Vinicius Jr.", 23, "Brasileña", "Delantero", null, false );
		s.savePlayer(player1);
		league.setPlayer(player1);
		realMadrid.setPlayer(player1);

		Player player2 = new Player(league, realMadrid, "Antonio Rudiguer", 22, "Alemana", "Defensa", null, false );
		s.savePlayer(player2);
		league.setPlayer(player2);
		realMadrid.setPlayer(player2);

		Team barcelona = new Team(league, "FC Barcelona", 97, "Spain", "Xavi Hernández", "Óscar Hernández",
				"Joan Laporta", "Spotify Camp Nou", null, false);
		s.saveTeam(barcelona);
		league.setTeam(barcelona);

		Player player3 = new Player(league, barcelona, "Lionel Messi", 36, "Argentina", "Delantero", null, false );
		s.savePlayer(player3);
		league.setPlayer(player3);
		barcelona.setPlayer(player3);

		Player player4 = new Player(league, barcelona, "Lamine Yamal", 17, "Española", "MedioCampista", null, false );
		s.savePlayer(player4);
		league.setPlayer(player4);
		barcelona.setPlayer(player4);

		Match match = new Match(league, barcelona, realMadrid, barcelona.getName() + '-' + realMadrid.getName(), barcelona.getStadium());
		s.saveMatch(match);
		league.setMatch(match);
		s.updateTeamInfo(barcelona);
        s.updateTeamInfo(realMadrid);
		TeamMatch teamMatch1 = new TeamMatch(barcelona, match);
		match.setTeamMatch(teamMatch1);
		barcelona.setTeamMatch(teamMatch1);
		TeamMatch teamMatch2 = new TeamMatch(realMadrid, match);
		match.setTeamMatch(teamMatch2);
		realMadrid.setTeamMatch(teamMatch2);
		s.saveTeamMatch(teamMatch1);
		s.saveTeamMatch(teamMatch2);
		
		
		// League league1 = new League("Premier League", "Richard Masters", "Inglesa", null, false);
		// s.saveLeague(league1);

		// Team manchesterUnited = new Team(league1, "Manchester United", 66, "England", "Erik ten Hag",
		// 		"Mitchell van der Gaag", "Joel Glazer", "Old Trafford", null, false);
		// s.saveTeam(manchesterUnited);
		// league1.setTeam(manchesterUnited);

		// Player player5 = new Player(league1, manchesterUnited, "Garnacho", 23, "Argentina", "Delantero", null, false );
		// s.savePlayer(player5);
		// league1.setPlayer(player5);
		// manchesterUnited.setPlayer(player5);

		// Player player6 = new Player(league1, manchesterUnited, "Bruno Fernandes", 28, "Portuguesa", "MedioCampista", null, false );
		// s.savePlayer(player6);
		// league1.setPlayer(player6);
		// manchesterUnited.setPlayer(player6);

		// Team manchesterCity = new Team(league1, "Manchester City", 36, "England", "Pep Guardiola", "Juanma Lillo",
		// 		"Sheikh Mansour", "Etihad Stadium", null, false);
		// s.saveTeam(manchesterCity);
		// league1.setTeam(manchesterUnited);

		// Player player7 = new Player(league1, manchesterCity, "Gvardiol", 23, "Croata", "Defensa", null, false );
		// s.savePlayer(player7);
		// league1.setPlayer(player7);
		// manchesterCity.setPlayer(player7);;

		// Player player8 = new Player(league1, manchesterCity, "Rodrigo Hernandez", 21, "Española", "MedioCampista", null, false );
		// s.savePlayer(player8);
		// league1.setPlayer(player8);
		// manchesterCity.setPlayer(player8);

		// League league2 = new League("Bundesliga", "Donata Hopfen", "Alemana", null, false);
		// s.saveLeague(league2);

		// Team bayernMunich = new Team(league2, "Bayern Munich", 83, "Germany", "Thomas Tuchel", "Arno Michels",
		// 	"Herbert Hainer", "Allianz Arena", null, false);
		// s.saveTeam(bayernMunich);
		// league2.setTeam(bayernMunich);

		// Team borussiaDortmund = new Team(league2, "Borussia Dortmund", 59, "Germany", "Edin Terzić",
		// 		"Sebastian Geppert", "Hans-Joachim Watzke", "Signal Iduna Park", null, false);
		// s.saveTeam(borussiaDortmund);
		// league2.setTeam(borussiaDortmund);
	}
}
