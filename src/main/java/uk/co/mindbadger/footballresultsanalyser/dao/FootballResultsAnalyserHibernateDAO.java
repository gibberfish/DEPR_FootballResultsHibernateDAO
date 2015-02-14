package uk.co.mindbadger.footballresultsanalyser.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.DivisionImpl;
import uk.co.mindbadger.footballresultsanalyser.domain.DomainObjectFactory;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivision;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionId;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionImpl;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionTeam;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonImpl;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

@Repository("footballResultsAnalyserHibernateDao")
public class FootballResultsAnalyserHibernateDAO implements FootballResultsAnalyserDAO<Integer> {

	Logger logger = Logger.getLogger(FootballResultsAnalyserHibernateDAO.class);
	private Map<Thread, Session> sessions = new HashMap<Thread, Session>();
	private DomainObjectFactory<Integer> domainObjectFactory;
	private SessionFactory sessionFactory;

	@Override
	public void startSession() {
		logger.debug("About to start new Hibernate session...");
		Session session = sessionFactory.openSession();
		
		logger.debug("Session open, about to attach it to current thread: " + Thread.currentThread());

		sessions.put(Thread.currentThread(), session);
	}

	@Override
	public void closeSession() {
		logger.debug("About to close Hibernate session...");
		
		Session session = sessions.get(Thread.currentThread());
		session.close();

		logger.debug("Hibernate session closed, removing from sessions list...");
		
		sessions.remove(session);
	}

