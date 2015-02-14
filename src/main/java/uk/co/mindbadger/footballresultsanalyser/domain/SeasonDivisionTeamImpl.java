package uk.co.mindbadger.footballresultsanalyser.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Target;

@Entity
@Table(name = "season_division_team", catalog = "football")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.seasonDivision", joinColumns = {
	    @JoinColumn(name = "SSN_NUM", referencedColumnName="SSN_NUM"),
	    @JoinColumn(name = "DIV_ID", referencedColumnName="DIV_ID")
    }),
    @AssociationOverride(name = "primaryKey.team", joinColumns = @JoinColumn(name = "TEAM_ID")) })
public class SeasonDivisionTeamImpl implements SeasonDivisionTeam<Integer> {
    private static final long serialVersionUID = -862413151112264079L;

    private SeasonDivisionTeamId<Integer> primaryKey = new SeasonDivisionTeamIdImpl();

    @Override
    @EmbeddedId
    @Target(SeasonDivisionTeamIdImpl.class)
    public SeasonDivisionTeamId<Integer> getPrimaryKey() {
	return primaryKey;
    }

    @Override
    public void setPrimaryKey(SeasonDivisionTeamId<Integer> primaryKey) {
	this.primaryKey = primaryKey;
    }

    @Override
    @Transient
    @Target(SeasonDivisionImpl.class)
    public SeasonDivision<Integer> getSeasonDivision() {
	return primaryKey.getSeasonDivision();
    }

    @Override
    public void setSeasonDivision(SeasonDivision<Integer> seasonDivision) {
	primaryKey.setSeasonDivision(seasonDivision);
    }

    @Override
    @Transient
    @Target(TeamImpl.class)
    public Team<Integer> getTeam() {
	return primaryKey.getTeam();
    }

    @Override
    public void setTeam(Team<Integer> team) {
	primaryKey.setTeam(team);
    }
}
