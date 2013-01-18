package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "division", catalog = "football")
public class DivisionImpl implements Division {
    private static final long serialVersionUID = 693092329472146716L;

    private Integer divisionId;
    private String divisionName;
    private Set<SeasonDivision> seasonsForDivision = new HashSet<SeasonDivision>();

    public DivisionImpl() { }
    
    public DivisionImpl(String divisionName) {
	this.divisionName = divisionName;
    }

    @Override
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "DIV_ID", unique = true, nullable = false)
    public Integer getDivisionId() {
	return divisionId;
    }

    @Override
    public void setDivisionId(Integer divisionId) {
	this.divisionId = divisionId;
    }

    @Override
    @Column(name = "DIV_NAME", nullable = false)
    public String getDivisionName() {
	return divisionName;
    }

    @Override
    public void setDivisionName(String divisionName) {
	this.divisionName = divisionName;
    }

    @Override
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryKey.division", targetEntity=SeasonDivisionImpl.class)
    public Set<SeasonDivision> getSeasonsForDivision() {
	return seasonsForDivision;
    }

    @Override
    public void setSeasonsForDivision(Set<SeasonDivision> seasonsForDivision) {
	this.seasonsForDivision = seasonsForDivision;
    }
}
