package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private final RunnerService runnerService;
    private final ShoeNameRepository shoeNameRepository;

    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository,
                                RunnerService runnerService, ShoeNameRepository shoeNameRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.runnerService = runnerService;
        this.shoeNameRepository = shoeNameRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    @GetMapping("/biggest-shoe-size-runner")
    public ResponseEntity<String> getBiggestShoeSizeRunnerName() {
        return ResponseEntity.ok(runnerService.getBiggestShoeSizeRunner().getRunnerName());
    }

    @PutMapping("/{id}/shoe-name")
    public ResponseEntity<?> changeShoeName(
            @PathVariable Long id,
            @RequestParam(name = "shoeNameId") Long shoeNameId
    ) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        ShoeName shoeName = shoeNameRepository.findById(shoeNameId).orElse(null);
        if (runner == null || shoeName == null) {
            String message;
            if(runner == null) {
                message = "Runner with ID " + id + " not found";
            } else {
                message = "Shoe-name with ID " + shoeNameId + " not found";
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            runner.setShoeName(shoeName);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        }
    }


    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }
}
