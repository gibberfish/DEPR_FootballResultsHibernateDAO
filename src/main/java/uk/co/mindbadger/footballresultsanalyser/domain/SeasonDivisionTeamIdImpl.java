package uk.co.mindbadger.footballresultsanalyser.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SeasonDivisionTeamIdImpl implements SeasonDivisionTeamId {
    private static final long serialVersionUID = 2550212076229186627L;

    private SeasonDivision seasonDivision;
    private Team team;

    @Override
    @ManyToOne (targetEntity=SeasonDivisionImpl.class)
    public SeasonDivision getSeasonDivision() {
	return seasonDivision;
    }

    @Override
    public void setSeasonDivision(SeasonDivision seasonDivision) {
	this.seasonDivision = seasonDivision;
    }

    @Override
    @ManyToOne (targetEntity=TeamImpl.class)
    public Team getTeam() {
	return team;
    }

    @Override
    public void setTeam(Team team) {
	this.team = team;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;

	SeasonDivisionTeamId that = (SeasonDivisionTeamId) o;

	if (seasonDivision != null ? !seasonDivision.equals(that.getSeasonDivision()) : that.getSeasonDivision() != null)
	    return false;
	if (team != null ? !team.equals(that.getTeam()) : that.getTeam() != null)
	    return false;

	return true;
    }

    @Override
    public int hashCode() {
	int result;
	result = (seasonDivision != null ? seasonDivision.hashCode() : 0);
	result = 31 * result + (team != null ? team.hashCode() : 0);
	return result;
    }
}
