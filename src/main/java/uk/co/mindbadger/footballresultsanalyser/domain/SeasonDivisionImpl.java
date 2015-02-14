package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Target;

@Entity
@Table(name = "season_division", catalog = "football")
@AssociationOverrides({
		@AssociationOverride(name = "primaryKey.season", 
			joinColumns = @JoinColumn(name = "SSN_NUM")),
		@AssociationOverride(name = "primaryKey.division", 
			joinColumns = @JoinColumn(name = "DIV_ID")) })
public class SeasonDivisionImpl implements SeasonDivision<Integer> {
	private static final long serialVersionUID = -862413151112264079L;
	
	private SeasonDivisionId<Integer> primaryKey = new SeasonDivisionIdImpl();
	private int divisionPosition;
	private Set<SeasonDivisionTeam<Integer>> teamsInSeasonDivision = new HashSet<SeasonDivisionTeam<Integer>>(0);
	
	@Override
	@EmbeddedId
	@Target(SeasonDivisionIdImpl.class)
	public SeasonDivisionId<Integer> getPrimaryKey() {
		return primaryKey;
	}
	@Override
	public void setPrimaryKey(SeasonDivisionId<Integer> primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	@Transient
	@Target(SeasonImpl.class)
	public Season<Integer> getSeason() {
		return primaryKey.getSeason();
	}
	@Override
	public void setSeason(Season<Integer> season) {
		primaryKey.setSeason(season);
	}
	
	@Override
	@Transient
	@Target(DivisionImpl.class)
	public Division<Integer> getDivision() {
		return primaryKey.getDivision();
	}
	@Override
	public void setDivision(Division<Integer> division) {
		primaryKey.setDivision(division);
	}
	
	@Override
	@Column(name = "DIV_POS", nullable=true)
	public int getDivisionPosition() {
		return divisionPosition;
	}
	@Override
	public void setDivisionPosition(int divisionPosition) {
		this.divisionPosition = divisionPosition;
	}
	
	@Override
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryKey.seasonDivision", targetEntity=SeasonDivisionTeamImpl.class)
	public Set<SeasonDivisionTeam<Integer>> getTeamsInSeasonDivision() {
		return teamsInSeasonDivision;
	}
	@Override
	public void setTeamsInSeasonDivision(Set<SeasonDivisionTeam<Integer>> teamsInSeasonDivision) {
		this.teamsInSeasonDivision = teamsInSeasonDivision;
	}
}
