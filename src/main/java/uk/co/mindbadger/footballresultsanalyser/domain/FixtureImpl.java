package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "fixture", catalog = "football")
public class FixtureImpl implements Fixture {
    private static final long serialVersionUID = -1065165521448191394L;

    private Integer fixtureId;
    private Season season;
    private Team homeTeam;
    private Team awayTeam;
    private Calendar fixtureDate;
    private Division division;
    private Integer homeGoals;
    private Integer awayGoals;

    @Override
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "FIXTURE_ID", unique = true, nullable = false)
    public Integer getFixtureId() {
	return fixtureId;
    }
    @Override
    public void setFixtureId(Integer fixtureId) {
	this.fixtureId = fixtureId;
    }

    @Override
    @ManyToOne (targetEntity=SeasonImpl.class)
    @JoinColumn(name = "SSN_NUM")
    public Season getSeason() {
	return season;
    }
    @Override
    public void setSeason(Season season) {
	this.season = season;
    }

    @Override
    @ManyToOne (targetEntity=TeamImpl.class)
    @JoinColumn(name = "HOME_TEAM_ID")
    public Team getHomeTeam() {
	return homeTeam;
    }
    @Override
    public void setHomeTeam(Team homeTeam) {
	this.homeTeam = homeTeam;
    }

    @Override
    @ManyToOne (targetEntity=TeamImpl.class)
    @JoinColumn(name = "AWAY_TEAM_ID")
    public Team getAwayTeam() {
	return awayTeam;
    }
    @Override
    public void setAwayTeam(Team awayTeam) {
	this.awayTeam = awayTeam;
    }

    @Override
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "FIXTURE_DATE", nullable = true)
    public Calendar getFixtureDate() {
	return fixtureDate;
    }
    @Override
    public void setFixtureDate(Calendar fixtureDate) {
	this.fixtureDate = fixtureDate;
    }

    @Override
    @ManyToOne (targetEntity=DivisionImpl.class)
    @JoinColumn(name = "DIV_ID")
    public Division getDivision() {
	return division;
    }
    @Override
    public void setDivision(Division division) {
	this.division = division;
    }

    @Override
    @Column(name = "HOME_GOALS", nullable = true)
    public Integer getHomeGoals() {
	return homeGoals;
    }
    @Override
    public void setHomeGoals(Integer homeGoals) {
	this.homeGoals = homeGoals;
    }

    @Override
    @Column(name = "AWAY_GOALS", nullable = true)
    public Integer getAwayGoals() {
	return awayGoals;
    }
    @Override
    public void setAwayGoals(Integer awayGoals) {
	this.awayGoals = awayGoals;
    }
}
