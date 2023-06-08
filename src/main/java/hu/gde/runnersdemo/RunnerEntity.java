package hu.gde.runnersdemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RunnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long runnerId;
    private String runnerName;
    private long pace;
    private int shoeSize;

    @JsonIgnore
    @ManyToOne
    private ShoeName shoeName;

    @OneToMany(mappedBy = "runner")
    private List<LapTimeEntity> laptimes = new ArrayList<>();


    public RunnerEntity() {
    }

    public long getRunnerId() {
        return runnerId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public long getPace() {
        return pace;
    }

    public void setRunnerId(long runnerId) {
        this.runnerId = runnerId;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public void setPace(long pace) {
        this.pace = pace;
    }

    public List<LapTimeEntity> getLaptimes() {
        return laptimes;
    }

    public int getShoeSize()
    {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize)
    {
        this.shoeSize = shoeSize;
    }

    public ShoeName getShoeName()
    {
        return shoeName;
    }

    public void setShoeName(ShoeName shoeName)
    {
        this.shoeName = shoeName;
    }
}
