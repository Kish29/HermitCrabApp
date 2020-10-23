/*package com.kish2.hermitcrabapp.anno.aspect;

import android.view.View;

import com.kish2.hermitcrabapp.anno.SingleClick;
import com.kish2.hermitcrabapp.utils.ClickUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class SingleClickAspect {

    private static final long DEFAULT_TIME_INTERVAL = 5000;

    *//**
     * 定义切点，标记切点为所有被
     * "@SingleClick"
     * 注解的方法(注意下面的"@"符号)
     * com.kish2.hermitcrabapp.anno.SingleClick需要替换成
     * 自己项目中SingleClick这个类的全路径哦
     *//*
    @Pointcut("execution(@com.kish2.hermitcrabapp.anno.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    //     定义切面方法，包裹切点
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出切点方法的参数，在button中是被点击的button（也就是那个v）
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }

        if (view == null)
            return;

        //取出方法的注解名称
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 不是SingleClick返回
        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        assert singleClick != null;
        if (!ClickUtil.isFastRepeatClick(view, singleClick.value())) {
            //不是快速点击，执行原方法
            joinPoint.proceed();
        }
    }
}*/