	@Override
	public List<Season<Integer>> getSeasons() {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Season<Integer>> seasons = null;
		tx = session.beginTransaction();

		// Get the Seasons
		seasons = session.createQuery("from SeasonImpl").list();

		tx.commit();

		logger.debug("DAO getSeasons " + seasons.size());
		
		return seasons;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Season<Integer> getSeason(Integer seasonNumber) {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Season<Integer>> seasons = null;
		tx = session.beginTransaction();

		seasons = session.createQuery("select S from SeasonImpl S where S.seasonNumber = " + seasonNumber).list();

		tx.commit();

		if (seasons.size() == 1) {
			logger.debug("DAO getSeason " + seasonNumber);
			return seasons.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Division<Integer>> getAllDivisions() {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Division<Integer>> divisions = null;
		tx = session.beginTransaction();

		// Get the Divisions
		divisions = session.createQuery("from DivisionImpl").list();

		tx.commit();

		Map<Integer, Division<Integer>> divisionsMap = new HashMap<Integer, Division<Integer>>();
		for (Division<Integer> division : divisions) {
			divisionsMap.put(division.getDivisionId(), division);
		}

		logger.debug("DAO getAllDivisions " + divisionsMap.size());
		
		return divisionsMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Team<Integer>> getAllTeams() {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Team<Integer>> teams = null;
		tx = session.beginTransaction();

		// Get the Divisions
		teams = session.createQuery("from TeamImpl").list();

		tx.commit();

		Map<Integer, Team<Integer>> teamsMap = new HashMap<Integer, Team<Integer>>();
		for (Team<Integer> team : teams) {
			teamsMap.put(team.getTeamId(), team);
		}

		logger.debug("DAO getAllTeams " + teamsMap.size());
		
		return teamsMap;
	}

	@Override
	public Set<SeasonDivision<Integer>> getDivisionsForSeason(int seasonNumber) {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		Season<Integer> season = null;
		tx = session.beginTransaction();

		season = (Season<Integer>) session.get(SeasonImpl.class, seasonNumber);

		tx.commit();

		logger.debug("DAO getDivisionsForSeason " + seasonNumber);
		
		return season.getDivisionsInSeason();
	}

	@Override
	public Set<SeasonDivisionTeam<Integer>> getTeamsForDivisionInSeason(int seasonNumber, int divisionId) {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		SeasonDivision<Integer> seasonDivision = null;
		tx = session.beginTransaction();

		Season<Integer> season = (Season<Integer>) session.get(SeasonImpl.class, seasonNumber);
		Division<Integer> division = (Division<Integer>) session.get(DivisionImpl.class, divisionId);

		SeasonDivisionId<Integer> seasonDivisionId = domainObjectFactory.createSeasonDivisionId(season, division);

		seasonDivision = (SeasonDivision<Integer>) session.get(SeasonDivisionImpl.class, seasonDivisionId);

		tx.commit();

		logger.debug("DAO getTeamsForDivisionInSeason " + seasonNumber + ", div " + divisionId + ", count " + seasonDivision.getTeamsInSeasonDivision().size());
		
		return seasonDivision.getTeamsInSeasonDivision();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fixture<Integer>> getFixturesForTeamInDivisionInSeason(int seasonNumber, int divisionId, int teamId) {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Fixture<Integer>> fixtures = null;
		tx = session.beginTransaction();

		fixtures = session.createQuery("select F from FixtureImpl F join F.division D join F.season S" + " where S.seasonNumber = " + seasonNumber + " and D.divisionId = " + divisionId + " order by F.fixtureDate").list();

		tx.commit();

		logger.debug("DAO getFixturesForTeamInDivisionInSeason " + seasonNumber + ", div " + divisionId + ", team " + teamId + ", count " + fixtures.size());
		
		return fixtures;
	}

	@Override
	public Season addSeason(Integer seasonNum) {
		Session session = sessions.get(Thread.currentThread());
		Transaction tx = session.beginTransaction();

		Season season = domainObjectFactory.createSeason(seasonNum);

		session.save(season);
		tx.commit();

		logger.debug("DAO addSeason " + seasonNum);
		
		return season;
	}

	@Override
	public Division<Integer> addDivision(String divisionName) {
		Session session = sessions.get(Thread.currentThread());
		Transaction tx = session.beginTransaction();

		Division<Integer> division = domainObjectFactory.createDivision(divisionName);

		session.save(division);
		tx.commit();
		
		logger.debug("DAO addDivision " + divisionName);
		
		return division;
	}

	@Override
	public Team<Integer> addTeam(String teamName) {
		Session session = sessions.get(Thread.currentThread());
		Transaction tx = session.beginTransaction();

		Team<Integer> team = domainObjectFactory.createTeam(teamName);

		session.save(team);
		tx.commit();

		logger.debug("DAO addTeam " + teamName);
		
		return team;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fixture<Integer> addFixture(Season<Integer> season, Calendar fixtureDate, Division<Integer> division, Team<Integer> homeTeam, Team<Integer> awayTeam, Integer homeGoals, Integer awayGoals) {
		logger.info("About to add fixture, ssn="+season+", date="+String.format("%1$te-%1$tm-%1$tY", fixtureDate)+", div="+division+", hmTeam="+homeTeam+", awTeam="+awayTeam+", score="+homeGoals+"-"+awayGoals);
		Session session = sessions.get(Thread.currentThread());
		Transaction tx = session.beginTransaction();
		Fixture<Integer> fixture = null;

		// Have we got a fixture with a date?

		List<Fixture<Integer>> fixtures = null;
		StringBuffer sb = new StringBuffer("select F from FixtureImpl F join F.homeTeam T1 join F.awayTeam T2 join F.season S ");
		sb.append(" where S.seasonNumber = :seasonNumber ");
		sb.append(" and T1.teamId = :homeTeamId ");
		sb.append(" and T2.teamId = :awayTeamId ");
		sb.append(" and F.fixtureDate = :fixtureDate ");

		Query query = session.createQuery(sb.toString());
		query.setInteger("seasonNumber", season.getSeasonNumber());
		query.setInteger("homeTeamId", homeTeam.getTeamId());
		query.setInteger("awayTeamId", awayTeam.getTeamId());
		query.setCalendarDate("fixtureDate", fixtureDate);
		
		fixtures = query.list();
		
		if (fixtures.size() > 0) {
			logger.info("Got an existing fixture on the date specified (count: " + fixtures.size() + ")");
			fixture = fixtures.get(0);
			Calendar existingFixtureDate = fixture.getFixtureDate();
			logger.debug("Fixture date: " + new SimpleDateFormat("yyyy-MM-dd").format(existingFixtureDate.getTime()));
		} else {
			sb = new StringBuffer("select F from FixtureImpl F join F.homeTeam T1 join F.awayTeam T2 join F.season S join F.division D ");
			sb.append(" where S.seasonNumber = :seasonNumber ");
			sb.append(" and T1.teamId = :homeTeamId ");
			sb.append(" and T2.teamId = :awayTeamId ");
			sb.append(" and D.divisionId = :divisionId ");
	
			query = session.createQuery(sb.toString());
			query.setInteger("seasonNumber", season.getSeasonNumber());
			query.setInteger("homeTeamId", homeTeam.getTeamId());
			query.setInteger("awayTeamId", awayTeam.getTeamId());
			query.setInteger("divisionId", division.getDivisionId());
			
			fixtures = query.list();
			
			if (fixtures.size() > 0) {
				fixture = fixtures.get(0);
				Calendar existingFixtureDate = fixture.getFixtureDate();
				logger.info("Got an existing fixture for these teams, but not on the date supplied");
				if (existingFixtureDate != null) {
					logger.info("...Shouldn't have one, but fixture date: " + new SimpleDateFormat("yyyy-MM-dd").format(existingFixtureDate.getTime()));
				}
			} else {
				logger.debug("No fixture found for these teams in this division for the season, so adding one...");
				fixture = domainObjectFactory.createFixture(season, homeTeam, awayTeam);
			}
			
			fixture.setFixtureDate(fixtureDate);
		}
		
		fixture.setDivision(division);
		fixture.setHomeGoals(homeGoals);
		fixture.setAwayGoals(awayGoals);

		session.save(fixture);
		tx.commit();

		logger.debug("DAO addFixture " + fixture);
		
		return fixture;
	}

	public DomainObjectFactory<Integer> getDomainObjectFactory() {
		return domainObjectFactory;
	}

	public void setDomainObjectFactory(DomainObjectFactory<Integer> domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fixture<Integer>> getUnplayedFixturesBeforeToday() {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Fixture<Integer>> fixtures = null;
		tx = session.beginTransaction();

		fixtures = session.createQuery("select F from FixtureImpl F where F.fixtureDate <= current_date and F.homeGoals is null").list();

		tx.commit();

		logger.info("DAO getUnplayedFixturesBeforeToday, returned " + fixtures.size() + " fixtures.");
		
		return fixtures;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fixture<Integer>> getFixturesWithNoFixtureDate() {
		Transaction tx = null;

		Session session = sessions.get(Thread.currentThread());

		List<Fixture<Integer>> fixtures = null;
		tx = session.beginTransaction();

		fixtures = session.createQuery("select F from FixtureImpl F where F.fixtureDate is null").list();

		tx.commit();

		logger.info("DAO getFixturesWithNoFixtureDate, returned " + fixtures.size() + " fixtures.");
		
		return fixtures;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
