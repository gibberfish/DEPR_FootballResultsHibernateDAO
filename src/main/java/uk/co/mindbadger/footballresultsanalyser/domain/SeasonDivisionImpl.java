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

@Entity
@Table(name = "season_division", catalog = "football")
@AssociationOverrides({
		@AssociationOverride(name = "primaryKey.season", 
			joinColumns = @JoinColumn(name = "SSN_NUM")),
		@AssociationOverride(name = "primaryKey.division", 
			joinColumns = @JoinColumn(name = "DIV_ID")) })
public class SeasonDivisionImpl implements SeasonDivision {
	private static final long serialVersionUID = -862413151112264079L;
	
	private SeasonDivisionId primaryKey = new SeasonDivisonIdImpl();
	private int divisionPosition;
	private Set<SeasonDivisionTeam> teamsInSeasonDivision = new HashSet<SeasonDivisionTeam>(0);
	
	@Override
	@EmbeddedId
	public SeasonDivisionId getPrimaryKey() {
		return primaryKey;
	}
	@Override
	public void setPrimaryKey(SeasonDivisionId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	@Transient
	public Season getSeason() {
		return primaryKey.getSeason();
	}
	@Override
	public void setSeason(Season season) {
		primaryKey.setSeason(season);
	}
	
	@Override
	@Transient
	public Division getDivision() {
		return primaryKey.getDivision();
	}
	@Override
	public void setDivision(Division division) {
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
	public Set<SeasonDivisionTeam> getTeamsInSeasonDivision() {
		return teamsInSeasonDivision;
	}
	@Override
	public void setTeamsInSeasonDivision(Set<SeasonDivisionTeam> teamsInSeasonDivision) {
		this.teamsInSeasonDivision = teamsInSeasonDivision;
	}
}
