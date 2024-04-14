package book.project.First.aspect;

import book.project.First.annotation.TrackExecution;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Log
@Configuration
public class TrackingExecutionAspect {
    @Around("@annotation(trackExecution)")
    public Object trackingExecutionSpecifiedForMethod(ProceedingJoinPoint pjp, TrackExecution trackExecution) throws Throwable {
        return handleTrackingExecution(pjp, trackExecution);
    }

    @Around("@within(trackExecution) && !@annotation(book.project.First.annotation.TrackExecution)")
    public Object trackingExecutionSpecifiedForClass(ProceedingJoinPoint pjp, TrackExecution trackExecution) throws Throwable {
        return handleTrackingExecution(pjp, trackExecution);
    }

    private Object handleTrackingExecution(ProceedingJoinPoint pjp, TrackExecution trackExecution) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed(pjp.getArgs());
        long endTime = System.currentTimeMillis();

        if (trackExecution.isEnabled()) {
            long executionTime = endTime - startTime;

            Signature signature = pjp.getSignature();

            StringBuilder messageSb = new StringBuilder("Executed method: " + signature);
            if (trackExecution.isExecutionTimeEnabled()) {
                messageSb.append(". Execution time: " + executionTime + "ms");
            }

            log.info(messageSb.toString());
        }

        return result;
    }
}