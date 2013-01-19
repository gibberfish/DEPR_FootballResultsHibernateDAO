package uk.co.mindbadger.footballresultsanalyser.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class SeasonDivisionTeamImpl implements SeasonDivisionTeam {
    private static final long serialVersionUID = -862413151112264079L;

    private SeasonDivisionTeamId primaryKey = new SeasonDivisionTeamIdImpl();

    @Override
    @EmbeddedId
    @Target(SeasonDivisionTeamIdImpl.class)
    public SeasonDivisionTeamId getPrimaryKey() {
	return primaryKey;
    }

    @Override
    public void setPrimaryKey(SeasonDivisionTeamId primaryKey) {
	this.primaryKey = primaryKey;
    }

    @Override
    @Transient
    @Target(SeasonDivisionImpl.class)
    public SeasonDivision getSeasonDivision() {
	return primaryKey.getSeasonDivision();
    }

    @Override
    public void setSeasonDivision(SeasonDivision seasonDivision) {
	primaryKey.setSeasonDivision(seasonDivision);
    }

    @Override
    @Transient
    @Target(TeamImpl.class)
    public Team getTeam() {
	return primaryKey.getTeam();
    }

    @Override
    public void setTeam(Team team) {
	primaryKey.setTeam(team);
    }
}
