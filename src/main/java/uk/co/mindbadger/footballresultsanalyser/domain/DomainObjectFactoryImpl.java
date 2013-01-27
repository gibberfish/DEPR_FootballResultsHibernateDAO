package uk.co.mindbadger.footballresultsanalyser.domain;

public class DomainObjectFactoryImpl implements DomainObjectFactory {
	
	@Override
	public Season createSeason(Integer seasonNum) {
		Season season = new SeasonImpl();
		season.setSeasonNumber(seasonNum);
		return season;
	}

	@Override
	public Division createDivision(String divisionName) {
		Division division = new DivisionImpl();
		division.setDivisionName(divisionName);
		return division;
	}
	
	@Override
	public Team createTeam(String teamName) {
		Team team = new TeamImpl ();
		team.setTeamName(teamName);
		return team;
	}

	@Override
	public Fixture createFixture(Season season, Team homeTeam, Team awayTeam) {
		Fixture fixture = new FixtureImpl();
		fixture.setSeason(season);
		fixture.setHomeTeam(homeTeam);
		fixture.setAwayTeam(awayTeam);
		return fixture;
	}

	@Override
	public SeasonDivision createSeasonDivision(Season season, Division division) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeasonDivisionTeam createSeasonDivisionTeam(SeasonDivision seasonDivision, Team team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeasonDivisionId createSeasonDivisionId(Season season, Division division) {
		SeasonDivisionId seasonDivisionId = new SeasonDivisionIdImpl();
		seasonDivisionId.setSeason(season);
		seasonDivisionId.setDivision(division);
		return seasonDivisionId;
	}

	@Override
	public SeasonDivisionTeamId createSeasonDivisionTeamId(SeasonDivision seasonDivision, Team team) {
		SeasonDivisionTeamId seasonDivisionTeamId = new SeasonDivisionTeamIdImpl();
		seasonDivisionTeamId.setSeasonDivision(seasonDivision);
		seasonDivisionTeamId.setTeam(team);
		return seasonDivisionTeamId;
	}
}
