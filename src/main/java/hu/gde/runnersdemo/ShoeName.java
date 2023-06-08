package hu.gde.runnersdemo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ShoeName
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shoeNameId;

    private String shoeName;

    @OneToMany(mappedBy = "shoeName")
    private List<RunnerEntity> runners;

    public long getShoeNameId()
    {
        return shoeNameId;
    }

    public void setShoeNameId(long shoeNameId)
    {
        this.shoeNameId = shoeNameId;
    }

    public String getShoeName()
    {
        return shoeName;
    }

    public void setShoeName(String shoeName)
    {
        this.shoeName = shoeName;
    }

    public List<RunnerEntity> getRunners()
    {
        return runners;
    }
}
