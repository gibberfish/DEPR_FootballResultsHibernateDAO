package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="season", catalog="football")
public class SeasonImpl implements Season<Integer> {
	private static final long serialVersionUID = -4032527106145900975L;

	private Integer seasonNumber;
	private Set<SeasonDivision<Integer>> divisionsInSeason = new HashSet<SeasonDivision<Integer>>(0);
	//public Set<Fixture> fixturesInSeason;

	public SeasonImpl () {}
	
	public SeasonImpl (Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
	@Id
	@Column(name="SSN_NUM", unique=true, nullable=false)
	public Integer getSeasonNumber() {
		return seasonNumber;
	}
	
	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryKey.season", cascade=CascadeType.ALL, targetEntity=SeasonDivisionImpl.class)
	public Set<SeasonDivision<Integer>> getDivisionsInSeason() {
		return divisionsInSeason;
	}
	public void setDivisionsInSeason(Set<SeasonDivision<Integer>> divisionsInSeason) {
		this.divisionsInSeason = divisionsInSeason;
	}
	
	//TODO Move to DAO
//	public void addDivisionToSeason (Division division, int divisionPosition) {
//		SeasonDivision seasonDivision = new SeasonDivision ();
//		seasonDivision.setSeason(this);
//		seasonDivision.setDivision(division);
//		seasonDivision.setDivPos(divisionPosition);
//		this.divisionsInSeason.add(seasonDivision);
//		division.getSeasonsForDivision().add(seasonDivision);
//	}
	
//	public Set<Fixture> getFixturesInSeason() {
//		return fixturesInSeason;
//	}
//	public void setFixturesInSeason(Set<Fixture> fixturesInSeason) {
//		this.fixturesInSeason = fixturesInSeason;
//	}
}
