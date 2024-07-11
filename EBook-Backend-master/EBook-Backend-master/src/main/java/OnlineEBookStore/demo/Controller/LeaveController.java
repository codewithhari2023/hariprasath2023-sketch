package OnlineEBookStore.demo.Controller;
import OnlineEBookStore.demo.Model.LeaveRequest;
import OnlineEBookStore.demo.Repository.LeaveRepository;
import OnlineEBookStore.demo.Request.LeaveDto;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private  APIResponse apiResponse;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllLeaves() {
        List<LeaveRequest> LeaveList = leaveRepository.findAll();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(LeaveList );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/createLeave")
    public ResponseEntity<?> createLeave(@RequestBody LeaveDto leaveDTO) {

                      LeaveRequest leave = new LeaveRequest();
                      leave.setUsername(leaveDTO.getUsername());
                      leave.setLeaveStart((leaveDTO.getStartDate()));
                      leave.setLeaveEnd((leaveDTO.getEndDate()));
                      leave.setReason(leaveDTO.getReason());
                      leave.setStatus("Pending");

                      leaveRepository.save(leave);
                      return ResponseEntity.ok("Leave created successfully");

    }



    @PutMapping("/{id}")
    public LeaveRequest updateLeave(@PathVariable Long id, @RequestBody LeaveRequest leaveDetails) {
        LeaveRequest leave = leaveRepository.findById(id).orElseThrow();
        leave.setLeaveStart(leaveDetails.getLeaveStart());
        leave.setLeaveEnd(leaveDetails.getLeaveEnd());
        leave.setReason(leaveDetails.getReason());
        leave.setStatus(leaveDetails.getStatus());
        return leaveRepository.save(leave);
    }
}



