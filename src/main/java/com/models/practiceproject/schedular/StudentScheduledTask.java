package com.models.practiceproject.schedular;

import com.models.practiceproject.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentScheduledTask {
    private StudentRepository studentRepository;
    public StudentScheduledTask(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    @Scheduled(fixedRate = 10000)
//    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "*/10 * * * * *")
    public void printStudentCount(){
        long studentCount = studentRepository.count();
        log.info("Student count is {}", studentCount);
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Practice examples on scheduled task");
    }
}
