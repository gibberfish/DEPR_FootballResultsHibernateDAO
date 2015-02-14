package uk.co.mindbadger.footballresultsanalyser.domain;

public class DomainObjectFactoryImpl implements DomainObjectFactory<Integer> {
	
	@Override
	public Season createSeason(Integer seasonNum) {
		Season season = new SeasonImpl();
		season.setSeasonNumber(seasonNum);
		return season;
	}

	@Override
	public Division<Integer> createDivision(String divisionName) {
		Division<Integer> division = new DivisionImpl();
		division.setDivisionName(divisionName);
		return division;
	}
	
	@Override
	public Team<Integer> createTeam(String teamName) {
		Team<Integer> team = new TeamImpl ();
		team.setTeamName(teamName);
		return team;
	}

	@Override
	public Fixture<Integer> createFixture(Season<Integer> season, Team<Integer> homeTeam, Team<Integer> awayTeam) {
		Fixture<Integer> fixture = new FixtureImpl();
		fixture.setSeason(season);
		fixture.setHomeTeam(homeTeam);
		fixture.setAwayTeam(awayTeam);
		return fixture;
	}

	@Override
	public SeasonDivision createSeasonDivision(Season<Integer> season, Division<Integer> division) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeasonDivisionTeam createSeasonDivisionTeam(SeasonDivision<Integer> seasonDivision, Team<Integer> team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeasonDivisionId createSeasonDivisionId(Season<Integer> season, Division<Integer> division) {
		SeasonDivisionId seasonDivisionId = new SeasonDivisionIdImpl();
		seasonDivisionId.setSeason(season);
		seasonDivisionId.setDivision(division);
		return seasonDivisionId;
	}

	@Override
	public SeasonDivisionTeamId createSeasonDivisionTeamId(SeasonDivision<Integer> seasonDivision, Team<Integer> team) {
		SeasonDivisionTeamId seasonDivisionTeamId = new SeasonDivisionTeamIdImpl();
		seasonDivisionTeamId.setSeasonDivision(seasonDivision);
		seasonDivisionTeamId.setTeam(team);
		return seasonDivisionTeamId;
	}
}
