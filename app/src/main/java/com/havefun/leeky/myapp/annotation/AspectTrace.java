package com.havefun.leeky.myapp.annotation;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * Created by Leeky on 2020/11/13.
 */

@Aspect
public class AspectTrace {
    private static AspectTraceListener aspectTraceListener;

    /**
     * 针对所有继承 Activity 类的 onCreate 方法
     */
    @Pointcut("execution(* android.support.v7.app.AppCompatActivity.onCreate(..))")
    public void activityOnCreatePointcut() {

    }
    /**
     * 针对带有AspectAnalyze注解的方法
     */
    @Pointcut("execution(@com.havefun.leeky.myapp.annotation.AspectAnalyze * *(..))")
    public void aspectAnalyzeAnnotation() {
    }
    /**
     * 针对带有AspectDebugLog注解的方法
     */
    @Pointcut("execution(@com.havefun.leeky.myapp.annotation.AspectDebugLog * *(..))")
    public void aspectDebugLogAnnotation() {
    }

    /**
     * 针对前面 aspectAnalyzeAnnotation() 的配置
     */
    @Around("aspectAnalyzeAnnotation()")
    public void aroundJoinAspectAnalyze(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AspectAnalyze aspectAnalyze = methodSignature.getMethod().getAnnotation(AspectAnalyze.class);
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        if (aspectTraceListener != null) {
            aspectTraceListener.onAspectAnalyze(joinPoint, aspectAnalyze, methodSignature, System.currentTimeMillis() - startTimeMillis);
        }
    }
    /**
     * 针对前面 aspectDebugLogAnnotation() 或 activityOnCreatePointcut() 的配置
     */
    @Around("aspectDebugLogAnnotation()")
    public void aroundJoinAspectDebugLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTimeMillis;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SourceLocation location = joinPoint.getSourceLocation();
        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(), location.getFileName(), location.getLine(), duration);
        if (aspectTraceListener != null) {
            aspectTraceListener.logger("AspectTrace", message);
        } else {
            Log.e("AspectTrace", message);
        }
    }

    public static void setAspectTraceListener(AspectTraceListener aspectTraceListener) {
        AspectTrace.aspectTraceListener = aspectTraceListener;
    }

    public interface AspectTraceListener {
        void logger(String tag, String message);

        void onAspectAnalyze(ProceedingJoinPoint joinPoint, AspectAnalyze aspectAnalyze, MethodSignature methodSignature, long duration);
    }
}
